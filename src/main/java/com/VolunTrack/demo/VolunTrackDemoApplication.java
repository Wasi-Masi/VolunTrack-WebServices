package com.VolunTrack.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main class for the VolunTrackDemo Spring Boot application.
 * This class bootstraps the Spring application context.
 *
 * @SpringBootApplication combines:
 * - @Configuration: Tags the class as a source of bean definitions.
 * - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
 * - @ComponentScan: Tells Spring to look for other components, configurations, and services in the 'com.VolunTrack.demo' package, allowing it to find controllers, services, and repositories.
 *
 * @EnableJpaAuditing enables JPA auditing features, typically for automatic population of
 * creation and last modification timestamps.
 *
 * @EnableJpaRepositories enables Spring Data JPA repositories. It's often not strictly
 * necessary if your repositories are in a subpackage of the main application class,
 * but it makes the intention explicit and can be useful for scanning specific packages.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {
        "com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories",
        "com.VolunTrack.demo.ActivityRegistration.Domain.Repositories",
        "com.VolunTrack.demo.Participation.Domain.Repositories",
        "com.VolunTrack.demo.Notifications.Domain.Repositories" 

})
public class VolunTrackDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(VolunTrackDemoApplication.class, args);
    }
}