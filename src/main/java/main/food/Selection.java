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
public class Selection {

    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    public final Food left, right;
    public int select;

    public Selection(Food left, Food right) {
        this.left = left;
        this.right = right;
        this.select = 0;
    }

    public void select(int select) {
        this.select = select;
    }
}
