package com.ftn.sbnz.model.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlcoholAmountPreferenceCount {
    public String strength;
    public int amount;

    public AlcoholAmountPreferenceCount(String strength, int amount) {
        this.strength = strength;
        this.amount = amount;
    }
}
