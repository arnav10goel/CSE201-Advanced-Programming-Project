package com.mygdx.game.Tanks;

public abstract class Tank {
    private int health_points;

    private float x;
    private float y;

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

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getHealth_points() {
        return health_points;
    }
}
