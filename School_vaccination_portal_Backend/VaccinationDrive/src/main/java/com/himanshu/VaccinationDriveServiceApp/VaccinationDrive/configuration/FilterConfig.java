package com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.configuration;


import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.security.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter jwtFilter) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns("/drives/*"); // ✅ Secure all /drives endpoints
        return registrationBean;
    }
}
