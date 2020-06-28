package com.dsg.service.setting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dsg.entity.setting.SsoOrgCompany;

/**
 * <p>
 * 组织机构-公司信息表 服务类
 * </p>
 *
 * @author zcb
 * @since 2017-10-30
 */
public interface ISsoOrgCompanyService extends IService<SsoOrgCompany> {
	
	Page<SsoOrgCompany> selectByList(Page<SsoOrgCompany> page, SsoOrgCompany ssoOrgCompany);

	boolean addCompany(SsoOrgCompany ssoOrgCompany, String projectCode) throws Exception;
}
