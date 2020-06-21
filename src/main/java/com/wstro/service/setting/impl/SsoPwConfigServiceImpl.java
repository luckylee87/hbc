package com.wstro.service.setting.impl;

import com.wstro.dao.setting.SsoPwConfigMapper;
import com.wstro.entity.setting.SsoPwConfig;
import com.wstro.service.setting.ISsoPwConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.transaction.annotation.Transactional;
import com.wstro.service.base.impl.BaseServiceImpl;
import java.util.List;

@Service
public class SsoPwConfigServiceImpl extends BaseServiceImpl<SsoPwConfigMapper,SsoPwConfig> implements ISsoPwConfigService {

    @Autowired
    private SsoPwConfigMapper ssoPwConfigMapper;

    /**
     * 分页查询
     *
     * @param page 分页
     * @param pw_config
     * @return Page<SsoPwConfig>
     */
    @Override
    public Page<SsoPwConfig> selectSsoPwConfigList(Page<SsoPwConfig> page, SsoPwConfig pw_config) {
		return page.setRecords(ssoPwConfigMapper.selectSsoPwConfigList(page, pw_config));
	}

    /**
    * 批量删除
    * @param idList 主键集合
    * @return
    */
    @Transactional
    public boolean deleteBySeqNoList(List<String> idList){
        QueryWrapper entityWrapper = new QueryWrapper();
        entityWrapper.in("oid",idList);

        //TODO 若有子表关联数据，先删除子表数据
        /*List<SsoPwConfig> ssoPwConfigList=ssoPwConfigMapper.list(entityWrapper);
        for (SsoPwConfig ssoPwConfig:ssoPwConfigList) {
            //根据表头Id 查找SEQ_NO删除子表
            QueryWrapper delEntityWrapper=new QueryWrapper();
            delEntityWrapper.eq("SEQ_NO", ssoPwConfig.getSeqNo());

        }*/
        boolean flag = retBool(ssoPwConfigMapper.delete(entityWrapper));
        return flag;
    }

    /**
    * 根据单据编号 判断单据是否存在
    * @param seqNo
    * @return
    */
    public boolean isExistBsc(String seqNo){
        QueryWrapper entityWrapper = new QueryWrapper();
        entityWrapper.eq("SEQ_NO",seqNo);
        int count = ssoPwConfigMapper.selectCount(entityWrapper);
        if(count == 0)
            return false;
        return  true;
    }

}