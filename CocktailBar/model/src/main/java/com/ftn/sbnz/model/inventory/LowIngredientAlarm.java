package com.ftn.sbnz.model.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LowIngredientAlarm {
    private String ingredientName;

    public LowIngredientAlarm(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
