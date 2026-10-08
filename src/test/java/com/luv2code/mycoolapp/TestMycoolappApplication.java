package com.luv2code.mycoolapp;

import org.springframework.boot.SpringApplication;

public class TestMycoolappApplication {

    public static void main(String[] args) {
        SpringApplication.from(MycoolappApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
