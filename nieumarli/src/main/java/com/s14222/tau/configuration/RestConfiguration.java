package com.s14222.tau.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan({"com.s14222.tau"})
public class RestConfiguration extends WebMvcConfigurerAdapter{

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        super.configureMessageConverters(messageConverters);
    }
}
