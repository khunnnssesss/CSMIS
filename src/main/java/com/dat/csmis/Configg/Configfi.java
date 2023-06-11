package com.dat.csmis.Configg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class Configfi {
    
    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean myb(){

        FreeMarkerConfigurationFactoryBean bb = new FreeMarkerConfigurationFactoryBean();

        bb.setTemplateLoaderPath("classpath:/templates/mailTemplates");
        return bb;
    }
}
