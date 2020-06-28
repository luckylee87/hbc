package com.dsg.dao.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsg.entity.setting.SsoProAuth;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsoProAuthMapper extends BaseMapper<SsoProAuth> {

    List<SsoProAuth> selectByList(Page<SsoProAuth> page, SsoProAuth ssoProAuth);

}
