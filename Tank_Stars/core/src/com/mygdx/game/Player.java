package com.mygdx.game;

public class Player {
    private String status;
    private Tank tank_chosen;

    Player(String x, Tank y){
        this.status = x;
        this.tank_chosen = y;
    }

    public String getStatus() {
        return status;
    }

    public Tank getTank_chosen() {
        return tank_chosen;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
