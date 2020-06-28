package com.dsg.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dsg.dao.base.SystemLogMapper;
import com.dsg.entity.base.SystemLog;
import com.dsg.service.base.ISystemLogService;
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
