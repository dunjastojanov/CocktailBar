package com.ftn.sbnz.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlcoholAmountPreferenceCount {
    public Long eventId;
    public String strength;
    public int amount;

    public AlcoholAmountPreferenceCount(String strength, int amount) {
        this.strength = strength;
        this.amount = amount;
    }
}
