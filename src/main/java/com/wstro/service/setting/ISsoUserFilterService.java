package com.wstro.service.setting;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wstro.entity.setting.SsoUserFilter;

public interface ISsoUserFilterService extends IService<SsoUserFilter> {

    boolean insertEntity(String userName, String userOrgFilter);

}
