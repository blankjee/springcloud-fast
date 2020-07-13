package org.blankjee.cloudfast.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.blankjee.cloudfast.common.entity.PageBean;
import org.blankjee.cloudfast.common.entity.ResponseResult;
import org.blankjee.cloudfast.common.entity.ResponseTableResult;
import org.blankjee.cloudfast.common.entity.ResponseUtil;
import org.blankjee.cloudfast.entity.TaskVo;
import org.blankjee.cloudfast.service.ITaskProceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/13 8:59
 */

@Controller
@RequestMapping("task")
@Slf4j
public class TaskController {

    @Resource
    private ITaskProceService taskProceService;

    /**
     * 查询我的任务
     * @param pageBean
     * @return
     */
    @RequestMapping("queryMyTask")
    @ResponseBody
    public ResponseTableResult<List<TaskVo>> queryMyTask(PageBean pageBean) {
        Page<TaskVo> taskPage = taskProceService.queryMyTask(pageBean);
        return ResponseUtil.makeTableRsp(0, taskPage.getTotal(), taskPage.getRecords());
    }

    /**
     * 跳转流程审批
     * @param model
     * @param request
     * @return
     */
    @GetMapping("toTaskExec")
    public String toTaskExec(Model model, HttpServletRequest request) {
        String vacationId = request.getParameter("vacationId");
        TaskVo taskInfo = taskProceService.queryTaskById(Long.valueOf(vacationId));
        model.addAttribute("taskInfo", taskInfo);
        return "/page/taskExec";
    }

    /**
     * 流程审批
     * @param taskVo
     * @return
     */
    @PostMapping("completeTask")
    @ResponseBody
    public ResponseResult<String> completeTask(@RequestBody TaskVo taskVo) {
        taskProceService.completeTask(taskVo);
        return ResponseUtil.makeOkRsp();
    }
}
