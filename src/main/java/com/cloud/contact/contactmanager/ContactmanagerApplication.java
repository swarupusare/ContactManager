package com.cloud.contact.contactmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class ContactmanagerApplication{

	public static void main(String[] args) {
		SpringApplication.run(ContactmanagerApplication.class, args);
//                SpringApplicationBuilder builder = new SpringApplicationBuilder(ContactmanagerApplication.class);
//                builder.headless(false);
//            ConfigurableApplicationContext context = builder.run(args);
                System.out.println("started");
	}

}
