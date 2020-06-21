package com.wstro.service.setting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wstro.entity.setting.SsoProAuth;

public interface ISsoProAuthService extends IService<SsoProAuth> {

    public Page<SsoProAuth> selectByList(Page<SsoProAuth> page, SsoProAuth ssoProAuth);

}
