package com.wstro.service.setting.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.common.util.UUIDGenerator;
import com.wstro.entity.setting.*;
import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.dao.setting.SsoOrganizationMapper;
import com.wstro.service.setting.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构表 服务实现类
 * </p>
 *
 * @author zcb
 * @since 2017-10-30
 */
@Service("ssoOrganizationService")
public class SsoOrganizationServiceImpl extends BaseServiceImpl<SsoOrganizationMapper, SsoOrganization> implements ISsoOrganizationService {

	@Autowired
    private SsoOrganizationMapper ssoOrganizationMapper;
	@Autowired
	private ISsoOrgCompanyService ssoOrgCompanyService;
	@Autowired
	private ISsoRoleService ssoRoleService;
	@Autowired
	private ISsoRoleUserService ssoRoleUserService;
	@Autowired
	private ISsoUserService ssoUserService;
	
	@Override
	public Page<SsoOrganization> selectByList(Page<SsoOrganization> page,
                                              SsoOrgCompany ssoOrgCompany) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SsoOrganization> selectOrgTreeByCode(String orgCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("CODE", orgCode);
		List<SsoOrganization> list = ssoOrganizationMapper.selectByMap(params);
		recursionGet(list, list);
		return list;
	}

	private void recursionGet(List<SsoOrganization> parentList, List<SsoOrganization> list) {
		List<SsoOrganization> tmp = ssoOrganizationMapper.selectOrgTreeInCode(parentList);
		if(null != tmp && tmp.size() > 0) {
			list.addAll(tmp);
			recursionGet(tmp, list);
		}
	}

	@Transactional
	@Override
	public AjaxResult addSaasInfo(JSONObject jsonObject) {
		//完善机构信息
		SsoOrganization ssoOrganization = new SsoOrganization();
		ssoOrganization.setOid(UUIDGenerator.getUUID());
		ssoOrganization.setCode(jsonObject.get("code").toString());
		ssoOrganization.setName(jsonObject.get("name").toString());
		//判断企业海关编码是否已经存在
		QueryWrapper<SsoOrgCompany> entityWrapper1 = new QueryWrapper<>();
		entityWrapper1.eq("CUS_CODE",jsonObject.get("cusCode").toString());
		ssoOrgCompanyService.list(entityWrapper1);
		List<SsoOrgCompany> ssoOrgCompanies = ssoOrgCompanyService.list(entityWrapper1);
		if (ssoOrgCompanies.size() > 0) {
			return new AjaxResult(MessageConstants.SSO_ORGCOMPANY_CUSCODE_EXIST);
		}
		//完善公司信息
		SsoOrgCompany ssoOrgCompany = new SsoOrgCompany();
		ssoOrgCompany.setOid(UUIDGenerator.getUUID());
		ssoOrgCompany.setOrgCode(ssoOrganization.getCode());
                /*判断主管海关和场所代码是否为null*/
		ssoOrgCompany.setMasterCuscd(jsonObject.get("masterCuscd").toString());
		if(jsonObject.containsKey("managePlace")){
			ssoOrgCompany.setAreaCode( jsonObject.get("managePlace").toString() );
		}
		ssoOrgCompany.setCompanyName( jsonObject.get("name").toString() );
		ssoOrgCompany.setShortName( jsonObject.get("name").toString() );
		ssoOrgCompany.setCusCode( jsonObject.get("cusCode").toString() );
		ssoOrgCompany.setCreditCode( jsonObject.get("creditCode").toString() );
		if(jsonObject.containsKey("linkMan")){
			ssoOrgCompany.setLinkMan( jsonObject.get("linkMan").toString() );
		}
		if(jsonObject.containsKey("linkTel")){
			ssoOrgCompany.setLinkTel( jsonObject.get("linkTel").toString() );
		}
		if(jsonObject.containsKey("linkEmail")){
			ssoOrgCompany.setLinkEmail( jsonObject.get("linkEmail").toString() );
		}
		if(jsonObject.containsKey("address")){
			ssoOrgCompany.setAddress( jsonObject.get("address").toString() );
		}
		if(jsonObject.containsKey("masterCiqcd")){
			ssoOrgCompany.setMasterCiqcd( jsonObject.get("masterCiqcd").toString() );
		}
		//生成默认角色
		SsoRole ssoRole = new SsoRole();
		ssoRole.setOid(UUIDGenerator.getUUID());
		ssoRole.setIsValid("Y");
		ssoRole.setOrgCode(ssoOrganization.getCode());
		ssoRole.setOrgName(ssoOrganization.getName());
		ssoRole.setRoleName("SAAS角色");
		QueryWrapper<SsoUser> entityWrapper2 = new QueryWrapper<>();
		entityWrapper2.eq("USER_NAME", jsonObject.get("userName").toString());
		//账号默认绑定默认生成的角色
		SsoRoleUser ssoRoleUser = new SsoRoleUser();
		ssoRoleUser.setOid(UUIDGenerator.getUUID());
		ssoRoleUser.setUserName(jsonObject.get("userName").toString());
		ssoRoleUser.setRoleOid(ssoRole.getOid());
		//更新用户账户的所属企业信息
		SsoUser ssoUser = ssoUserService.getOne(entityWrapper2);
		if(jsonObject.containsKey("icCardNo")){
			ssoUser.setIcCode( jsonObject.get("icCardNo").toString() );
		}
		ssoUser.setOrgCode(ssoOrganization.getCode());
		ssoUserService.updateById(ssoUser);
		//插入机构信息,公司信息,默认角色,用户绑定角色
		this.save(ssoOrganization);
		ssoOrgCompanyService.save(ssoOrgCompany);
		ssoRoleService.save(ssoRole);
		ssoRoleUserService.save(ssoRoleUser);
		return new AjaxResult(MessageConstants.SSO_STATUS_SUCCESS);
	}

	@Override
	public List<SsoOrganization> getOrgTree(SsoOrganization organization){
		return ssoOrganizationMapper.getOrgTree(organization);
	}
}
