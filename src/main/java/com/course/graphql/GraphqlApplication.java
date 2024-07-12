package com.course.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraphqlApplication {

    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//
//        executorService.submit(() -> {
//        });
//        executorService.shutdown();
//        executorService.awaitTermination(5000, TimeUnit.MINUTES);
//        executorService.shutdownNow();
        SpringApplication.run(GraphqlApplication.class, args);
    }

}
