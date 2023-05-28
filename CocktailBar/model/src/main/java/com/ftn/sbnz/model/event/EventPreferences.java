package com.ftn.sbnz.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventPreferences {
    int maleGuestAmount;
    int femaleGuestAmount;
    int eventHours;
    EventType eventType;

    public int getGuestAmount() {
        return maleGuestAmount + femaleGuestAmount;
    }
}
