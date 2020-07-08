package org.blankjee.cloudfast.controller;

import lombok.extern.slf4j.Slf4j;
import org.blankjee.cloudfast.common.entity.ResponseResult;
import org.blankjee.cloudfast.common.entity.ResponseUtil;
import org.blankjee.cloudfast.common.entity.SysConstant;
import org.blankjee.cloudfast.entity.SysDict;
import org.blankjee.cloudfast.service.ISystemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author blankjee
 * @Date 2020/7/6 19:57
 */
@RestController
@Slf4j
public class IndexController {
    @Resource
    private ISystemService systemService;

    @RequestMapping("queryMenu")
    public Map<String, Object> queryMenu() {
        Map<String, Object> map = systemService.queryMenuList();
        return map;
    }

    @RequestMapping("querySysDict")
    public ResponseResult<List<SysDict>> querySysDict() {
        List<SysDict> sysDicts = systemService.querySysDictInfo(SysConstant.SYSTEM_CODE);
        return ResponseUtil.makeOkRsp(sysDicts);
    }
}
