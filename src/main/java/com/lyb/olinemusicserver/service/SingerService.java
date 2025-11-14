package com.lyb.olinemusicserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.Singer;
import com.lyb.olinemusicserver.model.request.SingerRequest;
import org.springframework.web.multipart.MultipartFile;

/**
* @author LENOVO
* @description 针对表【singer】的数据库操作Service
* @createDate 2025-09-16 15:06:04
*/
public interface SingerService extends IService<Singer> {
    R allSinger(Integer curPage, Integer pageSize);

    R allSingers();

    R addSinger(SingerRequest addSingerRequest);
    
    R deleteSinger(int id);

    R deleteSingers(String[] id);

    R updateSingerMsg(SingerRequest updateSingerRequest);

    R selectSingerByGender(int gender);

    R updateSingerPic(MultipartFile avatorFile, int id);
}
