package org.blankjee.cloudfast.common.entity;

/**
 * @Author blankjee
 * @Date 2020/7/6 16:14
 */
public class SysConstant {
    // 字典表系统来源类型
    public static final int SYSTEM_CODE = 10;
    // 字典表业务类型
    public static final int BUSI_TYPE = 20;
    // 字典表请假类型
    public static final int VACATION_TYPE = 30;
    // 登录 cookie
    public static final String ACTIVITI_COOKIE = "ACTIVITI_COOKIE";
    // 审批通过
    public static final String APPROVAL_AGREE = "agree";
    // 审批驳回
    public static final String APPROVAL_REJECT = "reject";
    // 待提交
    public static final int SUBMITTED_STATE = 0;
    // 审核中
    public static final int REVIEW_STATE = 1;
    // 已废弃
    public static final int OBSOLETE_STATE = 2;
    // 已完成
    public static final int COMPLETED_STATE = 3;
}
