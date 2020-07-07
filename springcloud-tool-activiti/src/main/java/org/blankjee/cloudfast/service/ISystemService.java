package org.blankjee.cloudfast.service;

import org.blankjee.cloudfast.entity.SysDict;

import java.util.List;
import java.util.Map;

/**
 * @Author blankjee
 * @Date 2020/7/6 19:58
 */
public interface ISystemService {
    /**
     * 查询菜单列表
     * @return
     */
    Map<String, Object> queryMenuList();

    /**
     * 查询字典信息
     * @param dictTypeCode
     * @return
     */
    List<SysDict> querySysDictInfo(int dictTypeCode);
}
