package org.blankjee.cloudfast.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.blankjee.cloudfast.common.entity.*;
import org.blankjee.cloudfast.entity.FlowDef;
import org.blankjee.cloudfast.entity.FlowRule;
import org.blankjee.cloudfast.entity.SysDict;
import org.blankjee.cloudfast.service.IFlowInfoService;
import org.blankjee.cloudfast.service.ISystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/7 15:09
 */
@Controller
@RequestMapping("flow")
public class FlowDefController {
    @Resource
    private ISystemService systemService;

    @Resource
    private IFlowInfoService flowInfoService;

    /**
     * 查询流程规则
     * @param pageBean
     * @return
     */
    @RequestMapping("queryFlowRule")
    @ResponseBody
    public ResponseTableResult<List<FlowRule>> queryFlowRule(PageBean pageBean) {
        Page<FlowRule> flowRulePage = flowInfoService.queryFlowRule(pageBean);
        return ResponseUtil.makeTableRsp(0, flowRulePage.getTotal(), flowRulePage.getRecords());
    }

    /**
     * 跳转到新增规则页面
     * @param model
     * @return
     */
    @RequestMapping("addFlowRule")
    public String addFlowRule(Model model) {
        List<SysDict> sysDicts = systemService.querySysDictInfo(SysConstant.SYSTEM_CODE);
        List<SysDict> busitypeList = systemService.querySysDictInfo(SysConstant.BUSI_TYPE);
        List<FlowDef> flowDefList = flowInfoService.queryFlowDefList();
        model.addAttribute("systemList", sysDicts);
        model.addAttribute("busitypeList", busitypeList);
        model.addAttribute("flowDefList", flowDefList);
        return "page/addFlowRule";
    }

    /**
     * 提交流程规则
     * @param flowRule
     * @return
     */
    @PostMapping("submitFlowRule")
    @ResponseBody
    public ResponseResult<String> submitFlow(@RequestBody FlowRule flowRule) {
        String resMsg = flowInfoService.insertFlowRule(flowRule);
        if (StrUtil.isNotBlank(resMsg)) {
            return ResponseUtil.makeErrRsp(ResultCode.FAIL.code, resMsg);
        }
        return ResponseUtil.makeOkRsp(resMsg);
    }

    /**
     * 删除流程规则
     * @param request
     * @return
     */
    @RequestMapping("delFlowRule")
    @ResponseBody
    public ResponseResult<String> delModel(HttpServletRequest request) {
        String ruleId = request.getParameter("ruleId");
        if (StrUtil.isBlank(ruleId)) {
            return ResponseUtil.makeErrRsp(ResultCode.FAIL.code, "规则ID不存在！");
        }
        flowInfoService.delFlowRuleBy(Long.valueOf(ruleId));
        return ResponseUtil.makeOkRsp("删除流程成功！");
    }
}
