package org.mamba.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class RedditPostAggregatorServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditPostAggregatorServerApplication.class, args);
	}

}
