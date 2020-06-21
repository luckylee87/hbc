package com.wstro.service.setting.impl;

import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.dao.setting.SsoFunctionMapper;
import com.wstro.entity.setting.SsoFunction;
import com.wstro.service.setting.ISsoFunctionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 功能维护表 服务实现类
 * </p>
 *
 * @author zcb
 * @since 2017-10-18
 */
@Service("ssoFunctionService")
public class SsoFunctionServiceImpl extends BaseServiceImpl<SsoFunctionMapper, SsoFunction> implements ISsoFunctionService {
}
