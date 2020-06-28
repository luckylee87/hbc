package com.dsg.service.setting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dsg.entity.setting.Profun;

import java.util.List;

public interface IProfunSerivce extends IService<Profun> {

    public Page<Profun> selectByList(Page<Profun> page, Profun profun);

    public List<Profun> selectByProjectCode(String code);

    public Profun selectOneByProjectCode(String code);
}
