package com.dsg.dao.system;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsg.entity.system.SystemPosition;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xuzuotao
 * @since 2017-07-18
 */
public interface SystemPositionMapper extends BaseMapper<SystemPosition> {
    /**
     * 下拉数据源
     *
     * @return
     */
    List<SystemPosition> selectRedisDropDown();
}