package com.example.order.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrapAddress}")
    private String bootstrapServers;

    @Value("${my.order-topic-name}")
    private String orderTopicName;

    @Value("${my.inventory-topic-name}")
    private String inventoryTopicName;

    @Value("${my.payment-topic-name}")
    private String paymentTopicName;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaAdmin.NewTopics clip2s() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(orderTopicName).build(),
                TopicBuilder.name(inventoryTopicName).build(),
                TopicBuilder.name(paymentTopicName).build()

        );
    }
}
