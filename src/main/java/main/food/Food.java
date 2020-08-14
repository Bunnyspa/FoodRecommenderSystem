/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.food;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.agent.state.FoodState;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Dongsoo Seo
 */
public class Food {

    public final String name;
    public final String country;
    public final int id;
    public final Set<String> ingredients;
    public final Nutrition nutrition;

    public Food(String name, String country, int id) {
        this.name = name;
        this.country = country;
        this.id = id;
        this.ingredients = new HashSet<>();

        JSONObject j = Reader.readFDC(id);
        if (j != null) {
            // Nutrients
            JSONArray foodNutrients = j.getJSONArray("foodNutrients");
            double protein = 0, fat = 0, carbs = 0;
            for (int i = 0; i < foodNutrients.length(); i++) {
                JSONObject foodNutrient = foodNutrients.getJSONObject(i);
                switch (foodNutrient.getInt("id")) {
                    case 9459481:
                        protein = foodNutrient.getDouble("amount");
                        break;
                    case 9459482:
                        fat = foodNutrient.getDouble("amount");
                        break;
                    case 9459483:
                        carbs = foodNutrient.getDouble("amount");
                        break;
                    default:
                }
            }
            nutrition = new Nutrition(protein, fat, carbs);
            // Ingredients
            JSONArray inputFoods = j.getJSONArray("inputFoods");
            for (int i = 0; i < inputFoods.length(); i++) {
                JSONObject inputFood = inputFoods.getJSONObject(i);
                String ingredientDescription = inputFood.getString("ingredientDescription");
                ingredients.add(ingredientDescription.split(",")[0]);
            }
        } else {
            this.nutrition = new Nutrition();
        }
    }

    public static Food getSimilar(FoodState state, List<Food> foods) {
        double outSim = 0;
        Food out = foods.get(0);
        for (Food food : foods) {
            double sim = similarity(food, state.ingredient, state.country, state.nutrition);
            if (outSim < sim) {
                outSim = sim;
                out = food;
            }
        }
        return out;
    }

    public static double similarity(Food food, String ingredient, String country, NuType nutritions) {
        double out = (food.ingredients.contains(ingredient) ? 1 : 0)
                + (food.country.equals(country) ? 1 : 0)
                + (food.nutrition.dominant() == nutritions ? 1 : 0);
        return out;
    }

    public static double similarity(Food food, Map<String, Double> ingredients, Map<String, Double> countries, Map<NuType, Double> nutritions) {
        double out = 0;
        out += ingredients.keySet().stream().mapToDouble((o) -> food.ingredients.contains(o) ? ingredients.get(o) : 0).sum();
        out += countries.containsKey(food.country) ? countries.get(food.country) : 0;
        if (nutritions.containsKey(NuType.PROTEIN)) {
            out += food.nutrition.protein * nutritions.get(NuType.PROTEIN);
        }
        if (nutritions.containsKey(NuType.FAT)) {
            out += food.nutrition.fat * nutritions.get(NuType.FAT);
        }
        if (nutritions.containsKey(NuType.CARBS)) {
            out += food.nutrition.carbs * nutritions.get(NuType.CARBS);
        }
        return out;
    }

    @Override
    public String toString() {
        return name;
    }

}
