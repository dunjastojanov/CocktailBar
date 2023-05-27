package com.ftn.sbnz.model.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventHours {
    int hours;

    public EventHours(int hours) {
        this.hours = hours;
    }
}
