package com.ftn.sbnz.model.template;

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

    private String instructions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredientTemplate> recipeIngredientTemplates = new HashSet<>();


    @Data
    public static class RecipeTemplateDTO {
        private List<Long> recipeIngredients;
        private List<Long> recipeIngredientTemplates;
        private String instructions;

        public RecipeTemplateDTO(RecipeTemplate recipe) {
            recipeIngredients = recipe.getRecipeIngredients().stream().map(RecipeIngredient::getId).toList();
            recipeIngredientTemplates = recipe.getRecipeIngredientTemplates().stream().map(RecipeIngredientTemplate::getId).toList();
            instructions = recipe.getInstructions();
        }

    }
}
