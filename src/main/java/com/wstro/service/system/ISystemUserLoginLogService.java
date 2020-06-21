package com.wstro.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wstro.entity.system.SystemUserLoginLog;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：ISystemUserLoginLogService
 * 类描述：SystemUserLoginLog 表业务逻辑层接口
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17s
 */
public interface ISystemUserLoginLogService extends IService<SystemUserLoginLog> {

    /**
     * 根据用户ID查询用户登录日志
     *
     * @param userId 用户ID
     * @return List<SystemUserLoginLog>
     */
    List<SystemUserLoginLog> selectUserLoginLog(String userId);


}