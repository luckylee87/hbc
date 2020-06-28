package com.dsg.service.setting.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.dao.setting.SsoRoleFuncMapper;
import com.dsg.dao.setting.SsoRoleMapper;
import com.dsg.entity.setting.SsoRole;
import com.dsg.service.setting.ISsoRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 岗位（角色）信息表 服务实现类
 * </p>
 *
 * @author jokylao
 * @since 2017-10-30
 */
@Service("ssoRoleService")
public class SsoRoleServiceImpl extends BaseServiceImpl<SsoRoleMapper, SsoRole> implements ISsoRoleService {

    @Autowired
    private SsoRoleMapper ssoRoleMapper;
    @Autowired
    private SsoRoleFuncMapper ssoRoleFuncMapper;

    @Override
    public Page<SsoRole> selectByPage(Page<SsoRole> page, SsoRole ssoRole) {
        //没有选择机构不执行查询
        if(null == ssoRole.getOrgCode()
                || "".equals(ssoRole.getOrgCode()))
            return page;
        page.setRecords(ssoRoleMapper.selectByPage(page, ssoRole));
        return page;
    }

    @Override
    public SsoRole selectById(String oid) {
        return ssoRoleMapper.selectById(oid);
    }

    @Override
    @Transactional
    public void deleteByOid(String oid) {
        ssoRoleMapper.deleteById(oid);
        Map<String, Object> params = new HashMap<>();
        params.put("ROLE_OID", oid);
        ssoRoleFuncMapper.deleteByMap(params);
    }

}
