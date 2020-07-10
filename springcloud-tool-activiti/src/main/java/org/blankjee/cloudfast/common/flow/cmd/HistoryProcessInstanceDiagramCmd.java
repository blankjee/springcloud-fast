package org.blankjee.cloudfast.common.flow.cmd;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import java.io.InputStream;

/**
 * @Author blankjee
 * @Date 2020/7/10 16:19
 */
public class HistoryProcessInstanceDiagramCmd implements Command<InputStream> {

    protected String historyProcessInstanceId;

    public HistoryProcessInstanceDiagramCmd(String historyProcessInstanceId) {
        this.historyProcessInstanceId = historyProcessInstanceId;
    }

    @Override
    public InputStream execute(CommandContext commandContext) {
        try {
            CustomProcessDiagramGenerator customProcessDiagramGenerator = new CustomProcessDiagramGenerator();
            return customProcessDiagramGenerator.generateDiagram(historyProcessInstanceId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
