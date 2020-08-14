/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.agent;

import aima.core.learning.reinforcement.PerceptStateReward;
import aima.core.probability.mdp.MarkovDecisionProcess;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import main.agent.action.FoodAction;
import main.agent.state.FoodState;
import main.food.Food;
import main.food.NuType;

/**
 *
 * @author Dongsoo Seo
 */
public class MDP implements MarkovDecisionProcess<FoodState, FoodAction> {

    private final List<String> ingredients;
    private final List<String> countries;
    private final List<NuType> nutritions;

    public MDP(List<Food> foods) {
        Set<String> ingredientSet = new HashSet<>();
        Set<String> countrySet = new HashSet<>();
        foods.forEach((f) -> {
            ingredientSet.addAll(f.ingredients);
            countrySet.add(f.country);
        });
        ingredients = new ArrayList<>(ingredientSet);
        countries = new ArrayList<>(countrySet);
        nutritions = new ArrayList<>();
        for (NuType value : NuType.values()) {
            nutritions.add(value);
        }
    }

    @Override
    public Set<FoodState> states() {
        Set<FoodState> out = new HashSet<>();
        ingredients.forEach((ing) -> countries.forEach((country) -> nutritions.forEach((nut) -> out.add(new FoodState(ing, country, nut))
        )));
        return out;
    }

    @Override
    public FoodState getInitialState() {
        Random random = new Random();
        return new FoodState(
                ingredients.get(random.nextInt(ingredients.size())),
                countries.get(random.nextInt(countries.size())),
                nutritions.get(random.nextInt(nutritions.size())));
    }

    @Override
    public Set<FoodAction> actions(FoodState s) {
        Set<FoodAction> out = new HashSet<>();
        ingredients.stream().filter((i) -> !i.equals(s.ingredient)).forEach((i) -> out.add(new FoodAction(i, s.country, s.nutrition)));
        countries.stream().filter((c) -> !c.equals(s.country)).forEach((c) -> out.add(new FoodAction(s.ingredient, c, s.nutrition)));
        nutritions.stream().filter((n) -> n != s.nutrition).forEach((n) -> out.add(new FoodAction(s.ingredient, s.country, n)));
        return out;
    }

    @Override
    public double transitionProbability(FoodState sDelta, FoodState s, FoodAction a) {
        return a.toState().equals(sDelta) ? 1 : 0;
    }

    @Override
    public double reward(FoodState s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public PerceptStateReward<FoodState> getPercept(FoodState a, boolean selected) {
        return new PerceptStateReward<FoodState>() {
            @Override
            public FoodState state() {
                return a;
            }

            @Override
            public double reward() {
                return selected ? 1 : -1;
            }
        };
    }

}
