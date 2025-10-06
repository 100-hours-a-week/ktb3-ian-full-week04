package ktb3.full.week04.config;

import ktb3.full.week04.interceptor.LoginValidateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginValidateInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/users/**");
    }
}
