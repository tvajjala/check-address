package com.tvajjala.address.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Swagger UI render configuration
 *
 * @author ThirupathiReddy Vajjala
 */
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        LOGGER.info("Adding resource handlers to the swagger UI");
        registry.addResourceHandler("api/**").addResourceLocations("classpath:/api/");
        registry.addResourceHandler("*.css").addResourceLocations("classpath:/swagger-ui/");
        registry.addResourceHandler("*.js").addResourceLocations("classpath:/swagger-ui/");
        registry.addResourceHandler("*.html").addResourceLocations("classpath:/swagger-ui/");
        registry.addResourceHandler("images/**").addResourceLocations("classpath:/images/");
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        LOGGER.info("Enabling Cross Origin Resource Sharing  ");
        registry.addMapping("/*");// all the end-points now can be accessible from other domains
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/index.html");
    }

}
