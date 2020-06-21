package com.wstro.dao.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wstro.entity.setting.SsoOrgCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
  * 组织机构-公司信息表 Mapper 接口
 * </p>
 *
 * @author zcb
 * @since 2017-10-30
 */
@Repository
public interface SsoOrgCompanyMapper extends BaseMapper<SsoOrgCompany> {
	
	List<SsoOrgCompany> selectByList(Page<SsoOrgCompany> page, SsoOrgCompany ssoOrgCompany);
	
}