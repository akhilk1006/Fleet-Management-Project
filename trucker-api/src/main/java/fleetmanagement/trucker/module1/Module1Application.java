package fleetmanagement.trucker.module1;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Instant;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class Module1Application {

    public static void main(String[] args) {
        SpringApplication.run(Module1Application.class, args);
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.initialize();
        return executor;
    }

    @Bean
    public AmazonSNS sns(){
        AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
        return snsClient;
    }
}
