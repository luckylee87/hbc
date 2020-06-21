package com.wstro.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wstro.dao.base.SystemLogMapper;
import com.wstro.entity.base.SystemLog;
import com.wstro.service.base.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Simon.xie
 * @version 1.0.0
 * @ClassName SystemLogServiceImpl
 * @Description 用户行为日志
 * @Date 2017年5月3日 下午9:47:54
 */
@Service("systemLogService")
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog> implements ISystemLogService {
//    @Autowired
//    public SystemLogMapper systemLogMapper;
}
