package com.dsg.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 错误控制器
 * 
 */
//@Controller
public class MainsiteErrorController implements ErrorController {
	private static final String ERROR_PATH = "/error";

	@RequestMapping(value = ERROR_PATH)
	public String handleError() {
		return "/error";
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}
