package jungle.HandTris.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 경로에 대해 CORS 설정
                        .allowedOrigins("http://localhost:3000") // 허용할 도메인
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD") // 허용할 HTTP 메서드
                        .allowCredentials(true); // 자격 증명 허용 (쿠키 등)
            }
        };
    }
}

