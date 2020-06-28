package com.dsg.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dsg.common.constants.CommonConstants;
import com.dsg.service.base.OAuthService;
import com.dsg.service.setting.ISsoProAuthService;
import com.dsg.service.setting.ISsoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service("oAuthService")
public class OAuthServiceImpl implements OAuthService {


    private Cache cache ;
    @Autowired
    private ISsoUserService ssoUserService;

    @Autowired
    private ISsoProAuthService ssoProAuthService;

    @Autowired
    public OAuthServiceImpl(CacheManager cacheManager) {
        this.cache = cacheManager.getCache(CommonConstants.CACHE_TOKEN_KEY);
    }

    @Override
    public void addAuthCode(String authCode, String username){
        cache.put(authCode,username);
    }

    @Override
    public boolean checkClientId(String clientId,String clientKey){

        QueryWrapper entityWrapper = new QueryWrapper();
        entityWrapper.eq("CLIENT_ID",clientId);
//        entityWrapper.and();
        entityWrapper.eq("CLIENT_KEY",clientKey);
//        entityWrapper.and("CLIENT_KEY",clientKey);

        return ssoProAuthService.getOne(entityWrapper) != null ? true:false;
    }

    @Override
    public boolean checkAuthCode(String authCode){

        return cache.get(authCode) != null;
    }

    @Override
    public boolean checkAccessToken(String accessToken){

        return cache.get(accessToken) != null;
    }

    @Override
    public void addAccessToken(String accessToken, String username){
        cache.put(accessToken,username);

    }

    @Override
    public String getUsernameByAuthCode(String authCode){

        return (String)cache.get(authCode).get();
    }

    @Override
    public String getUsernameByAccessToken(String accessToken){

        return cache.get(accessToken).get().toString();
    }

    @Override
    public long getExpireIn(){
        return 3600L;
    }
}
