package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//Used Singleton Design Pattern
public class Ground{
    private final ArrayList<Float> points_y = new ArrayList<>();
    private final ArrayList<Float> points_x = new ArrayList<>();
    private final int height;
    private final int width;
    private static Ground ground = null;
    public static Ground getInstance(){
        if(ground == null){
            ground = new Ground();
        }
        return ground;
    }
    private Ground(){
        this.height = Gdx.graphics.getHeight();
        this.width = Gdx.graphics.getWidth();

        int val=1 + (int)(Math.random() * 3);
        if(val<=1){
            this.addpoints();
        }
        else if(val == 2){
            this.addpoints2();
        }
        else{
            this.addpoints3();
        }
    }

    public void addpoints() {
        double amp = 0.1*(height);
        double h = 0.25*(height);
        double c = Math.random()*(5);
        for(int i=0;i<width;i++){
            float x = (2*i + 1)*(0.5f);
            points_x.add((float) i);
            points_x.add(x);
            points_y.add((float)(h+amp*(Math.sin(0.01*2*i)+Math.sin(0.01*(-1)*i))));
            points_y.add((float)(h+amp*(Math.sin(0.01*2*x)+Math.sin(0.01*(-1)*x))));

        }
        System.out.println(points_x);
        System.out.println(points_y);
    }
    public void addpoints2() {
        double amp = 0.1*(height);
        double h = 0.25*(height);
        for(int i=0;i<width;i++){
            float x = (2*i + 1)*(0.5f);
            points_x.add((float) i);
            points_x.add(x);
            points_y.add((float)(h+amp*(Math.sin(0.01*2*i)+Math.cos(0.01*(-1)*i))));
            points_y.add((float)(h+amp*(Math.sin(0.01*2*x)+Math.cos(0.01*(-1)*x))));
        }
        System.out.println(points_x);
        System.out.println(points_y);
    }
    public void addpoints3() {
        double amp = 0.1*(height);
        double h = 0.25*(height);
        for(int i=0;i<width;i++){
            float x = (2*i + 1)*(0.5f);
            points_x.add((float) i);
            points_x.add(x);
            points_y.add((float)(h+amp*(Math.sin(0.01*(1)*i)-Math.cos(0.01*(-0.5)*i)+Math.cos(0.01*(1.5)*i))));
            points_y.add((float)(h+amp*(Math.sin(0.01*(1)*x)-Math.cos(0.01*(-0.5)*x)+Math.cos(0.01*(1.5)*x))));
        }
        System.out.println(points_x);
        System.out.println(points_y);
    }
    public void plot(@NotNull ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 2*Gdx.graphics.getWidth()-1; i++) {
            sr.setColor(67/255f, 34/255f, 16/255f, 1);
            sr.rect((float) ((float)i*0.5),0,1,points_y.get(i));
        }
        sr.end();
    }

    public ArrayList<Float> getPoints_x() {
        return points_x;
    }

    public ArrayList<Float> getPoints_y() {
        return points_y;
    }
}

