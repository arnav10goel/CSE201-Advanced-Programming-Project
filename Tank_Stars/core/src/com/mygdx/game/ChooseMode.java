package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class ChooseMode implements Screen{

    SpriteBatch batch;
    Texture friend_button;
    Texture comp_button;
    Texture main_menu_bg;
    Sprite friend_button_sprite;
    Sprite comp_button_sprite;

    Sprite main_menu_bg_sprite;

    static MyGdxGame game;
    Vector3 coord;
    OrthographicCamera cam;

    ChooseMode(MyGdxGame game){
        ChooseMode.game = game;
        batch = new SpriteBatch();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        main_menu_bg = new Texture(Gdx.files.internal("Main_Menu_Pg.png"));
        main_menu_bg_sprite = new Sprite(main_menu_bg);
        main_menu_bg_sprite.setSize(w,h);
        friend_button = new Texture(Gdx.files.internal("Friend_Button.png"));
        friend_button_sprite = new Sprite(friend_button);
        comp_button = new Texture("Comp_Button.png");
        comp_button_sprite = new Sprite(comp_button);
        friend_button_sprite.setSize(2*friend_button.getWidth()/9, 2*friend_button.getHeight()/9);
        comp_button_sprite.setSize(2*comp_button.getWidth()/9, 2*comp_button.getHeight()/9);
        comp_button_sprite.setPosition(1760*w/2550, 689*h/1180);
        friend_button_sprite.setPosition(1760*w/2550, 233*h/1180);
        this.coord = new Vector3();
        this.cam = new OrthographicCamera();
        cam.setToOrtho(false);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        main_menu_bg_sprite.draw(batch);
        friend_button_sprite.draw(batch);
        comp_button_sprite.draw(batch);
        batch.end();
        this.checkTouch();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        main_menu_bg.dispose();
        friend_button.dispose();
        comp_button.dispose();
    }

    public void checkTouch(){
        if(Gdx.input.justTouched()) {
            coord.set(Gdx.input.getX(),Gdx.input.getY(), 0);

            cam.unproject(coord);

            float touchX = coord.x;
            float touchY= coord.y;


            if((touchX>= friend_button_sprite.getX()) && touchX<=(friend_button_sprite.getX()+friend_button_sprite.getWidth()) && (touchY>=friend_button_sprite.getY()) && touchY<=(friend_button_sprite.getY()+friend_button_sprite.getHeight()) ) {
                game.setScreen(new ChoiceScreen(game, "Buratino_P1.png", "Buratino_P1"));
            }

        }
    }
}