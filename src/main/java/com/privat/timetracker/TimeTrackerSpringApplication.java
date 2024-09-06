package com.privat.timetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimeTrackerSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeTrackerSpringApplication.class, args);
    }

}
