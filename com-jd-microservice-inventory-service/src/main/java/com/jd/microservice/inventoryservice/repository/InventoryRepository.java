package com.jd.microservice.inventoryservice.repository;

import com.jd.microservice.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCode(String skuCode);
}
