package org.blankjee.cloudfast.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author blankjee
 * @Date 2020/7/6 19:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_FLOW_DEF")
public class FlowDef implements Serializable {
    /**
     * 业务流程定义ID
     */
    @TableId("DEF_ID")
    private Long defId;

    /**
     * 流程编码(流程图的编码)
     */
    @TableField("FLOW_CODE")
    private String flowCode;

    /**
     * 流程名称
     */
    @TableField("FLOW_NAME")
    private String flowName;

    /**
     * 状态(0:启用 1:禁用)
     */
    @TableField("FLOW_STATE")
    private Integer flowState;

}
