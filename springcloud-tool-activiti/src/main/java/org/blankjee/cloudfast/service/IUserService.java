package org.blankjee.cloudfast.service;

import org.blankjee.cloudfast.common.entity.ResponseResult;
import org.blankjee.cloudfast.entity.User;

/**
 * @Author blankjee
 * @Date 2020/7/7 19:37
 */
public interface IUserService {

    /**
     * 获取当前登录的用户
     * @return
     */
    User getCurrentUser();

    /**
     * 根据用户名查询用户信息
     * @param userId
     * @return
     */
    User queryUserById(String userId);

    /**
     * 用户登录
     * @param userName
     * @param passWord
     * @return
     */
    ResponseResult<User> doLogin(String userName, String passWord);
}
