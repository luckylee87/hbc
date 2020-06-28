package com.dsg.dao.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsg.entity.setting.SsoFunction;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 功能维护表 Mapper 接口
 * </p>
 *
 * @author zcb
 * @since 2017-10-18
 */
public interface SsoFunctionMapper extends BaseMapper<SsoFunction> {
	
	String getCode();	

	List<String> getAuthFuncCode(Map<String, String> param);

}
