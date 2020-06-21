package com.wstro.service.setting;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wstro.common.dto.AjaxResult;
import com.wstro.entity.setting.SsoOrgCompany;
import com.wstro.entity.setting.SsoOrganization;

import java.util.List;

/**
 * <p>
 * 组织机构表 服务类
 * </p>
 *
 * @author zcb
 * @since 2017-10-30
 */
public interface ISsoOrganizationService extends IService<SsoOrganization> {
	
	Page<SsoOrganization> selectByList(Page<SsoOrganization> page, SsoOrgCompany ssoOrgCompany);
	
	List<SsoOrganization> selectOrgTreeByCode(String orgCode);

	AjaxResult addSaasInfo(JSONObject jsonObject);

	List<SsoOrganization> getOrgTree(SsoOrganization organization);
}
