package com.dsg.dao.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsg.entity.setting.SsoOrgCompany;
import com.dsg.entity.setting.SsoOrganization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
  * 组织机构表 Mapper 接口
 * </p>
 *
 * @author zcb
 * @since 2017-10-30
 */
@Repository
public interface SsoOrganizationMapper extends BaseMapper<SsoOrganization> {
	
	List<SsoOrganization> selectByList(Page<SsoOrganization> page, SsoOrgCompany ssoOrgCompany);
	
	String getCode();

	List<SsoOrganization> selectOrgTreeInCode(@Param("parentList") List<SsoOrganization> parentList);

	List<SsoOrganization> getOrgTree(SsoOrganization organization);
}