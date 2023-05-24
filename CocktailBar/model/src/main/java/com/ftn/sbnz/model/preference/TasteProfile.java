package com.ftn.sbnz.model.preference;

import com.ftn.sbnz.model.cocktail.Flavor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TasteProfile {
    private Long referenceId;
    private Set<Flavor> flavors = new HashSet<>();
}
