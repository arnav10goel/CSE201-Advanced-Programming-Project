package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ground {
    private ArrayList<Float> points = new ArrayList<>();
    public ground(){
        int val=1 + (int)(Math.random() * 3);
        if(val<=1){
            this.addpoints();
        }else if(val>1 && val<=2){
            this.addpoints2();
        }else{
            this.addpoints3();
        }
    }

    public void addpoints() {
        int width = 600;
        int height = 400;
        double amp = 0.1*(height);
        //double phase = Math.random()*(3.14/2);
        double h = 0.25*(height);
        double c = Math.random()*(5);
        for(int i=0;i<width;i++){
            points.add((float)(h+amp*(Math.sin(0.01*2*i)+Math.sin(0.01*(-1)*i))));
        }
    }
    public void addpoints2() {
        int width = 600;
        int height = 400;
        double amp = 0.1*(height);
        double h = 0.25*(height);
        double c = Math.random()*(5);
        for(int i=0;i<width;i++){
            points.add((float)(h+amp*(Math.sin(0.01*2*i)+Math.cos(0.01*(-1)*i))));
        }
    }
    public void addpoints3() {
        int width = 600;
        int height = 400;
        double amp = 0.1*(height);
        //double phase = Math.random()*(3.14/2);
        double h = 0.25*(height);
        double c = Math.random()*(5);
        for(int i=0;i<width;i++){
            points.add((float)(h+amp*(Math.sin(0.01*(1)*i)-Math.cos(0.01*(-0.5)*i)+Math.cos(0.01*(1.5)*i))));
        }
    }


    public void plot (@NotNull ShapeRenderer sr) {

        int width = 600;
        int height = 400;
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.WHITE);
        for (int i = 0; i < width; i++) {
            sr.setColor(67/255f, 34/255f, 16/255f, 1);
            sr.rect(i,0,1,points.get(i));
        }
        sr.end();
    }

}
