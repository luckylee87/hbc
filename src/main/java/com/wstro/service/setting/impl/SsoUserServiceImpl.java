package com.wstro.service.setting.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wstro.common.constants.Constant;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.dao.setting.*;
import com.wstro.entity.setting.*;
import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.common.constants.CommonConstants;
import com.wstro.common.util.UUIDGenerator;
import com.wstro.common.util.toolbox.StringUtil;
import com.wstro.service.setting.*;
import com.wstro.common.constants.APIMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("ssoUserService")
public class SsoUserServiceImpl extends BaseServiceImpl<SsoUserMapper,SsoUser> implements ISsoUserService{

    @Autowired
    private SsoUserMapper ssoUserMapper;
    @Autowired
    private SsoRoleMapper ssoRoleMapper;
    @Autowired
    private ISsoRoleService ssoRoleService;
    @Autowired
    private SsoRoleUserMapper ssoRoleUserMapper;
    @Autowired
    private ISsoRoleUserService ssoRoleUserService;
    @Autowired
    private SsoOrganizationMapper ssoOrganizationMapper;
    @Autowired
    private ISsoRoleFuncService ssoRoleFuncService;
    @Autowired
    private ISsoOrganizationService ssoOrganizationService;
    @Autowired
    private SsoUserFilterMapper ssoUserFilterMapper;

    @Override
    public Page<SsoUser> selectByList(Page<SsoUser> page, SsoUser ssoUser){
        page.setRecords(ssoUserMapper.selectByList(page,ssoUser));
        return page;
    }

    @Override
    public SsoUser login(String userName,String password){

        return ssoUserMapper.login(userName,password);
    }

    @Override
    @Transactional
    public boolean addUser(SsoUser ssoUser) {
        ssoUser.setOid(UUIDGenerator.getUUID());
        if(!this.save(ssoUser)) {
            return false;
        }
        //设置用户过滤条件，默认当前机构
        //先删除当前用户的过滤条件，避免旧数据残留
        QueryWrapper<SsoUserFilter> entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("USER_NAME",ssoUser.getUserName());
        ssoUserFilterMapper.delete(entityWrapper);

        SsoUserFilter userFilter = new SsoUserFilter();
        userFilter.setOid(UUIDGenerator.getUUID());
        userFilter.setUserName(ssoUser.getUserName());
        userFilter.setIsNeedFilter(Constant.Y);
        userFilter.setOrgCodeArray(ssoUser.getOrgCode());
        ssoUserFilterMapper.insert(userFilter);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteUser(String oid) {
        SsoUser user = ssoUserMapper.selectById(oid);
        int count = ssoUserMapper.deleteById(oid);
        if(count > 0) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("USER_NAME", user.getUserName());
            ssoRoleUserMapper.deleteByMap(params);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean addOrgUser(SsoUser ssoUser) throws Exception {
        ssoUser.setOpTime(new Date());
        ssoUser.setOpUser("ssoadmin");
        ssoUser.setOrderIndex(new BigDecimal(1));
        if(StringUtil.isEmpty(ssoUser.getTrueName())){
            ssoUser.setTrueName(ssoUser.getUserName());
        }
        if(addUser(ssoUser)){
            QueryWrapper<SsoRole> entityWrapper = new QueryWrapper<>();
            entityWrapper.eq("ORG_CODE",ssoUser.getOrgCode());
            if(CommonConstants.USER_TYPE_ORG.equals(ssoUser.getUserType())){
                entityWrapper.eq("ROLE_NAME","机构管理员");
            }
            else if(CommonConstants.USER_TYPE_COM.equals(ssoUser.getUserType())){
                entityWrapper.eq("ROLE_NAME","普通用户");
            }
            List<SsoRole> ssoRoles = ssoRoleMapper.selectList(entityWrapper);
            if(ssoRoles.size() >0){
                SsoRoleUser ssoRoleUser = new SsoRoleUser();
                ssoRoleUser.setOid(UUIDGenerator.getUUID());
                ssoRoleUser.setOpTime(new Date());
                ssoRoleUser.setOpUser("ssoadmin");
                ssoRoleUser.setRoleOid(ssoRoles.get(0).getOid());
                ssoRoleUser.setUserName(ssoUser.getUserName());
                ssoRoleUserMapper.insert(ssoRoleUser);
            }
        }
        return true;
    }

    @Override
    public APIMessage  checkUser(SsoUser ssoUser) throws Exception {
        //判断用户名是否存在
        QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("USER_NAME",ssoUser.getUserName());
        Integer count = ssoUserMapper.selectCount(entityWrapper);
        if(count >0){
            return APIMessage.API_STATUS_USER_EXISTS;
        }
        //用email作为用户名时，判断email是否存在
        entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("EMAIL",ssoUser.getUserName());
        count = ssoUserMapper.selectCount(entityWrapper);
        if(count >0){
            return APIMessage.API_STATUS_USEEMAIL_EXISTS;
        }
        //用手机号作为用户名时，判断手机号是否存在
        entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("TEL_NUM",ssoUser.getUserName());
        count = ssoUserMapper.selectCount(entityWrapper);
        if(count >0){
            return APIMessage.API_STATUS_USEPHONE_EXISTS;
        }
        //判断email是否存在
        entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("EMAIL",ssoUser.getEmail());
        count = ssoUserMapper.selectCount(entityWrapper);
        if(count >0){
            return APIMessage.API_STATUS_EMAIL_EXISTS;
        }
        //判断手机是否存在
        entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("TEL_NUM",ssoUser.getTelNum());
        count = ssoUserMapper.selectCount(entityWrapper);
        if(count >0){
            return APIMessage.API_STATUS_PHONE_EXISTS;
        }
        //判断机构代码是否存在
        QueryWrapper<SsoOrganization> entityWrapper2 = new QueryWrapper<>();
        entityWrapper2.eq("CODE",ssoUser.getOrgCode());
        count = ssoOrganizationMapper.selectCount(entityWrapper2);
        if(count  == 0){
            return APIMessage.API_STATUS_ORG_CODE_NOT_EXISTS;
        }
        if(StringUtil.isEmpty(ssoUser.getUserPwd())) {
            return APIMessage.API_STATUS_USER_PWD_NOT_EMPTY;
        }
        if(StringUtil.isEmpty(ssoUser.getUserType())) {
            return APIMessage.API_STATUS_USER_TYPE_NOT_EMPTY;
        }
        if(!CommonConstants.USER_TYPE_ORG.equals(ssoUser.getUserType()) && !CommonConstants.USER_TYPE_COM.equals(ssoUser.getUserType())){
            return APIMessage.API_STATUS_USER_TYPE_ERR;
        }
        QueryWrapper<SsoRole> entityWrapper3 = new QueryWrapper<>();
        entityWrapper3.eq("ORG_CODE",ssoUser.getOrgCode());
        if(CommonConstants.USER_TYPE_ORG.equals(ssoUser.getUserType())){
            entityWrapper3.eq("ROLE_NAME","机构管理员");
        }
        else if(CommonConstants.USER_TYPE_COM.equals(ssoUser.getUserType())){
            entityWrapper3.eq("ROLE_NAME","普通用户");
        }
        count = ssoRoleMapper.selectCount(entityWrapper3);
        if(count == 0){
            return APIMessage.API_STATUS_ROLE_NOT_SET;
        }
        return null;
    }

    @Override
    public APIMessage  updateUserCheck(SsoUser ssoUser) throws Exception {
        QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
        Integer count = 0;
        if(!StringUtil.isBlank(ssoUser.getEmail())) {
            entityWrapper.ne("USER_NAME", ssoUser.getUserName());
            //判断email是否存在
            entityWrapper.eq("EMAIL", ssoUser.getEmail());
            count = ssoUserMapper.selectCount(entityWrapper);
            if (count > 0) {
                return APIMessage.API_STATUS_EMAIL_EXISTS;
            }
        }
        if(!StringUtil.isBlank(ssoUser.getTelNum())) {
            //判断手机是否存在
            entityWrapper = new QueryWrapper<>();
            entityWrapper.ne("USER_NAME", ssoUser.getUserName());
            entityWrapper.eq("TEL_NUM", ssoUser.getTelNum());
            count = ssoUserMapper.selectCount(entityWrapper);
            if (count > 0) {
                return APIMessage.API_STATUS_PHONE_EXISTS;
            }
        }
        return null;
    }

    /**
     * 通过机构代码生成对应账号的权限
     * @param code SSO菜单模板机构
     * @param userName SSO用户名
     * @param roleNames SSO菜单模板机构对应角色名
     * @return
     */
    @Transactional
    @Override
    public AjaxResult copyMenuTemplate(String code, String userName, List<String> roleNames) {
        //判断该用户名是否存在
        QueryWrapper<SsoUser> ssoUserEntityWrapper = new QueryWrapper<>();
        ssoUserEntityWrapper.eq("USER_NAME",userName);
        SsoUser ssoUser = this.getOne(ssoUserEntityWrapper);
        if(ssoUser != null && StringUtil.isNotEmpty(ssoUser.getOrgCode())) {
            //判断目标模板企业机构代码是否已存在
            QueryWrapper<SsoOrganization> ssoOrganizationEntityWrapper = new QueryWrapper<>();
            ssoOrganizationEntityWrapper.eq("CODE", code);
            SsoOrganization targetSsoOrganization = ssoOrganizationService.getOne(ssoOrganizationEntityWrapper);
            if (targetSsoOrganization != null) {
                //获取该目标模板机构下的对应角色名角色
                QueryWrapper<SsoRole> ssoRoleEntityWrapper = new QueryWrapper<>();
                ssoRoleEntityWrapper.eq("ORG_CODE", code);
                ssoRoleEntityWrapper.in("ROLE_NAME", roleNames);
                List<SsoRole> ssoRoleList = ssoRoleService.list(ssoRoleEntityWrapper);
                if(ssoRoleList == null || ssoRoleList.size() == 0 || ssoRoleList.size() != roleNames.size()){
                    return new AjaxResult(MessageConstants.SSO_ROLEUSER_NO_MATCHING);
                }
                //获取该用户名对应的机构下的角色并转出key-角色名称的map对象，用于判断该企业是否已存在相同的用户名
                QueryWrapper<SsoRole> targetSsoRoleEntityWrapper = new QueryWrapper<>();
                targetSsoRoleEntityWrapper.eq("ORG_CODE", ssoUser.getOrgCode());
                List<SsoRole> userSsoRoleList = ssoRoleService.list(targetSsoRoleEntityWrapper);
                Map<String, SsoRole> ssoRoleMap = new HashMap<>();
                if(userSsoRoleList != null && userSsoRoleList.size() > 0){
                    ssoRoleMap = userSsoRoleList.stream().collect(Collectors.toMap(role -> role.getRoleName(),Function.identity()));
                }
                for(SsoRole ssoRole:ssoRoleList) {
                    //获取该角色对应的菜单权限
                    QueryWrapper<SsoRoleFunc> ssoRoleFuncEntityWrapper = new QueryWrapper<>();
                    ssoRoleFuncEntityWrapper.eq("ROLE_OID", ssoRole.getOid());
                    List<SsoRoleFunc> ssoRoleFuncList = ssoRoleFuncService.list(ssoRoleFuncEntityWrapper);
                    //判断需要生成的角色名是否已存在该用户所属机构中，如果存在则直接绑定，不存在则生成
                    if(!ssoRoleMap.isEmpty() && ssoRoleMap.containsKey(ssoRole.getRoleName())){
                        SsoRole ssoRole1 = ssoRoleMap.get(ssoRole.getRoleName());
                        //如果复制的角色已存在，则赋值原先角色信息
                        ssoRole.setOid(ssoRole1.getOid());
                        ssoRole.setOrgCode(ssoUser.getOrgCode());
                    }else {
                        //如果复制的菜单模板有菜单，则复制一份，没有则生成空角色
                        String ssoRoleID = UUIDGenerator.getUUID();
                        ssoRole.setOid(ssoRoleID);
                        ssoRole.setOrgCode(ssoUser.getOrgCode());
                        ssoRoleService.save(ssoRole);
                    }
                    //账号默认绑定该新角色
                    SsoRoleUser ssoRoleUser = new SsoRoleUser();
                    ssoRoleUser.setOid(UUIDGenerator.getUUID());
                    ssoRoleUser.setUserName(userName);
                    ssoRoleUser.setRoleOid(ssoRole.getOid());
                    ssoRoleUserService.save(ssoRoleUser);
                    if (ssoRoleFuncList != null && ssoRoleFuncList.size() > 0) {
                        for (SsoRoleFunc ssoRoleFunc : ssoRoleFuncList) {
                            ssoRoleFunc.setOid(UUIDGenerator.getUUID());
                            ssoRoleFunc.setRoleOid(ssoRole.getOid());
                        }

                        ssoRoleFuncService.saveBatch(ssoRoleFuncList);
                    }
                }
                return new AjaxResult(MessageConstants.SSO_STATUS_SUCCESS);
            } else {
                return new AjaxResult(MessageConstants.SSO_ORGANIZATION_CODE_NO_EXIST);
            }
        }else{
            return new AjaxResult(MessageConstants.SSO_STATUS_USER_NOT_EXISTS);
        }
    }
}
