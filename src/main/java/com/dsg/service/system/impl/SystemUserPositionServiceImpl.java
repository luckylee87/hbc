package com.dsg.service.system.impl;


import com.dsg.dao.system.SystemUserPositionMapper;
import com.dsg.entity.system.SystemUserPosition;
import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.service.system.ISystemUserPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzuotao
 * @since 2017-07-18
 */
@Service("systemUserPositionService")
public class SystemUserPositionServiceImpl extends BaseServiceImpl<SystemUserPositionMapper, SystemUserPosition> implements ISystemUserPositionService {

    @Autowired
    private SystemUserPositionMapper systemUserPositionMapper;

    @Override
    public List<SystemUserPosition> selectPosListByUserId(Long userId) {
        return systemUserPositionMapper.selectPosListByUserId(userId);
    }
}
