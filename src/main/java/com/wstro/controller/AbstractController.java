package com.wstro.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wstro.util.Constant;
import com.wstro.util.EhcacheUtil;

/**
 * Controller公共组件
 * 
 */
abstract class AbstractController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/** 常量帮助类 */
	@Resource
	protected Constant constant;

	@Resource
	protected EhcacheUtil ehcacheUtil;

}
