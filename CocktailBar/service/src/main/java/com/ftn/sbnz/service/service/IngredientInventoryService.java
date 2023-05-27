package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.inventory.IngredientInventory;
import com.ftn.sbnz.model.inventory.IngredientInventoryCEPEvent;
import com.ftn.sbnz.service.repository.IngredientInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientInventoryService {
    @Autowired
    private IngredientInventoryRepository ingredientInventoryRepository;


    public IngredientInventory getById(Long id) {
        Optional<IngredientInventory> ingredientInventory = ingredientInventoryRepository.findById(id);
        if (ingredientInventory.isEmpty())
            throw new RuntimeException("There is no ingredient inventory with this id");
        return ingredientInventory.get();
    }

    public void updateIngredientInventory(IngredientInventoryCEPEvent object) {
        IngredientInventory ingredientInventory = getById(object.getId());
        if (ingredientInventory.getAmount() != object.getAmount()) {
            ingredientInventory.setAmount(object.getAmount());
            ingredientInventoryRepository.save(ingredientInventory);
        }
    }
}
