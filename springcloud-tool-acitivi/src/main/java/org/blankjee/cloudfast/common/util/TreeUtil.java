package org.blankjee.cloudfast.common.util;

import org.blankjee.cloudfast.entity.MenuVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/6 20:23
 */
public class TreeUtil {

    public static List<MenuVo> toTree(List<MenuVo> treeList, Integer pid) {
        List<MenuVo> resultList = new ArrayList<MenuVo>();
        for (MenuVo parent: treeList) {
            if (pid.equals(parent.getPid())) {
                resultList.add(findChildren(parent, treeList));
            }
        }
        return resultList;
    }

    private static MenuVo findChildren(MenuVo parent, List<MenuVo> treeList) {
        for (MenuVo child: treeList) {
            if (parent.getId().equals(child.getPid())) {
                if (parent.getChild() == null) {
                    parent.setChild(new ArrayList<>());
                }
                parent.getChild().add(findChildren(child, treeList));
            }
        }
        return parent;
    }
}
