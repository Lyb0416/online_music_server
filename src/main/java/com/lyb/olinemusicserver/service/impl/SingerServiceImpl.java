package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.mapper.SingerMapper;
import com.lyb.olinemusicserver.model.domain.Singer;
import com.lyb.olinemusicserver.model.request.SingerRequest;
import com.lyb.olinemusicserver.service.ObjectStoreService;
import com.lyb.olinemusicserver.service.SingerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
* @author LENOVO
* @description 针对表【singer】的数据库操作Service实现
* @createDate 2025-09-16 15:06:04
*/
@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer>
    implements SingerService{

    @Autowired
    private SingerMapper singerMapper;

    @Autowired
    private ObjectStoreService oss;


    @Override
    public R allSinger(Integer curPage, Integer pageSize) {
        Page<Singer> result = singerMapper.selectPage(new Page<>(curPage, pageSize), null);
        return R.success(null,
                result.convert(singer -> { // 将Singer对象转换为SingerRequest对象
                    SingerRequest singerRequest = new SingerRequest();
                    BeanUtils.copyProperties(singer, singerRequest);
                    return singerRequest;
                }));
    }

    @Override
    public R allSingers() {
        return R.success(null, singerMapper.selectList(null));
    }

    @Override
    public R addSinger(SingerRequest addSingerRequest) {
        Singer singer = new Singer();
        BeanUtils.copyProperties(addSingerRequest, singer);
        //给出默认头像,后续管理员可以在线修改
        String pic = "img/avatorImages/user.jpg";
        singer.setPic(pic);
        if(singerMapper.insert(singer) > 0) {
            return R.success("添加成功");
        }else {
            return R.error("添加失败");
        }
    }

    @Override
    public R deleteSinger(int id) {
        // 1. 查询歌手信息
        Singer singer = singerMapper.selectById(id);
        if (singer == null) {
            return R.error("歌手不存在");
        }

        // 2. 获取该歌手的头像（OSS 文件路径）
        String obj = singer.getPic();

        // 3. 优先删除 OSS 文件
        boolean flag = false;
        try {
            flag = oss.deleteFile(obj); // 删除 OSS 上的文件
            System.out.println("删除OSS文件：" + flag); // 输出删除结果
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除OSS文件失败：" + e.getMessage());
        }

        // 4. 再删除数据库中的歌手记录
        int rows = singerMapper.deleteById(id);
        if (rows > 0) {
            return R.success("删除成功（OSS删除：" + flag + "）");
        } else {
            return R.error("数据库删除失败（OSS删除：" + flag + "）");
        }
    }


    @Override
    public R deleteSingers(String[] ids) {
        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            sb.append(id).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        if (singerMapper.deleteIds(sb.toString()) > 0) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    @Override
    public R updateSingerMsg(SingerRequest updateSingerRequest) {
        Singer singer = new Singer();
        BeanUtils.copyProperties(updateSingerRequest, singer);
        if (singerMapper.updateById(singer) > 0) {
            return R.success("修改成功");
        } else {
            return R.error("修改失败");
        }
    }

    @Override
    public R selectSingerByGender(int gender) {
        return R.success("根据性别查询歌手成功",
                singerMapper.selectList(
                        new LambdaQueryWrapper<Singer>().eq(Singer::getGender, gender)
                )
        );
    }

    @Override
    public R updateSingerPic(MultipartFile avatorFile, int id) {
        String fileName = System.currentTimeMillis() + "-" + avatorFile.getOriginalFilename();
//        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "img/singerPic";
        // 使用阿里云对象存储服务替换原来的存储在本地的流程
        String ossFilePath;
        try {
            ossFilePath = oss.uploadFile(imgPath, fileName, avatorFile);
        } catch (IOException e) {
            return R.fatal("上传失败" + e.getMessage());
        }

        Singer singer = new Singer();
        singer.setId(id);
        singer.setPic(ossFilePath);
        if (singerMapper.updateById(singer) > 0) {
            return R.success("上传成功", ossFilePath);
        } else {
            return R.error("上传失败");
        }
    }
}




