package com.wstro.service.setting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wstro.entity.setting.SsoRole;

/**
 * <p>
 * 岗位（角色）信息表 服务类
 * </p>
 *
 * @author jokylao
 * @since 2017-10-30
 */
public interface ISsoRoleService extends IService<SsoRole> {

    Page<SsoRole> selectByPage(Page<SsoRole> page, SsoRole ssoRole);

    SsoRole selectById(String oid);

    void deleteByOid(String oid);
}
