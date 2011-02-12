package com.springone.myrestaurants.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.springone.myrestaurants.web.CouchDbMappingJacksonHttpMessageConverter;

@Configuration
public class CouchDbConfig {

	public static String URL =  "http://127.0.0.1:5984/spring_demo/";
	
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		
		//converters.add(new MappingJacksonHttpMessageConverter());
		converters.add(new CouchDbMappingJacksonHttpMessageConverter());
		restTemplate.setMessageConverters(converters);
		return restTemplate;
	}
}
