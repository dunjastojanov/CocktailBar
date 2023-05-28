package com.ftn.sbnz.model.template;

import com.ftn.sbnz.model.cocktail.IngredientType;
import com.ftn.sbnz.model.cocktail.RecipeIngredient;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class RecipeTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 65535)
    private String instructions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    @ManyToOne
    private RecipeIngredientTemplate recipeIngredientTemplate;

    public IngredientType getIngredientType() {
        return recipeIngredientTemplate.getType();
    }

    public double getIngredientAmount() {
        return recipeIngredientTemplate.getAmount();
    }


    @Data
    public static class RecipeTemplateDTO {
        private List<Long> recipeIngredients;
        private RecipeIngredientTemplate recipeIngredientTemplate;
        private String instructions;

        public RecipeTemplateDTO(RecipeTemplate recipe) {
            recipeIngredients = recipe.getRecipeIngredients().stream().map(RecipeIngredient::getId).toList();
            recipeIngredientTemplate = recipe.getRecipeIngredientTemplate();
            instructions = recipe.getInstructions();
        }

    }
}
