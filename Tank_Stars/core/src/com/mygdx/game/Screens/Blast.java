package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Blast {
    public static final float ftime=0.5f;
    public static final int subval=10;
    public static final int size=5;
    public boolean rem=false;
    private static Animation a=null;
    public float pos_x;
    public float pos_y;
    public float stime;

    public Blast (float px, float py){
        this.pos_x=px;
        this.pos_y=py;
        stime=0;

        if (a==null){
            a=new Animation<>(ftime, TextureRegion.split(new Texture("blast.png"),size,size)[0]);
        }
    }

    public void update(float dtime){
        stime=stime+dtime;
        if(a.isAnimationFinished(stime)){
            rem=true;
        }
    }

    public void render (SpriteBatch batch){
        batch.draw((Texture) a.getKeyFrame(stime),pos_x,pos_y);
    }
}
