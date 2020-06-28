package com.dsg.service.base;

public interface OAuthService {

    public void addAuthCode(String authCode, String username);// 添加 auth code
    public void addAccessToken(String accessToken, String username); // 添加 access token
    public boolean checkAuthCode(String authCode); // 验证auth code是否有效
    public boolean checkAccessToken(String accessToken); // 验证access token是否有效
    public String getUsernameByAuthCode(String authCode);// 根据auth code获取用户名
    public String getUsernameByAccessToken(String accessToken);// 根据access token获取用户名
    public long getExpireIn();//auth code / access token 过期时间
    public boolean checkClientId(String clientId, String password);// 检查客户端id是否存在
}
