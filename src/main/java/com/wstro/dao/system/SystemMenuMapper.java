package com.wstro.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wstro.entity.system.SystemMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemMenuMapper
 * 类描述：SystemMenu 表数据访问层接口
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
@Repository
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {

    /**
     * 查询系统目录
     *
     * @param status   状态
     * @param menuType 权限类型
     * @return List<SystemMenu>
     */
    List<SystemMenu> selectSystemMenu(@Param("status") Integer status, @Param("menuType") Integer menuType);

    /**
     * 根据角色查询系统目录
     *
     * @param status   状态
     * @param menuType 权限类型
     * @return List<SystemMenu>
     */
    List<SystemMenu> selectSystemMenuByRole(@Param("status") Integer status, @Param("menuType") Integer menuType, @Param("roleIdList") List<String> roleIdList);

}