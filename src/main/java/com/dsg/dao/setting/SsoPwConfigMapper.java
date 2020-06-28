package com.dsg.dao.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsg.entity.setting.SsoPwConfig;

import java.util.List;

/**
 * @Description: SsoPwConfig持久层
 * @author: powerbridge@powerbridge.com
 * @Date: 2019年07月09日 10:56:45
 */
public interface SsoPwConfigMapper extends BaseMapper<SsoPwConfig> {

    /**
     * 分页查询
     *
     * @param page 分页
     * @param pw_config
     * @return Page<SsoPwConfig>
     */
    List<SsoPwConfig> selectSsoPwConfigList(Page<SsoPwConfig> page, SsoPwConfig pw_config);

}