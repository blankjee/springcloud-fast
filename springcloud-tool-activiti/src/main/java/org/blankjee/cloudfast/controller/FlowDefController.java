package org.blankjee.cloudfast.controller;

import org.blankjee.cloudfast.service.ISystemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author blankjee
 * @Date 2020/7/7 15:09
 */
@Controller
@RequestMapping("flow")
public class FlowDefController {
    @Resource
    private ISystemService systemService;


}
