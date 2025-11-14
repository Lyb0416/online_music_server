package com.lyb.olinemusicserver.controller;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.request.SingerRequest;
import com.lyb.olinemusicserver.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SingerController {
    @Autowired
    private SingerService singerService;
    /**
     * 返回所有歌手
     */
    @GetMapping("/singer")
    public R allSinger(@RequestParam(required = false) Integer curPage,
                       @RequestParam(required = false) Integer pageSize) {
        if (curPage == null || pageSize == null) {
            // 没传分页参数 → 返回所有歌手
            return singerService.allSingers();
        }
        // 传了分页参数 → 返回分页歌手
        return singerService.allSinger(curPage, pageSize);
    }

    // 添加歌手
    @PostMapping("/singer/add")
    public R addSinger(@RequestBody SingerRequest addSingerRequest) {
        return singerService.addSinger(addSingerRequest);
    }

    // 删除歌手
    @DeleteMapping("/singer/delete")
    public R deleteSinger(@RequestParam int id) {
        return singerService.deleteSinger(id);
    }

    // 批量删除歌手
    @DeleteMapping("/singer/deleteIds")
    public R deleteSingerIds(String[] id) {
        return singerService.deleteSingers(id);
    }

    // 更新歌手信息
    @PostMapping("/singer/update")
    public R updateSingerMsg(@RequestBody SingerRequest updateSingerRequest) {
        return singerService.updateSingerMsg(updateSingerRequest);
    }

    //根据歌手的性别获取歌手
    @GetMapping("/singerBySex")
    public R getSingerOfSex(@RequestParam int gender){
        return singerService.selectSingerByGender(gender);
    }

    // 更新歌手头像
    @PostMapping("/singer/avatar/update")
    public R updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id) {
        return singerService.updateSingerPic(avatorFile, id);
    }
}
