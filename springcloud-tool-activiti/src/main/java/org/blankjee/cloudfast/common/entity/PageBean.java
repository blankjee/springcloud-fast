package org.blankjee.cloudfast.common.entity;

import lombok.Data;

/**
 * @Author blankjee
 * @Date 2020/7/6 16:19
 */
@Data
public class PageBean {
    // 当前页码
    private long page;
    // 每页显示条数
    private long limit;
}
