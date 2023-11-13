/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2021
 */
package de.muenchen.digiwf.bpm.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application class for starting the micro-service.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = { "de.muenchen.digiwf.bpm.server" })
@EntityScan(basePackages = { "de.muenchen.digiwf.bpm.server" })
@EnableScheduling
public class MicroServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MicroServiceApplication.class, args);
    }

}
