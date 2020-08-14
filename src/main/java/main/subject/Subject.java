/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import main.food.Food;
import main.food.NuType;
import main.food.Selection;

/**
 *
 * @author Dongsoo Seo
 */
public class Subject {

    private final List<String> ingredients;
    private final List<String> countries;
    private final List<NuType> nutritions;

    public String likeIng;
    public String likeCountry;
    public NuType likeNutrient;

    public Subject(List<Food> foods) {
        Set<String> ingredientSet = new HashSet<>();
        Set<String> countrySet = new HashSet<>();
        foods.forEach((f) -> {
            ingredientSet.addAll(f.ingredients);
            countrySet.add(f.country);
        });
        ingredients = new ArrayList<>(ingredientSet);
        countries = new ArrayList<>(countrySet);
        nutritions = new ArrayList<>();
        nutritions.addAll(Arrays.asList(NuType.values()));
        Random rand = new Random();

        // Ingred
        likeIng = ingredients.get(rand.nextInt(ingredients.size()));

        // Country
        likeCountry = countries.get(rand.nextInt(countries.size()));

        // Nutrient
        likeNutrient = nutritions.get(rand.nextInt(nutritions.size()));

    }

    public int choose(Selection s) {
        double lSim = evaluate(s.left);
        double rSim = evaluate(s.right);
        if (lSim < rSim) {
            return Selection.RIGHT;
        }
        return Selection.LEFT;
    }

    public int evaluate(Food food) {
        return similarity(food, likeIng, likeCountry, likeNutrient);
    }

    public static int similarity(Food food, String ingredient, String country, NuType nutritions) {
        int out = (food.ingredients.contains(ingredient) ? 1 : 0)
                + (food.country.equals(country) ? 1 : 0)
                + (food.nutrition.dominant() == nutritions ? 1 : 0);
        return out;
    }

}
