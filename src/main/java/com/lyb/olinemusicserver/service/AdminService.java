package com.lyb.olinemusicserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.Admin;
import com.lyb.olinemusicserver.model.request.AdminRequest;
import jakarta.servlet.http.HttpSession;
public interface AdminService extends IService<Admin> {
    R verityPasswd(AdminRequest adminRequest, HttpSession session);
}