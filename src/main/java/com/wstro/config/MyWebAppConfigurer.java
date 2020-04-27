package com.wstro.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 自定义配置
 * 
 */
@Configuration
// 可以导入自定义XML文件
// @ImportResource(locations = { "classpath:/application-mvc.xml" })
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

	/**
	 * 自定义资源映射
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/statics/**").addResourceLocations("/statics/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/upload/**").addResourceLocations("/upload/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		super.addResourceHandlers(registry);
	}

	/**
	 * 文件上传配置
	 * 
	 * @return MultipartConfigElement
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
		factory.setMaxFileSize(DataSize.ofMegabytes(10)); // KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize(DataSize.ofMegabytes(20));
		// Sets the directory location where files will be stored.
		// factory.setLocation("路径地址");
		return factory.createMultipartConfig();
	}

}
