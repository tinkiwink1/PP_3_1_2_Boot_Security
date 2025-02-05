package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/springsecurity/user").setViewName("user");
        registry.addViewController("/springsecurity/admin").setViewName("admin");
        registry.addViewController("/springsecurity/admin/users").setViewName("users");
        registry.addViewController("/springsecurity/admin/new_user").setViewName("new_user");
        registry.addViewController("/springsecurity/admin/edit").setViewName("edit_user");

    }
}
