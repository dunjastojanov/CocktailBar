package com.ftn.sbnz.model.template;

import com.ftn.sbnz.model.cocktail.Glass;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CocktailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Glass glass;

    @OneToOne
    private RecipeTemplate recipe;

    @Data
    public static class CocktailTemplateDTO {
        String glass;
        Long recipeId;

        public CocktailTemplateDTO(CocktailTemplate cocktail) {
            glass = cocktail.getGlass().toString();
            recipeId = cocktail.getRecipe().getId();
        }

    }
}
