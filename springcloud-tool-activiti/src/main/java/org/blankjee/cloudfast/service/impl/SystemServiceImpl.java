package org.blankjee.cloudfast.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.blankjee.cloudfast.common.util.TreeUtil;
import org.blankjee.cloudfast.entity.MenuVo;
import org.blankjee.cloudfast.entity.SysDict;
import org.blankjee.cloudfast.entity.SystemMenu;
import org.blankjee.cloudfast.mapper.SysDictMapper;
import org.blankjee.cloudfast.mapper.SystemMenuMapper;
import org.blankjee.cloudfast.service.ISystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author blankjee
 * @Date 2020/7/6 20:04
 */
@Service
public class SystemServiceImpl implements ISystemService {
    @Resource
    private SystemMenuMapper systemMenuMapper;
    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public Map<String, Object> queryMenuList() {

        Map<String, Object> map = new HashMap<>(16);
        Map<String, Object> home = new HashMap<>(16);
        Map<String, Object> logo = new HashMap<>(16);

        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.orderByAsc("sort");
        List<SystemMenu> menuList = systemMenuMapper.selectList(queryWrapper);

        List<MenuVo> menuInfo = new ArrayList<>();
        for (SystemMenu e: menuList) {
            MenuVo menuVo = new MenuVo();
            menuVo.setId(e.getId());
            menuVo.setPid(e.getPid());
            menuVo.setHref(e.getHref());
            menuVo.setTitle(e.getTitle());
            menuVo.setIcon(e.getIcon());
            menuVo.setTarget(e.getTarget());
            menuInfo.add(menuVo);
        }
        map.put("menuInfo", TreeUtil.toTree(menuInfo, 0));
        home.put("title","首页");
        home.put("href","/page/welcome-1");//控制器路由,自行定义
        logo.put("title","activiti工作流");
        logo.put("image","images/logo.png");//静态资源文件路径,可使用默认的logo.png
        map.put("homeInfo", home);
        map.put("logoInfo", logo);
        return map;
    }

    @Override
    public List<SysDict> querySysDictInfo(int dictTypeCode) {
        return sysDictMapper.querySysDictInfo(dictTypeCode);
    }
}
