/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.food;

/**
 *
 * @author Dongsoo Seo
 */
public class Nutrition {

    public final double protein, fat, carbs;

    public Nutrition() {
        this.protein = 0;
        this.fat = 0;
        this.carbs = 0;
    }

    public Nutrition(double protein, double fat, double carbs) {
        double total = protein + fat + carbs;
        if (total == 0) {
            this.protein = 0;
            this.fat = 0;
            this.carbs = 0;
        } else {
            this.protein = protein / total;
            this.fat = fat / total;
            this.carbs = carbs / total;
        }
    }

    public NuType dominant() {
        if (protein > fat && protein > carbs) {
            return NuType.PROTEIN;
        }
        if (fat > carbs) {
            return NuType.FAT;
        }
        return NuType.CARBS;
    }

}
