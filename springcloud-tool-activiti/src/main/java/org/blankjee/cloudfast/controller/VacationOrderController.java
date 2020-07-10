package org.blankjee.cloudfast.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.blankjee.cloudfast.common.entity.*;
import org.blankjee.cloudfast.entity.*;
import org.blankjee.cloudfast.service.ILogService;
import org.blankjee.cloudfast.service.ISystemService;
import org.blankjee.cloudfast.service.IVacationOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/9 19:52
 */
@Controller
@RequestMapping("vacation")
public class VacationOrderController {

    @Resource
    private IVacationOrderService vacationOrderService;

    @Resource
    private ISystemService systemService;

    @Resource
    private ILogService logService;

    /**
     * 请假信息列表
     * @param pageBean
     * @return
     */
    @RequestMapping("queryList")
    @ResponseBody
    public ResponseTableResult<List<VacationOrderVo>> queryList(PageBean pageBean) {
        Page<VacationOrderVo> vacationOrderPage = vacationOrderService.queryVacationOrder(pageBean);
        return ResponseUtil.makeTableRsp(0, vacationOrderPage.getTotal(), vacationOrderPage.getRecords());
    }

    /**
     * 新建请假记录
     * @param model
     * @param orderNo
     * @return
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model, @RequestParam(value = "orderNo", required = false) String orderNo) {
        List<SysDict> typeList = systemService.querySysDictInfo(SysConstant.VACATION_TYPE);
        model.addAttribute("typeList", typeList);
        if (StrUtil.isNotBlank(orderNo)) {
            // 存在order号，进入编辑模式。
            VacationOrder vacationOrder = vacationOrderService.queryVacation(Long.valueOf(orderNo));
            model.addAttribute("vacationOrder", vacationOrder);
            return "page/editVacation";
        }
        // 新建请假
        return "page/addVacation";
    }

    /**
     * 审批详情页
     * @param model
     * @param orderNo
     * @return
     */
    @RequestMapping("provalDetail")
    public String provalDetail(Model model, @RequestParam("orderNo") String orderNo) {
        List<ProcessLog> logList = logService.queryOperLog(Long.valueOf(orderNo));
        model.addAttribute("logList", logList);
        return "/page/viewFlow";
    }

    /**
     * 提交请假申请
     * @param vacationId
     * @return
     */
    @PostMapping("submitApply")
    @ResponseBody
    public ResponseResult<String> submitApply(@RequestParam("vacationId") String vacationId) {
        boolean res = vacationOrderService.submitApply(Long.valueOf(vacationId));
        if (res) {
            return ResponseUtil.makeOkRsp();
        } else {
            return ResponseUtil.makeErrRsp(ResultCode.FAIL.code, "提交申请失败");
        }
    }

    /**
     * 删除请假条
     * @param vacationId
     * @return
     */
    @RequestMapping("delVacation")
    @ResponseBody
    public ResponseResult<String> delVacation(@RequestParam("vacationId") Long vacationId) {
        vacationOrderService.delVacation(vacationId);
        return ResponseUtil.makeOkRsp();
    }
}
