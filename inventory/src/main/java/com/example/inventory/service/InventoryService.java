package com.example.inventory.service;

import com.example.inventory.common.exception.EntityNotFoundException;
import com.example.inventory.constant.Status;
import com.example.inventory.domain.Inventory;
import com.example.inventory.domain.InventoryHistory;
import com.example.inventory.dto.OrderEvent;
import com.example.inventory.kafka.Producer.InventoryProducer;
import com.example.inventory.repository.InventoryHistoryRepository;
import com.example.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.inventory.constant.Status.*;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryHistoryRepository inventoryHistoryRepository;
    private final InventoryProducer inventoryProducer;

    @Value("${my.outbound-success-event-channel}")
    private String outboundSuccessEventChannel;

    @Value("${my.outbound-failure-event-channel}")
    private String outboundFailureEventChannel;

    public void processInventoryReserved(OrderEvent orderEvent, String transactionId, Long inventoryId, Integer quantity) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new EntityNotFoundException("processInventoryReserved.orderEvent.inventory"));

        inventory.changeQuantity(quantity);

        InventoryHistory history = InventoryHistory.builder()
                .inventory(inventory)
                .transactionId(transactionId)
                .quantity(quantity)
                .build();

        Integer price = inventory.getPrice() * quantity;
        inventoryHistoryRepository.save(history);

        orderEvent.setTotalPrice(price);
        orderEvent.setStatus(INVENTORY_RESERVED);
        inventoryProducer.async(outboundSuccessEventChannel, orderEvent.getTransactionId(), orderEvent);
    }

    public void processOrderCancel(OrderEvent orderEvent, String transactionId, Long inventoryId, Integer quantity) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new EntityNotFoundException("processInventoryReserved.orderEvent.inventory"));
        inventory.cancel(quantity);

        InventoryHistory history = InventoryHistory.builder()
                .inventory(inventory)
                .transactionId(transactionId)
                .quantity(quantity)
                .build();
        inventoryHistoryRepository.save(history);

        orderEvent.setStatus(ORDER_CANCEL);
        inventoryProducer.async(outboundFailureEventChannel, orderEvent.getTransactionId(), orderEvent);
    }
}
