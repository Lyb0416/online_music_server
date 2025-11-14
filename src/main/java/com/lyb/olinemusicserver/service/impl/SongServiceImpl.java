package com.lyb.olinemusicserver.service.impl;

import com.aliyun.oss.OSS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.Song;
import com.lyb.olinemusicserver.model.request.SongRequest;
import com.lyb.olinemusicserver.service.ObjectStoreService;
import com.lyb.olinemusicserver.service.SongService;
import com.lyb.olinemusicserver.mapper.SongMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

/**
* @author LENOVO
* @description 针对表【song】的数据库操作Service实现
* @createDate 2025-09-16 16:35:45
*/
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song>
    implements SongService{

    @Autowired
    private SongMapper songMapper;

    @Override
    public R songOfSingerId(int singerId) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("singer_id", singerId);
        return R.success("歌手歌曲", songMapper.selectList(queryWrapper));
    }

    @Override
    public R songOfSongListId(int songListId) {
        return R.success("查询成功",songMapper.selectSongListByListId(songListId));
    }

    @Override
    public R addSong(SongRequest addSongRequest, MultipartFile mpfile) {
        Song song = new Song();
        BeanUtils.copyProperties(addSongRequest, song);
        String pic = "img/songPic/tubiao.jpg";
        String fileName = System.currentTimeMillis() + "-" + mpfile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        File file = new File(filePath);
        if (!file.exists()) {
            if(!file.mkdir())
                return R.error("上传失败");
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;
        try {
            mpfile.transferTo(dest);
        } catch (IOException e) {
            return R.fatal("上传失败" + e.getMessage());
        }
        song.setCreateTime(new Date());
        song.setUpdateTime(new Date());
        song.setPic(pic);
        song.setUrl(storeUrlPath);
        if (songMapper.insert(song) > 0) {
            return R.success("上传成功", storeUrlPath);
        } else {
            return R.error("上传失败");
        }
    }

    @Override
    public R songOfSongName(String name) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        return R.success(null, songMapper.selectList(queryWrapper));
    }

    @Override
    public R allSong() {
        return R.success(null, songMapper.selectList(null));
    }

    @Override
    public R urlOfSong(Integer id) {
        return R.success(null, songMapper.selectById(id).getUrl());
    }

    /**
     * @Description: 获取所有歌曲ID集合
     * @return: java.util.List<java.lang.Integer>
     **/
    @Override
    public List<Integer> getAllSongIds() {
        return songMapper.selectAllSongIds();
    }

    /**
     * @Description: 获取用户推荐数据
     * @CreateTime:
     * @return: com.example.online.music.common.R
     */
    @Override
    public R getRecommendSongList(Integer userId) {
        return R.success(null, songMapper.selectAllRecommendSongList(userId));
    }

}




