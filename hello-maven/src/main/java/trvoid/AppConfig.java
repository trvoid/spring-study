package trvoid;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean(name="helloService")
    public HelloManager getHelloManager() {
        return new HelloManagerImpl();
    }
}
