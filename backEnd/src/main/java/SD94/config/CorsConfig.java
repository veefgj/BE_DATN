package SD94.config;

import SD94.repository.implement.ThongKeRepositoryImpl;
import SD94.repository.thongKe.ThongKeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @SuppressWarnings("null")
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*", "https://sandbox.vnpayment.vn/", "http://127.0.0.1:5501/")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Content-Type", "Authorization");
    }

    @Bean
    public ThongKeRepository thongKeRepository(){
        return new ThongKeRepositoryImpl();
    }
}
