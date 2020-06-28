package com.dsg.service.setting.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.common.constants.CommonConstants;
import com.dsg.common.util.UUIDGenerator;
import com.dsg.common.util.toolbox.StringUtil;
import com.dsg.dao.setting.*;
import com.dsg.entity.setting.*;
import com.dsg.service.setting.ISsoOrgCompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 组织机构-公司信息表 服务实现类
 * </p>
 *
 * @author zcb
 * @since 2017-10-30
 */
@Service("ssoOrgCompanyService")
public class SsoOrgCompanyServiceImpl extends BaseServiceImpl<SsoOrgCompanyMapper, SsoOrgCompany> implements ISsoOrgCompanyService {

	@Autowired
    private SsoOrgCompanyMapper ssoOrgCompanyMapper;
	@Autowired
	private SsoOrganizationMapper ssoOrganizationMapper;
	@Autowired
	private SsoRoleMapper ssoRoleMapper;
    @Autowired
    private SsoRoleFuncMapper ssoRoleFuncMapper;
    @Autowired
    private ProfunMapper profunMapper;

	@Override
	public Page<SsoOrgCompany> selectByList(Page<SsoOrgCompany> page,
                                            SsoOrgCompany ssoOrgCompany) {
		 page.setRecords(ssoOrgCompanyMapper.selectByList(page,ssoOrgCompany));
		return page;
	}

	@Override
	@Transactional
	public boolean addCompany(SsoOrgCompany ssoOrgCompany,String projectCode) throws Exception {
        //机构信息
        QueryWrapper entityWrapper = new QueryWrapper();
        entityWrapper.eq("CODE",ssoOrgCompany.getOrgCode());
        Integer count = ssoOrganizationMapper.selectCount(entityWrapper);
        if(count == 0) {
            SsoOrganization ssoOrganization = new SsoOrganization();
            ssoOrganization.setOid(UUIDGenerator.getUUID());
            ssoOrganization.setCode(ssoOrgCompany.getOrgCode());
            ssoOrganization.setName(ssoOrgCompany.getCompanyName());
            ssoOrganization.setOpTime(new Date());
            ssoOrganization.setOpUser("ssoadmin");
            ssoOrganization.setDescription("接口服务创建");
            ssoOrganizationMapper.insert(ssoOrganization);
        }
        //企业信息
        if (StringUtil.isEmpty(ssoOrgCompany.getCusCode())) {
            ssoOrgCompany.setCusCode(ssoOrgCompany.getOrgCode());
        }
        QueryWrapper entityWrapper2 = new QueryWrapper();
        entityWrapper2.eq("CUS_CODE",ssoOrgCompany.getOrgCode());
        entityWrapper2.eq("ORG_CODE",ssoOrgCompany.getOrgCode());
        Integer count2 = ssoOrgCompanyMapper.selectCount(entityWrapper2);
        if(count2 == 0) {
            ssoOrgCompany.setOid(UUIDGenerator.getUUID());
            ssoOrgCompany.setOpTime(new Date());
            ssoOrgCompany.setOpUser("ssoadmin");
            ssoOrgCompanyMapper.insert(ssoOrgCompany);
        }
		//创建机构管理员角色
        String roleId = UUIDGenerator.getUUID();
        String roleId2 = UUIDGenerator.getUUID();
        QueryWrapper entityWrapper3 = new QueryWrapper();
        entityWrapper3.eq("ROLE_NAME","机构管理员");
        entityWrapper3.eq("ORG_CODE",ssoOrgCompany.getOrgCode());
        Integer count3 = ssoRoleMapper.selectCount(entityWrapper3);
        if(count3 == 0) {
            SsoRole ssoRole = new SsoRole();
            ssoRole.setOid(roleId);
            ssoRole.setIsValid(CommonConstants.Y);
            ssoRole.setRoleName("机构管理员");
            ssoRole.setOpUser("ssoadmin");
            ssoRole.setOpTime(new Date());
            ssoRole.setOrderIndex(new BigDecimal(1));
            ssoRole.setDescription("接口服务创建");
            ssoRole.setOrgCode(ssoOrgCompany.getOrgCode());
            ssoRole.setOrgName(ssoOrgCompany.getCompanyName());
            ssoRoleMapper.insert(ssoRole);
        }
        //创建普通用户角色
        QueryWrapper entityWrapper4 = new QueryWrapper();
        entityWrapper4.eq("ROLE_NAME","普通用户");
        entityWrapper4.eq("ORG_CODE",ssoOrgCompany.getOrgCode());
        Integer count4 = ssoRoleMapper.selectCount(entityWrapper4);
        if(count4 == 0) {
            SsoRole ssoRole = new SsoRole();
            ssoRole.setOid(roleId2);
            ssoRole.setIsValid(CommonConstants.Y);
            ssoRole.setRoleName("普通用户");
            ssoRole.setOpUser("ssoadmin");
            ssoRole.setOpTime(new Date());
            ssoRole.setOrderIndex(new BigDecimal(2));
            ssoRole.setDescription("接口服务创建");
            ssoRole.setOrgCode(ssoOrgCompany.getOrgCode());
            ssoRole.setOrgName(ssoOrgCompany.getCompanyName());
            ssoRoleMapper.insert(ssoRole);
        }

        QueryWrapper<Profun> entityWrapper1 = new QueryWrapper<>();
        entityWrapper1.eq("PROJECT_CODE",projectCode);
        List<Profun> profuns =profunMapper.selectList(entityWrapper1);

        for(Profun profun:profuns){
            SsoRoleFunc ssoRoleFunc = new SsoRoleFunc();
            ssoRoleFunc.setOid(UUIDGenerator.getUUID());
            ssoRoleFunc.setOrgCode(ssoOrgCompany.getOrgCode());
            ssoRoleFunc.setFunctionCode(profun.getFunctionCode());
            ssoRoleFunc.setFunctionName(profun.getFunctionName());
            ssoRoleFunc.setProjectCode(projectCode);
            ssoRoleFunc.setRoleOid(roleId);
            ssoRoleFunc.setRoleName("机构管理员");
            ssoRoleFunc.setOpTime(new Date());
            ssoRoleFunc.setOpUser("ssoadmin");
            ssoRoleFuncMapper.insert(ssoRoleFunc);

            SsoRoleFunc ssoRoleFunc2 = new SsoRoleFunc();
            BeanUtils.copyProperties(ssoRoleFunc,ssoRoleFunc2);
            ssoRoleFunc2.setOid(UUIDGenerator.getUUID());
            ssoRoleFunc2.setRoleOid(roleId2);
            ssoRoleFunc2.setRoleName("普通用户");
            ssoRoleFuncMapper.insert(ssoRoleFunc2);
        }
		return true;
	}
}
