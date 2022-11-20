package com.mygdx.game;

public class Player {
    String status;
    Tank tank_chosen;

    Player(String x, Tank y){
        this.status = x;
        this.tank_chosen = y;
    }
}