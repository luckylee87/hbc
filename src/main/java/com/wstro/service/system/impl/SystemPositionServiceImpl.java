package com.wstro.service.system.impl;


import com.wstro.dao.system.SystemPositionMapper;
import com.wstro.entity.system.SystemPosition;
import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.service.system.ISystemPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuzuotao
 * @since 2017-07-18
 */
@Service("systemPositionService")
public class SystemPositionServiceImpl extends BaseServiceImpl<SystemPositionMapper, SystemPosition> implements ISystemPositionService {

    @Autowired
    private SystemPositionMapper systemPositionMapper;

    /**
     * 下拉数据源
     */
    public List<SystemPosition> selectRedisDropDown() {
        return systemPositionMapper.selectRedisDropDown();
    }
}
