package com.ftn.sbnz.model.event;

import com.ftn.sbnz.model.cocktail.Glass;
import lombok.Data;

@Data
public class EventGlassPreference {
    private Long eventId;
    private Glass[] glasses = new Glass[10];
}
