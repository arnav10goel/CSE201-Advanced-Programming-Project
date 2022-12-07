package com.mygdx.game;

import com.mygdx.game.Tanks.Tank;

public class Player {
    private String status;
    private Tank tank_chosen;
    private int health_dynamic;

    public Player(String x, Tank y){
        this.status = x;
        this.tank_chosen = y;
        this.health_dynamic = this.tank_chosen.getHealth_points();
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

    public int getHealth_dynamic() {
        return health_dynamic;
    }

    public void setHealth_dynamic(int health_dynamic) {
        this.health_dynamic = health_dynamic;
    }
}
