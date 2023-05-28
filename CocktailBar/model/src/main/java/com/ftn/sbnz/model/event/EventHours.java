package com.ftn.sbnz.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventHours {
    Long eventId;
    int hours;

    public EventHours(int hours) {
        this.hours = hours;
    }
}
