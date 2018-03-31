package com.david.test.bean;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableApolloConfig
public class AppConfig {

    @Bean
    public TestAnnotationBean javaConfigBean() {
        return new TestAnnotationBean();
    }
}
