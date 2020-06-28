package com.dsg.dao.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powerbridge.core.dto.PullDown;
import com.dsg.entity.setting.AuthorizeTreeMode;
import com.dsg.entity.setting.SsoProject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper extends BaseMapper<SsoProject> {

    List<SsoProject> selectByList(Page<SsoProject> page, SsoProject project);

    SsoProject selectByCode(String code);

    //查询构建授权树的数据（系统管理员）
    List<AuthorizeTreeMode> selectAllForSYSAuthTree();

    //
    List<String> selectCodeByName(String userName);

    //下拉项目菜单
    List<PullDown> selectRedisDropDown();
}
