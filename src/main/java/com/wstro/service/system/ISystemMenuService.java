package com.wstro.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wstro.entity.system.SystemMenu;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：ISystemMenuService
 * 类描述：SystemMenu 表业务逻辑层接口
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
public interface ISystemMenuService extends IService<SystemMenu> {

    /**
     * 查询系统目录,网站目录列表
     *
     * @return List<SystemMenu>
     */
    List<SystemMenu> selectSystemMenu();

    /**
     * 根据角色获取系统目录
     *
     * @return List<SystemMenu>
     */
    List<SystemMenu> selectSystemMenuByRole(List<String> roleIdList);

}