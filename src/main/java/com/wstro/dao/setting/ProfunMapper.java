package com.wstro.dao.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wstro.entity.setting.AuthorizeTreeMode;
import com.wstro.entity.setting.Profun;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfunMapper extends BaseMapper<Profun> {

    List<Profun> selectByList(Page<Profun> page, Profun profun);

    List<Profun> selectByProjectCode(String code);

    Profun selectOneByProjectCode(String code);

    //查询构建"系统管理员"授权树的数据
    List<AuthorizeTreeMode> selectAllForSYSAuthTree();
    //查询构建"机构管理员"授权树的数据
    List<AuthorizeTreeMode> selectAllForORGAuthTree(@Param("userName") String userName);
}
