package com.wstro.service.system.impl;

import com.wstro.dao.system.SystemMenuMapper;
import com.wstro.entity.system.SystemMenu;
import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.service.system.ISystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemMenuServiceImpl
 * 类描述：SystemMenu 表业务逻辑层接口实现类
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
@Service("systemMenuService")
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenuMapper, SystemMenu> implements ISystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Override
    public List<SystemMenu> selectSystemMenu() {
        List<SystemMenu> systemMenus = new ArrayList<SystemMenu>();
        // 查询一级目录
        List<SystemMenu> parentMenuList = systemMenuMapper.selectSystemMenu(1, 1);
        // 查询二级目录
        List<SystemMenu> childMenuList = systemMenuMapper.selectSystemMenu(1, 2);

        // 获取根级权限的子级权限
        for (SystemMenu parentMenu : parentMenuList) {
            recursionMenu(systemMenus, childMenuList, parentMenu);
        }
        return systemMenus;
    }

    @Override
    public List<SystemMenu> selectSystemMenuByRole(List<String> roleIdList) {
        List<SystemMenu> systemMenus = new ArrayList<SystemMenu>();
        // 查询一级目录
        List<SystemMenu> parentMenuList = systemMenuMapper.selectSystemMenuByRole(1, 1, roleIdList);
        // 查询二级目录
        List<SystemMenu> childMenuList = systemMenuMapper.selectSystemMenuByRole(1, 2, roleIdList);

        // 获取根级权限的子级权限
        for (SystemMenu parentMenu : parentMenuList) {
            recursionMenu(systemMenus, childMenuList, parentMenu);
        }
        return systemMenus;
    }

    /**
     * 递归得到每个权限的子级权限
     *
     * @param systemMenus   处理后的目录列表
     * @param childMenuList 二级目录列表
     * @param parentMenu    当前一级目录
     */
    private void recursionMenu(List<SystemMenu> systemMenus, List<SystemMenu> childMenuList, SystemMenu parentMenu) {
        List<SystemMenu> childMenus = new ArrayList<SystemMenu>();
        for (SystemMenu menu : childMenuList) {
            if (parentMenu.getId().equals(menu.getParentId())) {
                childMenus.add(menu);
            }
        }
        parentMenu.setChildMenuList(childMenus);
        systemMenus.add(parentMenu);
    }
}