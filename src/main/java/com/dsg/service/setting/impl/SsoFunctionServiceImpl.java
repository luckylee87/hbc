package com.dsg.service.setting.impl;

import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.dao.setting.SsoFunctionMapper;
import com.dsg.entity.setting.SsoFunction;
import com.dsg.service.setting.ISsoFunctionService;
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
