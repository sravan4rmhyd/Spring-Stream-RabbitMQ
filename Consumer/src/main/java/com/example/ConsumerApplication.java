package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@EnableBinding(ConsumerChannels.class)
public class ConsumerApplication {
	@Bean
	IntegrationFlow integrationFlow(ConsumerChannels channel)
	{
		return IntegrationFlows.from(channel.consumer()).handle(String.class, (payload,headers) ->{
			System.out.println("consumer payload "+payload);
			return null;
		}).get();
	}
	
	public static void main(String[] args) {		
		SpringApplication.run(ConsumerApplication.class, args);
	}
	
}
interface ConsumerChannels{
	@Input
	MessageChannel consumer();
}
