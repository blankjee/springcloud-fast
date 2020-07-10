package org.blankjee.cloudfast.common.flow.cmd;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import java.io.InputStream;

/**
 * @Desc 历史流程图绘制
 * @Author blankjee
 * @Date 2020/7/10 11:08
 */
public class HistoryProcessInstanceDiagramCmd implements Command<InputStream> {
    protected String historyProcessInstanceId;

    public HistoryProcessInstanceDiagramCmd(String historyProcessInstanceId) {
        this.historyProcessInstanceId = historyProcessInstanceId;
    }

    public InputStream execute(CommandContext commandContext) {
        try {
            CustomProcessDiagramGenerator customProcessDiagramGenerator = new CustomProcessDiagramGenerator();

            return customProcessDiagramGenerator
                    .generateDiagram(historyProcessInstanceId);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
