package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.constant.Constants;
import com.lyb.olinemusicserver.mapper.RecommendMapper;
import com.lyb.olinemusicserver.model.domain.Consumer;
import com.lyb.olinemusicserver.model.request.ConsumerRequest;
import com.lyb.olinemusicserver.service.ConsumerService;
import com.lyb.olinemusicserver.service.RecommendService;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.lyb.olinemusicserver.mapper.ConsumerMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer>
        implements ConsumerService{

    @Autowired
    private ConsumerMapper consumerMapper;
    @Autowired
    private RecommendMapper recommendMapper;

    @Override
    public R allUser() {
        return R.success("查询成功",consumerMapper.selectList(null));
    }

    @Override
    public R allUsers() {
        return R.success("查询成功",consumerMapper.selectList(null));
    }

    @Override
    public R allUser(Integer currentPage, Integer pageSize) {
        Page<Consumer> page = new Page<>(currentPage, pageSize);
        return R.success("查询成功",consumerMapper.selectPage(page, null));
    }

    /**
     * 删除用户
     */
    @Override
    @Transactional
    public R deleteUser(Integer id) {
        // 先删除推荐表中的相关数据
        recommendMapper.deleteByUserId(id);

        // 再删除用户记录
        if (consumerMapper.deleteById(id) > 0) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    /**
     * 删除多个用户
     */
    @Override
    @Transactional
    public R deleteUsers(String[] ids) {
        // 先删除推荐表中的相关数据
        for (String id : ids) {
            recommendMapper.deleteByUserId(Integer.parseInt(id));
        }

        //把数组中参数组织成SQL语句需要的格式
        StringBuffer sb = new StringBuffer();
        for (String i:ids) {
            sb.append(i+",");
        }
        sb.deleteCharAt(sb.length()-1);
        if (consumerMapper.deleteByIds(sb.toString()) > 0) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    @Override
    public R userOfId(int id) {
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return R.success("查询成功",consumerMapper.selectList(queryWrapper));
    }

    @Override
    public R loginStatus(ConsumerRequest loginRequest, HttpSession session) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (this.verityPasswd(username, password)) {
            session.setAttribute("username", username);
            Consumer consumer = new Consumer();
            consumer.setUsername(username);
            return R.success("登录成功", consumerMapper.selectList(new QueryWrapper<>(consumer)));
        } else {
            return R.error("用户名或密码错误");
        }
    }

    @Override
    public boolean verityPasswd(String username, String password) {
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        String secretPassword = DigestUtils.md5DigestAsHex((Constants.SALT + password).getBytes(StandardCharsets.UTF_8));

        queryWrapper.eq("password",secretPassword);
        return consumerMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 新增用户
     */
    @Override
    public R addUser(ConsumerRequest registryRequest) {
        if (this.existUser(registryRequest.getUsername())) {
            return R.warning("用户名已注册");
        }
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(registryRequest, consumer);
        //MD5加密
        String password = DigestUtils.md5DigestAsHex((Constants.SALT + registryRequest.getPassword()).getBytes(StandardCharsets.UTF_8));
        consumer.setPassword(password);
        //都用用
        if (StringUtils.isBlank(consumer.getPhoneNum())) {
            consumer.setPhoneNum(null);
        }
        if ("".equals(consumer.getEmail())) {
            consumer.setEmail(null);
        }
        consumer.setAvator("img/avatorImages/user.jpg");
        try {
            if (consumerMapper.insert(consumer) > 0) {
                return R.success("注册成功");
            } else {
                return R.error("注册失败");
            }
        } catch (DuplicateKeyException e) {
            return R.fatal(e.getMessage());
        }
    }
    private boolean existUser(String username) {
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return consumerMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public R updateUserMsg(ConsumerRequest updateRequest) {
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(updateRequest, consumer);
        if (consumerMapper.updateById(consumer) > 0) {
            return R.success("修改成功");
        } else {
            return R.error("修改失败");
        }
    }

    @Override
    public R updateUserAvator(MultipartFile avatorFile, int id) {
        String fileName = System.currentTimeMillis()
                            + "-" + avatorFile.getOriginalFilename();
        //路径 他这个会根据你的系统获取对应的文件分隔符
        String filePath = Constants.ASSETS_PATH
                + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "avatorImages";

        File file = new File(filePath);

        if (!file.exists() && !file.mkdir()) {return R.fatal("创建文件失败");}

        File dest = new File(filePath
                + System.getProperty("file.separator")
                + fileName);

        String imgPath = "img/avatorImages/" + fileName;
        try {
            avatorFile.transferTo(dest);
        } catch (IOException e) {
            return R.fatal("上传失败" + e.getMessage());
        }
        Consumer consumer = new Consumer();
        consumer.setId(id);
        consumer.setAvator(imgPath);
        if (consumerMapper.updateById(consumer) > 0) {
            return R.success("上传成功", imgPath);
        } else {
            return R.error("上传失败");
        }
    }

    @Override
    public R updatePassword(ConsumerRequest updatePasswordRequest) {

        if (!this.verityPasswd(updatePasswordRequest.getUsername(),updatePasswordRequest.getOldPassword())) {
            return R.error("密码输入错误");
        }

        Consumer consumer = new Consumer();
        consumer.setId(updatePasswordRequest.getId());
        String secretPassword = DigestUtils.md5DigestAsHex((Constants.SALT + updatePasswordRequest.getPassword()).getBytes(StandardCharsets.UTF_8));
        consumer.setPassword(secretPassword);

        if (consumerMapper.updateById(consumer) > 0) {
            return R.success("密码修改成功");
        } else {
            return R.error("密码修改失败");
        }
    }

    @Override
    public boolean existUserByPhone(String phoneNum) {
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_num",phoneNum);
        return consumerMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * @Description: 获取所有用户ID集合
     * @return: java.util.List<java.lang.Integer>
     **/
    @Override
    public List<Integer> getAllUserIds() {
        return consumerMapper.selectAllUserIds();
    }

}
