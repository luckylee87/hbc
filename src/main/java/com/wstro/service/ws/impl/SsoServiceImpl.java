package com.wstro.service.ws.impl;

import com.wstro.service.ws.ISsoService;

import javax.jws.WebService;

@WebService(targetNamespace = "http://service.ws.sso.powerbridge.com/",endpointInterface="com.wstro.service.ws.ISsoService",serviceName="SsoService")
public class SsoServiceImpl implements ISsoService {
    @Override
    public String getMenu(String projectCode, String userNo, String token) {
        return null;
    }
}
