package org.blankjee.cloudfast.common.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.blankjee.cloudfast.common.entity.SysConstant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author blankjee
 * @Date 2020/7/6 17:13
 */
public class CookieUtil {

    /**
     * 获取cookie的key值
     * @param request
     * @return
     */
    public static String getCookieStr(HttpServletRequest request) {
        String cookieSet = "";
        Cookie cookie = ServletUtil.getCookie(request, SysConstant.ACTIVITI_COOKIE.toLowerCase());
        if (!ObjectUtil.isNull(cookie)) {
            cookieSet = cookie.getValue();
        }
        return cookieSet;
    }

    public static void delCookie(HttpServletResponse response, String cookieStr) {
        ServletUtil.addCookie(response, SysConstant.ACTIVITI_COOKIE, cookieStr, 0);
    }
}
