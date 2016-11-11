package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableBinding(ProducerChannel.class)
public class ProducerApplication {
	private final ProducerChannel producerChannel;

	public ProducerApplication(ProducerChannel channel) {
		this.producerChannel = channel;
	}

	@GetMapping(value = "/greet/{name}")
	public void greeting(@PathVariable String name) {
		String greeting = "Hello " + name + "!";
		System.out.println("producer sending message " + greeting);
		Message<String> message = MessageBuilder.withPayload(greeting).build();
		producerChannel.producer().send(message);
	}

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}
}

interface ProducerChannel {
	@Output
	MessageChannel producer();
}