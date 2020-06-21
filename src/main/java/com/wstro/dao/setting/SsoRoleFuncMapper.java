package com.wstro.dao.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wstro.entity.setting.AuthorizeTreeMode;
import com.wstro.entity.setting.SsoRoleFunc;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
  * 角色-功能对应表 Mapper 接口
 * </p>
 *
 * @author jokylao
 * @since 2017-11-01
 */
@Repository
public interface SsoRoleFuncMapper extends BaseMapper<SsoRoleFunc> {

    List<AuthorizeTreeMode> selectProjectForORGAuthTree(String orgCode);
    List<String> selectProjectCodeByUserName(String userName);

}