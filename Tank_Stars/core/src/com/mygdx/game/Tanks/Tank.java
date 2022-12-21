package com.mygdx.game.Tanks;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public abstract class Tank implements Cloneable, Serializable {
    private int health_points;

    private float x;
    private float y;
    private int fuel;
    private static Tank p1_chosen;

    Tank(int x, int fuel){
        this.health_points = x;
        this.fuel = fuel;
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

    public void setHealth_points(int health_points) {
        this.health_points = health_points;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getFuel() {
        return fuel;
    }

    public int getHealth_points() {
        return health_points;
    }

    public static Tank getP1_chosen() {
        return p1_chosen;
    }

    public static void setP1_chosen(@NotNull Tank p1_chosen) {
        Tank.p1_chosen = p1_chosen.clone();
    }

    public Tank clone(){
        try{
            Tank copy = (Tank)super.clone();
            return copy;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
