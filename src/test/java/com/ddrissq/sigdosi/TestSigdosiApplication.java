package com.ddrissq.sigdosi;

import org.springframework.boot.SpringApplication;

public class TestSigdosiApplication {

    public static void main(String[] args) {
        SpringApplication.from(SigdosiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
