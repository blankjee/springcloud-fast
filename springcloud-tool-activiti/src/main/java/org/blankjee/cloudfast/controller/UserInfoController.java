package org.blankjee.cloudfast.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.blankjee.cloudfast.common.entity.ResponseResult;
import org.blankjee.cloudfast.common.entity.ResponseUtil;
import org.blankjee.cloudfast.common.entity.ResultCode;
import org.blankjee.cloudfast.common.entity.SysConstant;
import org.blankjee.cloudfast.common.util.CookieUtil;
import org.blankjee.cloudfast.entity.User;
import org.blankjee.cloudfast.service.ICacheService;
import org.blankjee.cloudfast.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author blankjee
 * @Date 2020/7/8 15:39
 */
@RequestMapping("user")
@RestController
@Slf4j
public class UserInfoController {

    @Resource
    private IUserService userService;

    @Resource
    private ICacheService cacheService;

    /**
     * 登录控制器
     * @param user
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResponseResult<User> doLogin(@RequestBody User user, HttpServletResponse response) {
        ResponseResult<User> userResponseResult = userService.doLogin(user.getUserName(), user.getUserPass());
        String token = IdUtil.fastSimpleUUID();
        // maxAgeInSecond: -1 关闭浏览器登录失效
        ServletUtil.addCookie(response, SysConstant.ACTIVITI_COOKIE, token, -1);
        cacheService.cacheObjData(token, userResponseResult.getData(), 60);
        return userResponseResult;
    }

    /**
     * 获取登录信息
     * @return
     */
    @RequestMapping("getLoginInfo")
    public ResponseResult<User> getLoginInfo() {
        User currentUser = userService.getCurrentUser();
        if (ObjectUtil.isNull(currentUser)) {
            return ResponseUtil.makeErrRsp(ResultCode.FAIL.code, "登录失败");
        }
        return ResponseUtil.makeOkRsp(currentUser);
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/outLogin")
    public ResponseResult<Object> outLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取Cookie进行删除
            String cookieStr = CookieUtil.getCookieStr(request);
            boolean delCacheRes = cacheService.delCacheByCode(cookieStr);
            log.info("删除缓存 key:" + cookieStr + "，返回结果：" + delCacheRes);
            return ResponseUtil.makeOkRsp("注销成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.makeErrRsp(ResultCode.FAIL.code, "注销异常：" + e.getMessage());
        }
    }
}
