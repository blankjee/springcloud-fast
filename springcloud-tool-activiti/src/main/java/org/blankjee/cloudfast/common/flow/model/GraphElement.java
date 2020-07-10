package org.blankjee.cloudfast.common.flow.model;

import lombok.Data;

/**
 * @Author blankjee
 * @Date 2020/7/10 11:08
 */
@Data
public class GraphElement {

    /**
     * 实例ID，历史的ID
     */
    private String id;

    /**
     * 节点名称，bpmn图形中的ID
     */
    private String name;
}
