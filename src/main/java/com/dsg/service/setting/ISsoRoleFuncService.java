package com.dsg.service.setting;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsg.entity.setting.AuthorizeTreeMode;
import com.dsg.entity.setting.SsoRoleFunc;

import java.util.List;

/**
 * <p>
 * 角色-功能对应表 服务类
 * </p>
 *
 * @author jokylao
 * @since 2017-11-01
 */
public interface ISsoRoleFuncService extends IService<SsoRoleFunc> {

    List<AuthorizeTreeMode> selectAuthTreeAll(String userName, String orgCode);

    void saveRoleFunc(String oid, List<SsoRoleFunc> list);

    void deleteByRoleOid(String oid);
}
