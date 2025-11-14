package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.SongList;
import com.lyb.olinemusicserver.model.request.SongListRequest;
import com.lyb.olinemusicserver.service.SongListService;
import com.lyb.olinemusicserver.mapper.SongListMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author LENOVO
* @description 针对表【song_list】的数据库操作Service实现
* @createDate 2025-09-18 08:19:56
*/
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList>
    implements SongListService{

    @Autowired
    private SongListMapper songListMapper;

    /**
     * @Description: 查询歌单列表
     * @CreateTime:
     * @param: style 歌单分类
     * @return: cn.cn.com.niit.online.music.common.R
     */
    @Override
    public R songList(String style) {
        QueryWrapper<SongList> queryWrapper = null;
        if(StringUtils.hasLength(style)){//判断歌单分类参数是否为空，如果为空则不用构造 QW 对象封装查询条件。
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("style", style);
        }
        return R.success(null, songListMapper.selectList(queryWrapper));
    }

    /**
     * @Description: 新增歌单
     * @param: addSongListRequest 请求对象
     * @return: com.example.online.music.common.R
     **/
    @Override
    public R addSongList(SongListRequest addSongListRequest) {
        SongList songList = new SongList();
        BeanUtils.copyProperties(addSongListRequest, songList);
        String pic = "img/songListPic/default.jpg";
        songList.setPic(pic);
        if (songListMapper.insert(songList) > 0) {
            return R.success("添加成功");
        } else {
            return R.error("添加失败");
        }
    }

    /**
     * @Description: 编辑歌单
     * @param: updateSongListRequest 请求对象
     * @return: com.example.online.music.common.R
     **/
    @Override
    public R updateSongListMsg(SongListRequest updateSongListRequest) {
        SongList songList = new SongList();
        BeanUtils.copyProperties(updateSongListRequest, songList);
        if (songListMapper.updateById(songList) > 0) {
            return R.success("修改成功");
        } else {
            return R.error("修改失败");
        }
    }

    /**
     * @Description: 删除歌单
     * @return: com.example.online.music.common.R
     **/
    @Override
    public R deleteSongList(int id) {
        if (songListMapper.deleteById(id) > 0) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    @Override
    public R deleteSongLists(String[] ids) {
        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            sb.append(id).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        if (songListMapper.deleteIds(sb.toString()) > 0) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    @Override
    public R likeTitle(String title) {
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",title);
        return R.success(null, songListMapper.selectList(queryWrapper));
    }
}




