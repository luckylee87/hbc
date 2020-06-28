package com.dsg.dao.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsg.entity.setting.SsoUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsoUserMapper extends BaseMapper<SsoUser> {

    List<SsoUser> selectByList(Page<SsoUser> page, SsoUser ssoUser);

    SsoUser login(@Param("userName") String userName, @Param("password") String password);
}
