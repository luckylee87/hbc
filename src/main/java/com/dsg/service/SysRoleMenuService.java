package com.dsg.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsg.entity.SysRoleMenuEntity;

/**
 * 角色与菜单对应关系
 * 
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

	void saveOrUpdate(Long roleId, List<Long> menuIdList);

	/**
	 * 根据角色ID，获取菜单ID列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

}
