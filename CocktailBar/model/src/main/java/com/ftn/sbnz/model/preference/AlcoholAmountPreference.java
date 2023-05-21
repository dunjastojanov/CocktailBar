package com.ftn.sbnz.model.preference;

import lombok.Data;

@Data
public class AlcoholAmountPreference implements Preference {
    private Long referenceId;
    private AlcoholStrength alcoholStrength;
}
