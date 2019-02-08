package smirnov.bn.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
//https://spring.io/guides/gs/scheduling-tasks/ (:)
@EnableScheduling
public class ApiGatewayApplication {

	public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
        //[https://stackoverflow.com/questions/45173390/running-each-spring-scheduler-in-its-own-thread] [:]
        @Bean
        public TaskScheduler taskScheduler() {
            final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
            scheduler.setPoolSize(2);
            return scheduler;
        }

        /*
        @Scheduled(fixedDelay = 2 * 1000L, initialDelay = 3 * 1000L)
        public void scheduled1() throws InterruptedException {
            System.out.println(new Date() + " " + Thread.currentThread().getName() + ": scheduled1");
            Thread.sleep(1000);
        }
        //*/
}
