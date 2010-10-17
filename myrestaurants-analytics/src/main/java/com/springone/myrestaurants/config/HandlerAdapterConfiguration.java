package com.springone.myrestaurants.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.datastore.document.web.servlet.ActionInterceptor;
import org.springframework.datastore.document.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;

import com.springone.myrestaurants.web.DatastoreInterceptor;

@Configuration
public class HandlerAdapterConfiguration {

	@Autowired
	private DatastoreInterceptor datastoreInterceptor;
	
	@Bean
	public AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter() {
		AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
		
		ActionInterceptor[] interceptors = new ActionInterceptor[] {datastoreInterceptor};
		adapter.setActionInterceptors(interceptors);
		
		ConfigurableWebBindingInitializer wbi = new ConfigurableWebBindingInitializer();
		wbi.setValidator(getValidator());
		wbi.setConversionService(conversionService());
		
		adapter.setWebBindingInitializer(wbi);		
		adapter.setMessageConverters(getMessageConverters());
		adapter.setOrder(-1);
		return adapter;
	}

	@Bean
	public ConversionService conversionService() {
		FormattingConversionServiceFactoryBean fb = new FormattingConversionServiceFactoryBean();
		fb.afterPropertiesSet();
		FormattingConversionService cs = fb.getObject();		
		return cs;
	}
	
	private Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.afterPropertiesSet();
		return validator;
	}

	private HttpMessageConverter<?>[] getMessageConverters() {
		HttpMessageConverter<?>[] converters = new HttpMessageConverter<?>[] {
				new ByteArrayHttpMessageConverter(),
				new StringHttpMessageConverter(),
				new ResourceHttpMessageConverter(),
				new SourceHttpMessageConverter(),
				new XmlAwareFormHttpMessageConverter(),
				
		};
		return converters;
		
	}
	

	
	/*
	@Bean
	public ConfigurableWebBindingInitializer configurableWebBindingInitializer() {
		ConfigurableWebBindingInitializer bi = new ConfigurableWebBindingInitializer();
		
		return bi;
	}
	
	@Bean
	public FormattingConversionServiceFactoryBean formattingConversionServiceFactoryBean() {
		FormattingConversionServiceFactoryBean conversionServiceFB = new FormattingConversionServiceFactoryBean();
		return conversionServiceFB;
	}*/
	
}
