/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dongsoo Seo
 */
public class Util {

    public static Map<Boolean, Double> normalize(Map<Boolean, Double> input) {
        double vFalse = input.get(false);
        double vTrue = input.get(true);
        return normalize(vFalse, vTrue);
    }

    public static Map<Boolean, Double> normalize(double vFalse, double vTrue) {
        Map<Boolean, Double> out = new HashMap<>();
        double sum = vFalse + vTrue;
        out.put(false, vFalse / sum);
        out.put(true, vTrue / sum);
        return out;
    }
}
