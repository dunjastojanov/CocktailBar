package com.ftn.sbnz.model.preference;

import com.ftn.sbnz.model.cocktail.Glass;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlassPreference implements Preference {
    private Long referenceId;
    private Glass glass;

}
