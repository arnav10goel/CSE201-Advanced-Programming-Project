package com.mygdx.game;

import com.mygdx.game.Screens.LoadScreen;

import java.util.HashMap;

public class Serialiser {
    private static HashMap<Integer, String> ground_save = new HashMap<>();
    private static HashMap <Integer, String> p1_save = new HashMap<>();
    private static HashMap <Integer, String> p2_save = new HashMap<>();
    private static HashMap <Integer, String> t1_save = new HashMap<>();
    private static HashMap <Integer, String> t2_save = new HashMap<>();
    private static int count_saved_games = 0;

    public Serialiser() {
        int val = Serialiser.getCount_saved_games()+1;
        Serialiser.setCount_saved_games(Serialiser.getCount_saved_games()+1);
        ground_save.put(val, "ground1_"+ (val) +".ser");
        p1_save.put(val, "player1_"+ (val) +".ser");
        p2_save.put(val, "player2_"+ (val) +".ser");
        t1_save.put(val, "tank1_"+ (val) +".ser");
        t2_save.put(val, "tank2_"+ (val) +".ser");
    }
    public static HashMap<Integer, String> getGround_save() {
        return ground_save;
    }

    public static HashMap<Integer, String> getP1_save() {
        return p1_save;
    }

    public static HashMap<Integer, String> getP2_save() {
        return p2_save;
    }

    public static HashMap<Integer, String> getT1_save() {
        return t1_save;
    }

    public static HashMap<Integer, String> getT2_save() {
        return t2_save;
    }

    public static int getCount_saved_games() {
        return count_saved_games;
    }

    public static void setCount_saved_games(int count_saved_games) {
        Serialiser.count_saved_games = count_saved_games;
    }


}
