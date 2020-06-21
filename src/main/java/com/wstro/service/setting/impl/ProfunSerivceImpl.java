package com.wstro.service.setting.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.dao.setting.ProfunMapper;
import com.wstro.entity.setting.Profun;
import com.wstro.service.setting.IProfunSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("profunSerivce")
public class ProfunSerivceImpl extends BaseServiceImpl<ProfunMapper,Profun> implements IProfunSerivce{

    @Autowired
    private ProfunMapper profunMapper;

    public Page<Profun> selectByList(Page<Profun> page, Profun profun){
        page.setRecords(profunMapper.selectByList(page,profun));
        return page;
    }

    public List<Profun> selectByProjectCode(String code){
        return profunMapper.selectByProjectCode(code);
    }

    public Profun selectOneByProjectCode(String code){
        return profunMapper.selectOneByProjectCode(code);
    }
}
