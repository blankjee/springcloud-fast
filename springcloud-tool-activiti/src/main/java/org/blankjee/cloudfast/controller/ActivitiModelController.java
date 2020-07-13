package org.blankjee.cloudfast.controller;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.lang3.StringUtils;
import org.blankjee.cloudfast.common.entity.ResponseResult;
import org.blankjee.cloudfast.common.entity.ResponseTableResult;
import org.blankjee.cloudfast.common.entity.ResponseUtil;
import org.blankjee.cloudfast.common.entity.ResultCode;
import org.blankjee.cloudfast.common.flow.cmd.HistoryProcessInstanceDiagramCmd;
import org.blankjee.cloudfast.entity.FlowDef;
import org.blankjee.cloudfast.service.IFlowInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/8 17:03
 */
@RestController
@Slf4j
@RequestMapping("model")
public class ActivitiModelController {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private IFlowInfoService flowInfoService;

    @Resource
    private ManagementService managementService;

    @RequestMapping("/createModel")
    public void createModel(HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model model = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "name");
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, "description");
            model.setMetaInfo(modelObjectNode.toString());
            model.setName("name");
            model.setKey(StringUtils.defaultString("key"));

            repositoryService.saveModel(model);
            repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));

            request.setAttribute("modelId", model.getId());
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + model.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 查询模型列表
     * @param request
     * @return
     */
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

    /**
     * 保存流程模型
     * @param modelId
     * @param name
     * @param json_xml
     * @param svg_xml
     * @param description
     */
    @PutMapping(value = {"/{modelId}/save"})
    @ResponseStatus(HttpStatus.OK)
    public void saveModel(@PathVariable String modelId, @RequestParam("name") String name,
                          @RequestParam("json_xml") String json_xml, @RequestParam("svg_xml") String svg_xml,
                          @RequestParam("description") String description) {
        try {
            Model model = repositoryService.getModel(modelId);
            ObjectNode modelJson = (ObjectNode) this.objectMapper.readTree(model.getMetaInfo());
            modelJson.put("name", name);
            modelJson.put("description", description);
            model.setMetaInfo(modelJson.toString());
            model.setName(name);

            repositoryService.saveModel(model);
            repositoryService.addModelEditorSource(model.getId(), json_xml.getBytes("utf-8"));

            InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);
            PNGTranscoder transcoder = new PNGTranscoder();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);
            transcoder.transcode(input, output);
            byte[] result = outputStream.toByteArray();
            repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outputStream.close();
        } catch (Exception e) {
            throw new ActivitiException("保存模型失败", e);
        }
    }

    /**
     * 部署流程
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "deployModel")
    @ResponseBody
    public ResponseResult<String> deployModel(HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        String modelId = request.getParameter("modelId");
        if (StringUtils.isNoneBlank(modelId)) {
            Model modelData = this.repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString("A" + processName, new String(bpmnBytes,"utf-8")).deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
            redirectAttributes.addFlashAttribute("message", "部署成功，部署ID=" + deployment.getId());
            //向流程定义表保存数据
            FlowDef flowDef = new FlowDef();
            List<Process> processes = model.getProcesses();
            for (Process process : processes) {
                flowDef.setFlowCode(process.getId());
                flowDef.setFlowName(process.getName());
            }
            flowInfoService.insertFlowDef(flowDef);
            return ResponseUtil.makeOkRsp("部署成功");
        }
        return ResponseUtil.makeErrRsp(ResultCode.NOT_FOUND.code,"系统异常,流程ID不存在");
    }


    /**
     * 查询流程图
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("queryFlowImg")
    public void queryFlowImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String flowId = request.getParameter("flowId");
        InputStream inputStream = null;
        if (StrUtil.isBlank(flowId) || StrUtil.equals("null", flowId)) {
            inputStream = this.getClass().getClassLoader().getResourceAsStream("static/images/no_flowInfo.png");
        } else {
            Command<InputStream> cmd = new HistoryProcessInstanceDiagramCmd(flowId);
            inputStream = managementService.executeCommand(cmd);
        }
        BufferedOutputStream bout = new BufferedOutputStream(response.getOutputStream());
        try {
            if (inputStream == null) {
                // 展示默认图片
                inputStream = this.getClass().getResourceAsStream("/images/no_flowInfo.png");
            }
            byte b[] = new byte[1024];
            int len = inputStream.read(b);
            while (len > 0) {
                bout.write(b, 0, len);
                len = inputStream.read(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bout.close();
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 删除流程
     * @param request
     * @return
     */
    @RequestMapping("delModel")
    @ResponseBody
    public ResponseResult<String> delModel(HttpServletRequest request) {
        String modelId = request.getParameter("modelId");
        if (StrUtil.isBlank(modelId)) {
            return ResponseUtil.makeErrRsp(ResultCode.FAIL.code, "流程ID不存在！");
        }
        repositoryService.deleteModel(modelId);
        return ResponseUtil.makeOkRsp("删除流程成功！");
    }
}
