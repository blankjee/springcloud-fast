package org.blankjee.cloudfast.common.flow.model;

import lombok.Data;

/**
 * @Author blankjee
 * @Date 2020/7/10 11:11
 */
@Data
public class Edge extends GraphElement {

    /**
     * 起点节点
     */
    private Node src;

    /**
     * 终点节点
     */
    private Node desc;

    /**
     * 循环
     */
    private boolean cycle;
}
