package com.example.inventory.kafka.config;

import com.example.inventory.dto.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaJsonListenerContainerConfiguration implements KafkaListenerConfigurer {

    private final LocalValidatorFactoryBean validator;
    private JsonDeserializer<OrderEvent> deserializer;

    @Value("${spring.kafka.bootstrapAddress}")
    private String bootstrapServers;

    public KafkaJsonListenerContainerConfiguration(LocalValidatorFactoryBean validator) {
        this.validator = validator;

        this.deserializer = new JsonDeserializer<>(OrderEvent.class);
        this.deserializer.setRemoveTypeHeaders(false);
        this.deserializer.addTrustedPackages("*");
        this.deserializer.setUseTypeMapperForKey(true);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderEvent>> kafkaJsonContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderConsumerFactory());

        return factory;
    }

    public ConsumerFactory<String, OrderEvent> orderConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                props(),
                new StringDeserializer(),
                this.deserializer
        );
    }

    private Map<String, Object> props() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, this.deserializer);

        return props;
    }

    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
        registrar.setValidator(validator);
    }
}
