package com.wstro.service.system;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wstro.entity.system.SystemUserPosition;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzuotao
 * @since 2017-07-18
 */
public interface ISystemUserPositionService extends IService<SystemUserPosition> {
    /**
     * 通过账号ID查找角色列表
     * @param userId 用户Id
     * @return
     */
    List<SystemUserPosition> selectPosListByUserId(Long userId);
}
