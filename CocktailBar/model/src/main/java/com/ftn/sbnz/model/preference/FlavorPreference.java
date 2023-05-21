package com.ftn.sbnz.model.preference;

import com.ftn.sbnz.model.cocktail.Flavor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlavorPreference implements Preference {
    private Long referenceId;
    private Flavor flavor;
}
