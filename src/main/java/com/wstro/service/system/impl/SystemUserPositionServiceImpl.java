package com.wstro.service.system.impl;


import com.wstro.dao.system.SystemUserPositionMapper;
import com.wstro.entity.system.SystemUserPosition;
import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.service.system.ISystemUserPositionService;
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
