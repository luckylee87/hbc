package com.dsg.service.setting.impl;

import com.dsg.common.constants.CommonConstants;
import com.dsg.common.security.SystemAuthorizingUser;
import com.dsg.common.util.SingletonLoginUtils;
import com.dsg.common.util.UUIDGenerator;
import com.dsg.dao.setting.ProfunMapper;
import com.dsg.dao.setting.ProjectMapper;
import com.dsg.dao.setting.SsoRoleFuncMapper;
import com.dsg.dao.setting.SsoRoleMapper;
import com.dsg.entity.setting.AuthorizeTreeMode;
import com.dsg.entity.setting.SsoRole;
import com.dsg.entity.setting.SsoRoleFunc;
import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.service.setting.ISsoRoleFuncService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色-功能对应表 服务实现类
 * </p>
 *
 * @author jokylao
 * @since 2017-11-01
 */
@Service("ssoRoleFuncService")
public class SsoRoleFuncServiceImpl extends BaseServiceImpl<SsoRoleFuncMapper, SsoRoleFunc> implements ISsoRoleFuncService {

    @Autowired
    private SsoRoleFuncMapper ssoRoleFuncMapper;
    @Autowired
    private ProfunMapper profunMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private SsoRoleMapper ssoRoleMapper;

    @Override
    public List<AuthorizeTreeMode> selectAuthTreeAll(String userName,String orgCode) {
        //如果登录用户为空并且用户名和机构代码参数不为空，则为saas调用结构树
        SystemAuthorizingUser user = SingletonLoginUtils.getSystemUser();
        List<AuthorizeTreeMode> allProject = null;
        List<AuthorizeTreeMode> allProFunc = null;
        if(user != null && CommonConstants.USER_TYPE_SYS.equals(user.getUserType())) {  //系统管理员
            allProject = projectMapper.selectAllForSYSAuthTree();           //获取所有可显示的项目作为顶级节点
            allProFunc = profunMapper.selectAllForSYSAuthTree();            //获取项目与功能的数据作为子节点
        } else if(user != null){    //机构管理员
            allProject = ssoRoleFuncMapper.selectProjectForORGAuthTree(user.getOrgCode());
            allProFunc = profunMapper.selectAllForORGAuthTree(user.getUserName());
        } else {
            allProject = ssoRoleFuncMapper.selectProjectForORGAuthTree(orgCode);
            allProFunc = profunMapper.selectAllForORGAuthTree(userName);
        }
        if(null != allProFunc)
            allProFunc.addAll(allProject);
        return allProFunc;
    }

    @Override
    @Transactional
    public void saveRoleFunc(String oid, List<SsoRoleFunc> functionList) {
        //根据角色oid删除原先授权数据
        deleteByRoleOid(oid);
        if(CollectionUtils.isNotEmpty(functionList)) {
            SsoRole ssoRole = ssoRoleMapper.selectById(oid);
            for(SsoRoleFunc rf : functionList) {
                rf.setOid(UUIDGenerator.getUUID());
                rf.setOrgCode(ssoRole.getOrgCode());
            }
            saveBatch(functionList);
        }
    }

    @Override
    public void deleteByRoleOid(String oid) {
        Map<String, Object> params = new HashMap<>();
        params.put("ROLE_OID", oid);
        ssoRoleFuncMapper.deleteByMap(params);
    }


}
