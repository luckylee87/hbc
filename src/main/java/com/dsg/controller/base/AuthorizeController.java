package com.dsg.controller.base;

import com.powerbridge.sso.dto.JsonResultDto;
import com.dsg.common.constants.APIMessage;
import com.dsg.service.base.OAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：AuthorizeController
 * 类描述： 生成授权码
 * 创建人：weizheng
 * 创建时间：2017年11月2日 下午10:12:17
 * 修改人：weizheng
 * 修改时间：2017年11月12日 下午17:13
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthorizeController extends BaseController {

    @Autowired
    private OAuthService oAuthService;

    /**
     * 生成授权码
     * @param model
     * @param request
     * @return
     * @throws URISyntaxException
     * @throws OAuthSystemException
     */
    @RequestMapping(value = "/authorize",method = {RequestMethod.GET, RequestMethod.POST})

    public Object authorize(Model model, HttpServletRequest request)
            throws URISyntaxException, OAuthSystemException {
        try {

            String clientId = request.getParameter("clientId");
            String clientKey = request.getParameter("clientKey");

            //检查传入的客户端id是否正确
            if (!oAuthService.checkClientId(clientId,clientKey)) {
                return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_CLIENT_INFO_INVALID.getCode(), APIMessage.API_STATUS_CLIENT_INFO_INVALID.getMessage()));
            }

            //生成授权码
            String authorizationCode = null;

            OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
            authorizationCode = oauthIssuerImpl.authorizationCode();
            oAuthService.addAuthCode(authorizationCode, request.getParameter("clientId"));

            Map map = new HashMap();
            map.put("code", APIMessage.API_STATUS_SUCCESS.getCode());
            map.put("msg", APIMessage.API_STATUS_SUCCESS.getMessage());
            map.put("token", authorizationCode);
            log.info("/authorize生成授权码,token=" + authorizationCode);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            log.error("authorize() --error",e);
            e.printStackTrace();
            return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_SYSTEM_ERROR.getCode(), APIMessage.API_STATUS_SYSTEM_ERROR.getMessage()));
        }
    }

}
