package com.cox.cap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@EnableDiscoveryClient
//@PropertySource({"classpath:appconfig.properties"})
public class consulconfig {
	
    public static void main(String args[]) {
    	SpringApplication.run(consulconfig.class, args);
    }

}