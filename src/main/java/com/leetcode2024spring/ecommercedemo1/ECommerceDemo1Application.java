package com.leetcode2024spring.ecommercedemo1;


import com.leetcode2024spring.ecommercedemo1.collection.*;
import com.leetcode2024spring.ecommercedemo1.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import java.util.*;
import java.sql.Timestamp;

@SpringBootApplication
//@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class ECommerceDemo1Application {

    public static void main(String[] args) {

        SpringApplication.run(ECommerceDemo1Application.class, args);
    }

}
