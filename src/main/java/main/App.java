/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.List;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import main.agent.Agent;
import main.food.Food;
import main.food.Reader;
import main.food.Selection;
import main.subject.Subject;
import main.ui.MainFrame;

/**
 *
 * @author Dongsoo Seo
 */
public class App {

    private static final int GUI = 1;
    private static final int TRAINING = 2;

    private final List<Food> foods = Reader.MAP_FOOD_ID;
    private final Agent agent = new Agent(foods);
    private Selection s;
    // private int round = 1;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
        }
        for (int i = 0; i < 10; i++) {
            App app = new App(TRAINING);
        }
    }

    public App(int type) {
        switch (type) {
            case GUI:
                MainFrame mf = new MainFrame(this);
                mf.setVisible(true);
                break;
            case TRAINING:
                simulate();
                break;
            default:
                throw new AssertionError();
        }
    }

    public Selection getSelection() {
        s = agent.getComparison(foods);
        return s;
    }

    public Selection selectAndShowNext(int choice) {
        // Select and save
        s.select(choice);
        agent.receiveChoice(s);

//        if (round >= nRound) {
//            return null;
//        }
        //round++;
        return getSelection();
    }

    public Food showResult() {
        return agent.getBestFood(foods);
    }

    private void simulate() {
        Subject subject = new Subject(foods);
        Selection sel = getSelection();
        for (int iteration = 0; iteration < 1000; iteration++) {
            int choice = subject.choose(sel);
            sel = selectAndShowNext(choice);
        }
        Food result = showResult();
        System.out.println(subject.likeIng + "\t" + subject.likeCountry + "\t" + subject.likeNutrient.toString() + "\t" + result + "\t" + subject.evaluate(result));
    }

}
