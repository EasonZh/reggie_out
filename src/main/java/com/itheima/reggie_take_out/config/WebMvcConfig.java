package com.itheima.reggie_take_out.config;

import com.itheima.reggie_take_out.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始静态资源配置");
        registry.addResourceHandler("/backend/**").addResourceLocations("classPath:/backend/");
         registry.addResourceHandler("/front/**").addResourceLocations("classPath:/front/");

    }
    /**
     * 扩展MVC框架消息转化器
     */
    @Override
    protected void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        //创建消息转化对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转化器，底层使用jackson 将java对象转化为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转化器对象追加到MVC框架的转化集合
        getMessageConverters().add(0,messageConverter);
    }
}
