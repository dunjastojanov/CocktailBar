package com.ftn.sbnz.model.preference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    public AlcoholAmountPreference(Long referenceId, String alcoholStrength) {
        this.referenceId = referenceId;
        this.alcoholStrength = AlcoholStrength.valueOf(alcoholStrength);
    }

    public AlcoholAmountPreference(String alcoholStrength) {
        this.alcoholStrength = AlcoholStrength.valueOf(alcoholStrength);
    }


    public AlcoholAmountPreference(Long referenceId, AlcoholStrength alcoholStrength) {
        this.referenceId = referenceId;
        this.alcoholStrength = alcoholStrength;
    }

    public AlcoholAmountPreference(AlcoholStrength alcoholStrength) {
        this.alcoholStrength = alcoholStrength;
    }

}
