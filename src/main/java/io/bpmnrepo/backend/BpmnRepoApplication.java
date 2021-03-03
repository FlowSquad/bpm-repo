package io.bpmnrepo.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class BpmnRepoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BpmnRepoApplication.class, args);
    }

}
