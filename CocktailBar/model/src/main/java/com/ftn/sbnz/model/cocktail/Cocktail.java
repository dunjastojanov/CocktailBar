package com.ftn.sbnz.model.cocktail;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Glass glass;
    private String image;
    @OneToOne
    private Recipe recipe;

    @Data
    public static class CocktailDTO {
        String name;
        String glass;
        String image;
        Long recipeId;

        public CocktailDTO(Cocktail cocktail) {
            name = cocktail.getName();
            glass = cocktail.getGlass().toString();
            image = cocktail.getImage();
            recipeId = cocktail.getRecipe().getId();
        }


    }


}
