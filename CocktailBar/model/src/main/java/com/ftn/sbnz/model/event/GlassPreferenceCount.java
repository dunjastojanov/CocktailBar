package com.ftn.sbnz.model.event;

import com.ftn.sbnz.model.cocktail.Glass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlassPreferenceCount {
    EventType eventType;
    String glass;
    int count;


    public GlassPreferenceCount(EventType eventType, Glass glass, int count) {
        this.glass = glass.name();
        this.count = count;
        this.eventType = eventType;
    }
}
