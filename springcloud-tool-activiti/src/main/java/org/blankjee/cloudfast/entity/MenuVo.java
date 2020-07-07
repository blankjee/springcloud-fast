package org.blankjee.cloudfast.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/6 19:50
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MenuVo {

    private Integer id;

    private Integer pid;

    private String title;

    private String icon;

    private String href;

    private String target;

    private List<MenuVo> child;
}
