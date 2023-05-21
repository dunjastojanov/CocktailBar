package com.ftn.sbnz.model.preference;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TasteProfile {
    private Long referenceId;
    private Set<Preference> preferences = new HashSet<>();
}
