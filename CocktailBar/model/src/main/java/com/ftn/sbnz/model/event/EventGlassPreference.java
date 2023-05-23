package com.ftn.sbnz.model.event;

import com.ftn.sbnz.model.cocktail.Glass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventGlassPreference {
    private Long eventId;
    private List<Glass> glasses = new ArrayList<>();

    public boolean areGlassesEmpty() {
        return glasses.size() == 0;
    }

    public boolean areNotGlassesEmpty() {
        return glasses.size() > 0;
    }
}
