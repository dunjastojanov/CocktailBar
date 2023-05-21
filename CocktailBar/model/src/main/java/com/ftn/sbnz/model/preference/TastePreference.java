package com.ftn.sbnz.model.preference;

import com.ftn.sbnz.model.cocktail.Flavor;
import com.ftn.sbnz.model.cocktail.Glass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TastePreference {

    List<Flavor> flavors;
    AlcoholStrength alcoholStrength;
    Glass glass;

}
