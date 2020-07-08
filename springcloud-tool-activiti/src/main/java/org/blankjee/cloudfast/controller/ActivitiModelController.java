package org.blankjee.cloudfast.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.blankjee.cloudfast.common.entity.ResponseTableResult;
import org.blankjee.cloudfast.common.entity.ResponseUtil;
import org.blankjee.cloudfast.common.entity.ResultCode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/8 17:03
 */
@RestController
@RequestMapping("model")
public class ActivitiModelController {

    @Resource
    private RepositoryService repositoryService;

    @RequestMapping("queryModelList")
    @ResponseBody
    public ResponseTableResult<List<Model>> queryModelList(HttpServletRequest request) {
        int pageNo = Integer.valueOf(request.getParameter("page"));
        int pageSize = Integer.valueOf(request.getParameter("limit"));
        int firstResult = pageSize * (pageNo - 1);
        long count = repositoryService.createModelQuery().count();
        List<Model> list = repositoryService.createModelQuery().orderByCreateTime().desc().listPage(firstResult, pageSize);
        return ResponseUtil.makeTableRsp(0, count, list);
    }
}
