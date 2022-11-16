package project.cloud.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {
	
	@Bean
	public NewTopic fileDetailsTopic() {
		return TopicBuilder.name("fileDetails").build();
	}
	
	
}
