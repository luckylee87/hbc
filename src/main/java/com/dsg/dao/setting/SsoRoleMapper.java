package com.dsg.dao.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsg.entity.setting.SsoRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
  * 岗位（角色）信息表 Mapper 接口
 * </p>
 *
 * @author jokylao
 * @since 2017-10-30
 */
@Repository
public interface SsoRoleMapper extends BaseMapper<SsoRole> {

    List<SsoRole> selectByPage(Page<SsoRole> page, SsoRole ssoRole);

    SsoRole selectById(@Param("oid") String oid);

}