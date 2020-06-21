package com.wstro.service.system;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wstro.entity.system.SystemPosition;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuzuotao
 * @since 2017-07-18
 */
public interface ISystemPositionService extends IService<SystemPosition> {

    /**
     * 下拉数据源
     */
    List<SystemPosition> selectRedisDropDown();
}
