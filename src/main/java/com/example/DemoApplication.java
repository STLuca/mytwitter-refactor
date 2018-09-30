package com.example;

import com.example.Entities.*;
import com.example.EventHandlers.EventHandlers;
import com.example.Repositories.FollowRepository;
import com.example.Repositories.TweetRepository;
import com.example.Repositories.UserRepository;
import com.example.ValueObjects.TweetMessage;
import com.example.ValueObjects.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2WebMvc
@Import(SpringDataRestConfiguration.class)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/*
	@Bean
	public CommandLineRunner loadData(
			UserRepository userRepository,
			TweetRepository tweetRepository,
            FollowRepository followRepository
	) {
		return args -> {
			// userRepository.save(new UserVal(1, "bob"));
			User bob = new User(new Long(1), new Username("bob"));
			User sally = new User(new Long(2), new Username("sally"));

            Follow bobFollowSally = new Follow(new Long(5), bob, sally);
            Follow sallyFollowBob = new Follow(new Long(6), sally, bob);
            Follow bobFollowBob = new Follow(new Long(7), bob, bob);

			Tweet bobTweet1 = new Tweet(new Long(3), new TweetMessage("hi"), bob, null);
			Tweet bobTweet2 = new Tweet(new Long(4), new TweetMessage("hey me"), bob, bobTweet1);

			userRepository.save(bob);
			userRepository.save(sally);

			tweetRepository.save(bobTweet1);
			tweetRepository.save(bobTweet2);
			followRepository.save(bobFollowSally);
			followRepository.save(sallyFollowBob);

			// followRepository.save(bobFollowBob);
		};
	}
	*/
}
