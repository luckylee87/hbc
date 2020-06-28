package com.dsg.dao;

import com.dsg.entity.SysRoleEntity;
import com.dsg.util.BaseDao;

/**
 * 角色管理
 * 
 */
public interface SysRoleDao extends BaseDao<SysRoleEntity> {

	/**
	 * 更新角色
	 * 
	 * @param role
	 *            SysRoleEntity
	 */
	void updateNoMapper(SysRoleEntity role);

}
