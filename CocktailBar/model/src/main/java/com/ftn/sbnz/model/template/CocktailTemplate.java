package com.ftn.sbnz.model.template;

import com.ftn.sbnz.model.cocktail.Glass;
import com.ftn.sbnz.model.cocktail.IngredientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
public class CocktailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    @Enumerated(EnumType.STRING)
    private Glass glass;

    @OneToOne
    private RecipeTemplate recipe;

    public IngredientType getIngredientType() {
        return recipe.getIngredientType();
    }

    private double getIngredientAmount() {
        return recipe.getIngredientAmount();
    }

    @Data
    public static class CocktailTemplateDTO {
        String glass;
        Long recipeId;

        public CocktailTemplateDTO(CocktailTemplate cocktail) {
            glass = cocktail.getGlass().toString();
            recipeId = cocktail.getRecipe().getId();
        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CocktailTemplateTemplate {
        Long id;
        IngredientType type;
        double amount;
        String name;

        public CocktailTemplateTemplate(CocktailTemplate cocktailTemplate) {
            this.id = cocktailTemplate.getId();
            this.type = cocktailTemplate.getIngredientType();
            this.amount = cocktailTemplate.getIngredientAmount();
            this.name = cocktailTemplate.getName();
        }
    }


}
