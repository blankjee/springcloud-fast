package org.blankjee.cloudfast.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author blankjee
 * @Date 2020/7/8 15:26
 */
@Controller
public class PageController {

    /**
     * 首页
     * @return
     */
    @GetMapping("/")
    public String toIndexPage() {
        return "index";
    }

    /**
     * 控制器控制页面的转发
     * @param page
     * @return
     */
    @GetMapping("/{page}")
    public String toPage(@PathVariable String page) {
        if (StrUtil.equals("favicon.ico", page)) {
            return "favicon.ico";
        }
        return page;
    }

    /**
     * 跳转流程相关页面
     * @param page
     * @return
     */
    @GetMapping("/model/{page}")
    public String toModelPage(@PathVariable String page) {
        return "model/" + page;
    }

    /**
     * 跳转其他相关页面
     * @param page
     * @return
     */
    @GetMapping("/page/{page}")
    public String toOtherPage(@PathVariable String page) {
        return "page/" + page;
    }
}
