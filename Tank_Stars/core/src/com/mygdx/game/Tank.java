package com.mygdx.game;

public abstract class Tank {
    private int health_points;

    Tank(int x){
        this.health_points = x;
    }
    public void shoot(){
        // TO BE ADDED
    }
    public void move(){
        // TO BE ADDED
    }
    public void set_aim(){
        // TO BE ADDED
    }

    public int getHealth_points() {
        return health_points;
    }
}
