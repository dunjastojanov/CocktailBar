package com.ftn.sbnz.model.inventory;

import com.ftn.sbnz.model.cocktail.Ingredient;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class IngredientInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;


}
