package com.wstro.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wstro.entity.system.SystemUserLoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemUserLoginLogMapper
 * 类描述：SystemUserLoginLog 表数据访问层接口
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
@Repository
public interface SystemUserLoginLogMapper extends BaseMapper<SystemUserLoginLog> {

    /**
     * 根据用户ID查找用户日志
     *
     * @param userId 用户ID
     * @return List<SystemUserLoginLog>
     */
    List<SystemUserLoginLog> selectUserLoginLog(@Param("userId") String userId);

}