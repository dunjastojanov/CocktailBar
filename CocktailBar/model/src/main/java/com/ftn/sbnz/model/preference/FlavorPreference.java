package com.ftn.sbnz.model.preference;

import com.ftn.sbnz.model.cocktail.Flavor;
import lombok.Data;

@Data
public class FlavorPreference implements Preference {
    private Long referenceId;
    private Flavor flavor;
}
