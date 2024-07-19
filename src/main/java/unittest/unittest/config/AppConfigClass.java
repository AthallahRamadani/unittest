package unittest.unittest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfigClass {
    @Bean
    public RestClient restClient(){
        return RestClient.create();
    }
}