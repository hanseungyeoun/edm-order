package com.example.inventory.kafka.consumer;

import com.example.inventory.constant.Status;
import com.example.inventory.dto.OrderEvent;
import com.example.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class InventoryConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(id = "inventory_consumer_group", topics = "inventory", containerFactory = "kafkaJsonContainerFactory")
    public void listen(@Valid OrderEvent orderEvent) {
        if (orderEvent.getStatus() == Status.ORDER_CREATED) {
            inventoryService.processInventoryReserved(
                    orderEvent,
                    orderEvent.getTransactionId(),
                    orderEvent.getInventory().getId(),
                    orderEvent.getInventory().getQuantity());
        } else if(orderEvent.getStatus() == Status.ORDER_CANCEL) {
            inventoryService.processOrderCancel(orderEvent,
                    orderEvent.getTransactionId(),
                    orderEvent.getInventory().getId(),
                    orderEvent.getInventory().getQuantity());
        }
    }
}
