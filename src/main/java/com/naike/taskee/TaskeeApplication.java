package com.naike.taskee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskeeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TaskeeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hey, I'm back");
	}
}
