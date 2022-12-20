package com.mygdx.game;

import com.mygdx.game.Tanks.Tank;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private String status;
    private Tank tank_chosen;

    private String tank_status;
    private int health_dynamic;

    //Implemented flyweight design pattern

    private static Map<String, Player> instances = new HashMap<>();

    public static Player getInstance(String x, String tank_status, Tank y){
        String key = x + " " + tank_status + " " + y;
        if(!instances.containsKey(key)){
            instances.put(key, new Player(x, tank_status, y));
        }
        return instances.get(key);
    }
    private Player(String x, String tank_status, Tank y){
        this.status = x;
        this.tank_status = tank_status;
        this.tank_chosen = y;
        this.health_dynamic = this.tank_chosen.getHealth_points();
    }

    public String getStatus() {
        return status;
    }

    public Tank getTank_chosen() {
        return tank_chosen;
    }

    public void setTank_status(String tank_status) {
        this.tank_status = tank_status;
    }

    public String getTank_status() {
        return tank_status;
    }

    public void setTank_chosen(Tank tank_chosen) {
        this.tank_chosen = tank_chosen;
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
