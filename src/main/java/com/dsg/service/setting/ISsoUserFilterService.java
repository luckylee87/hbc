package com.dsg.service.setting;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsg.entity.setting.SsoUserFilter;

public interface ISsoUserFilterService extends IService<SsoUserFilter> {

    boolean insertEntity(String userName, String userOrgFilter);

}
