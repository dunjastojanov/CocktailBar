package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.inventory.IngredientInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientInventoryRepository extends JpaRepository<IngredientInventory, Long> {
}
