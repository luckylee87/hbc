package com.dsg.service.system.impl;


import com.dsg.dao.system.SystemPositionMapper;
import com.dsg.entity.system.SystemPosition;
import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.service.system.ISystemPositionService;
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
