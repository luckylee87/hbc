package com.dsg.dao.system;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsg.entity.system.SystemUserPosition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author xuzuotao
 * @since 2017-07-18
 */
public interface SystemUserPositionMapper extends BaseMapper<SystemUserPosition> {
    /**
     * 通过账号ID查找岗位列表
     * @param userId 用户Id
     * @return List<SystemUserPosition>
     */
    List<SystemUserPosition> selectPosListByUserId(@Param("userId") Long userId);

    /**
     * 插入用户岗位记录
     * @param systemUserPositions 岗位列表
     */
    void insertUserPositions(@Param("systemUserPositions") List<SystemUserPosition> systemUserPositions);
}