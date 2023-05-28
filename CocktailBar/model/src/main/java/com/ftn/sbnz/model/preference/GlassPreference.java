package com.ftn.sbnz.model.preference;

import com.ftn.sbnz.model.cocktail.Glass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlassPreference implements Preference {
    private Long referenceId;
    private Glass glass;

    public GlassPreference (String glass) {
        this.glass = Glass.valueOf(glass);
    }

    public void setGlass(String glass) {
        this.glass = Glass.valueOf(glass);
    }

}
