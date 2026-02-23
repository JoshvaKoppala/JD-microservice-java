package com.jd.microservice.inventoryservice.controller;

import com.jd.microservice.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode) {
        log.info("Checking inventory for: {}", skuCode);

        return inventoryRepository.findBySkuCode(skuCode).stream()
                .anyMatch(inventory -> inventory.getQuantity() > 0);
    }
}
