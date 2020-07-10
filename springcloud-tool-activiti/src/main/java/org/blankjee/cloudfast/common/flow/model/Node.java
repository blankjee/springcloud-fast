package org.blankjee.cloudfast.common.flow.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/10 11:08
 */
@Data
public class Node extends GraphElement {

    /**
     * 类型：比如userTask，startEvent
     */
    private String type;

    /**
     * 是否还未完成
     */
    private boolean active;

    /**
     * 进入这个节点的所有连线
     */
    private List<Edge> incomingEdges = new ArrayList<>();

    /**
     * 外出这个节点的所有连线
     */
    private List<Edge> outgoingEdges = new ArrayList<>();
}
