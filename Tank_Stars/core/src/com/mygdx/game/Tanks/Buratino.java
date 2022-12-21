package com.mygdx.game.Tanks;

import org.jetbrains.annotations.NotNull;

public class Buratino extends Tank implements Cloneable{

    public Buratino() {
        super(750, 250);
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public void shoot() {
        super.shoot();
    }

    @Override
    public void set_aim() {
        super.set_aim();
    }

    @Override
    public int getHealth_points() {
        return super.getHealth_points();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }

    @Override
    public int getFuel() {
        return super.getFuel();
    }

    @Override
    public void setFuel(int fuel) {
        super.setFuel(fuel);
    }

    @Override
    public void setHealth_points(int health_points) {
        super.setHealth_points(health_points);
    }


    @Override
    public Tank clone() {
        return super.clone();
    }
}
