package org.blankjee.cloudfast.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.blankjee.cloudfast.common.entity.ResponseResult;
import org.blankjee.cloudfast.common.entity.ResponseUtil;
import org.blankjee.cloudfast.common.entity.ResultCode;
import org.blankjee.cloudfast.common.entity.SysConstant;
import org.blankjee.cloudfast.entity.User;
import org.blankjee.cloudfast.mapper.UserMapper;
import org.blankjee.cloudfast.service.ICacheService;
import org.blankjee.cloudfast.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author blankjee
 * @Date 2020/7/7 19:40
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private HttpServletRequest request;

    @Resource
    private ICacheService cacheService;

    @Resource
    private UserMapper userMapper;

    @Override
    public User getCurrentUser() {
        Cookie cookie = ServletUtil.getCookie(request, SysConstant.ACTIVITI_COOKIE.toLowerCase());
        if (ObjectUtil.isNull(cookie)) {
            return null;
        }
        String loginToken = cookie.getValue();
        User cacheUser = (User) cacheService.getObjCacheByCode(loginToken);
        if (ObjectUtil.isNull(cacheUser)) {
            return null;
        }
        return cacheUser;
    }

    @Override
    public User queryUserById(String userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_ID", userId);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public ResponseResult<User> doLogin(String userName, String passWord) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_NAME", userName);
        User user = userMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(user)) {
            return ResponseUtil.makeErrRsp(ResultCode.FAIL.code, "用户不存在");
        }
        if (!StrUtil.equals(user.getUserPass(), SecureUtil.md5(passWord))) {
            return ResponseUtil.makeErrRsp(ResultCode.FAIL.code, "用户名/密码错误");
        }
        return ResponseUtil.makeOkRsp(user);
    }
}
