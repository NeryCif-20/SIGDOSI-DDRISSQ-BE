package com.ddrissq.sigdosi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SigdosiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigdosiApplication.class, args);
    }

}
