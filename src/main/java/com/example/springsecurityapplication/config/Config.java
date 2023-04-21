package com.example.springsecurityapplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    //Alt+insert и переопределяем этот метод
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
 //Если на каком-то шаблоне обращаемся по адресу /img/любоеЗначение, то необходимо открыть этот путь
 // ("file:///" означает, что открывать будем на компе (т.к. можно ещё открывать на каком-либо сервере, например)
        registry.addResourceHandler("/img/**").addResourceLocations("file:///"+uploadPath+"/");
    }
}
