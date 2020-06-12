package pl.koksyn.taskforest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TaskForestConfig {
    @Bean
    public Clock clock() {
        log.info("Clock as spring-bean registration...");
        return new SystemClock();
    }
}
