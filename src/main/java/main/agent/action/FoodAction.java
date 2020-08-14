/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.agent.action;

import aima.core.agent.Action;
import main.agent.state.FoodState;
import main.food.NuType;

/**
 *
 * @author Dongsoo Seo
 */
public class FoodAction implements Action {

    public final String ingredient;
    public final String country;
    public final NuType nutrition;

    public FoodAction(String ingredient, String country, NuType nutrition) {
        this.ingredient = ingredient;
        this.country = country;
        this.nutrition = nutrition;
    }

    public boolean equalsState(FoodState s) {
        return ingredient.equals(s.ingredient)
                && country.equals(s.country)
                && nutrition.equals(s.nutrition);
    }

    public FoodState toState() {
        return new FoodState(ingredient, country, nutrition);
    }

    @Override
    public String toString() {
        return country + " " + ingredient + " " + nutrition.toString();
    }

    @Override
    public boolean isNoOp() {
        return false;
    }

}
