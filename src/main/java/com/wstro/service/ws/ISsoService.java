package com.wstro.service.ws;

import javax.jws.WebService;

/**
 * 项目名称：sso
 * 类名称：IGetMenuListService
 * 类描述：菜单管理服务类
 * @author jokylao
 * @createtime 2017-11-01
 *
 * @Version 1.0.0
 */
@WebService
public interface ISsoService {
    /**
     * 获取项目菜单
     * @param projectCode 项目代码
     * @param userNo 用户编号
     * @param token
     * @return
     */
    String getMenu(String projectCode, String userNo, String token);
}
