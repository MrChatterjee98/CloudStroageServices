package project.cloud.configuration;

import java.util.HashMap;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


@Configuration
public class ApplicationConfiguration {

	@Value("${kafka.broker.url}")
	private String kafkaBrokerUrl;

	@Bean
	HashMap<String, Object> fileStorageProducerConfigs() {
		HashMap<String, Object> fileProducerConfig = new HashMap<String, Object>();
		fileProducerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerUrl);
		fileProducerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		fileProducerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return fileProducerConfig;
	}

	@Bean
	ProducerFactory<String, String> fileStorageProducerFactory(
			HashMap<String, Object> fileStorageProducerConfigs) {
		return new DefaultKafkaProducerFactory<String,String>(fileStorageProducerConfigs);
	}
	
	@Bean
	KafkaTemplate<String, String> fileStorageTemplate(ProducerFactory<String, String> fileStorageProducerFactory){
		return new KafkaTemplate<>(fileStorageProducerFactory);
	}

}
