package org.blankjee.cloudfast.entity;

import lombok.Data;

/**
 * @Author blankjee
 * @Date 2020/7/6 19:50
 */
@Data
public class VacationOrderVo extends VacationOrder {

    private String orderNo;

    private String userName;

    private String flowId;

    private String typeName;
}
