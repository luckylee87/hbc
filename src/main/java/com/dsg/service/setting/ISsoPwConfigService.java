package com.dsg.service.setting;


import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsg.entity.setting.SsoPwConfig;

import java.util.List;

/**
 * @Description: SsoPwConfig服务层
 * @author: powerbridge@powerbridge.com
 * @Date: 2019年07月09日 10:56:45
 */
public interface ISsoPwConfigService extends IService<SsoPwConfig> {

    /**
     * 分页查询
     *
     * @param page 分页
     * @param pw_config
     * @return Page<SsoPwConfig>
     */
    Page<SsoPwConfig> selectSsoPwConfigList(Page<SsoPwConfig> page, SsoPwConfig pw_config);

    /**
     * 批量删除
     * @param seqNoByList 主键集合
     * @return
     */
    boolean deleteBySeqNoList(List<String> seqNoByList);

    /**
     * 根据单据编号 判断单据是否存在
     * @param seqNo
     * @return
     */
    boolean isExistBsc(String seqNo);
}