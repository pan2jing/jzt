package jzt.erp.store.Filter;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;


@Configuration
public class WebConfig {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean LYMRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter((Filter) new LYMFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("myname", "这是我写的参数值");
        registration.setName("LYMFilter");
        registration.setOrder(1);
        return registration;
    }
}


