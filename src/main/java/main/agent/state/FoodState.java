/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.agent.state;

import java.util.Objects;
import main.food.NuType;

/**
 *
 * @author Dongsoo Seo
 */
public class FoodState {

    public final String ingredient;
    public final String country;
    public final NuType nutrition;

    public FoodState(String ingredient, String country, NuType nutrition) {
        this.ingredient = ingredient;
        this.country = country;
        this.nutrition = nutrition;
    }

    @Override
    public String toString() {
        return country + " " + ingredient + " " + nutrition.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FoodState other = (FoodState) obj;
        if (!Objects.equals(this.ingredient, other.ingredient)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.nutrition, other.nutrition)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.ingredient);
        hash = 47 * hash + Objects.hashCode(this.country);
        hash = 47 * hash + Objects.hashCode(this.nutrition);
        return hash;
    }

}
