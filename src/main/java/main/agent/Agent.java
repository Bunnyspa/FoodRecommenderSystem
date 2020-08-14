/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.agent.action.FoodAction;
import main.agent.state.FoodState;
import main.food.Food;
import main.food.Selection;

/**
 *
 * @author Dongsoo Seo
 */
public class Agent {

    private final MDP mdp;
    private FoodState ls, rs;
    private final QLearningAgent<FoodState, FoodAction> lq, rq;
    List<Selection> selections = new ArrayList<>();

    public Agent(List<Food> foods) {
        mdp = new MDP(foods);
        ls = mdp.getInitialState();
        rs = mdp.getInitialState();

        double alpha = 0.1;
        double gamma = 0.99;
        int Ne = 5;
        double rPlus = 2;
        lq = new QLearningAgent<>((s) -> mdp.actions(s), alpha, gamma, Ne, rPlus);
        rq = new QLearningAgent<>((s) -> mdp.actions(s), alpha, gamma, Ne, rPlus);
    }

    public void receiveChoice(Selection s) {
        selections.add(s);
    }

    public Selection getComparison(List<Food> foods) {
        if (selections.size() > 0) {
            int select = selections.get(selections.size() - 1).select;
            FoodAction la = lq.execute(mdp.getPercept(ls, select == Selection.LEFT));
            FoodAction ra = rq.execute(mdp.getPercept(rs, select == Selection.RIGHT));
            ls = la.toState();
            rs = ra.toState();
        }
        return new Selection(Food.getSimilar(ls, foods), Food.getSimilar(rs, foods));
    }

    public Food getBestFood(List<Food> foods) {
        Map<FoodState, Double> lu = lq.getUtility();
        Map<FoodState, Double> ru = rq.getUtility();
        Map<FoodState, Double> sum = new HashMap<>(lu);
        ru.keySet().forEach((k) -> {
            if (sum.containsKey(k)) {
                sum.put(k, sum.get(k) + ru.get(k));
            } else {
                sum.put(k, ru.get(k));
            }
        });

        double max = 0;
        FoodState state = ls;
        for (FoodState foodState : sum.keySet()) {
            double d = sum.get(foodState);
            if (max < d) {
                max = d;
                state = foodState;
            }
        }
        return Food.getSimilar(state, foods);
    }

}
