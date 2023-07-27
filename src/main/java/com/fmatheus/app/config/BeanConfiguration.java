package com.fmatheus.app.config;



import com.fmatheus.app.AuthApplication;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

@Configuration
@ComponentScan(basePackageClasses = AuthApplication.class)
public class BeanConfiguration {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }


    public Properties yamlProperties() {
        YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
        bean.setResources(new ClassPathResource("i18n/messages.yml"));
        return bean.getObject();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setCommonMessages(yamlProperties());
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }


}