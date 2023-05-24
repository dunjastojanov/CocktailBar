package com.ftn.sbnz.model.preference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlcoholAmountPreference implements Preference {
    private Long referenceId;
    private AlcoholStrength alcoholStrength;

    public boolean isStrong() {
        return alcoholStrength == AlcoholStrength.STRONG;
    }


    public boolean isMedium() {
        return alcoholStrength == AlcoholStrength.MEDIUM;
    }


    public boolean isLight() {
        return alcoholStrength == AlcoholStrength.LIGHT;
    }


}
