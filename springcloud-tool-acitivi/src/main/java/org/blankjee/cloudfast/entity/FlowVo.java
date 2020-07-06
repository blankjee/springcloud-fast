package org.blankjee.cloudfast.entity;

import lombok.Data;

/**
 * @Author blankjee
 * @Date 2020/7/6 19:50
 */
@Data
public class FlowVo {
    /**
     * 部署ID
     */
    private String deployMentId;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 流程key
     */
    private String flowKey;
}
