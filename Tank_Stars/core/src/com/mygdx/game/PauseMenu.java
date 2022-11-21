package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class PauseMenu implements Screen{
    private SpriteBatch batch;
    private Texture player1;
    private Sprite player1_sprite;
    private Texture pause_menu;
    private Sprite pause_menu_sprite;
    private Texture player2;
    private Sprite player2_sprite;
    private Texture img;
    private Sprite img_sprite;
    private Texture ground;
    private Sprite ground_sprite;
    private Texture tank1;
    private Sprite tank1_sprite;

    private Texture pause;
    private Sprite pause_sprite;
    private Texture toggle;
    private Sprite toggle_sprite;
    private Texture health1;
    private Sprite health1_sprite;
    private Texture health2;
    private Sprite health2_sprite;
    static MyGdxGame game;
    private Vector3 coord;
    private OrthographicCamera cam;
    public PauseMenu(MyGdxGame game) {
        this.game = game;
        batch = new SpriteBatch();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        img = new Texture(Gdx.files.internal("back_scene.png"));
        img_sprite = new Sprite(img);
        img_sprite.setSize(w,h);
        ground = new Texture("land_scene.png");
        player1 = new Texture("frost_player1.png");
        player2 = new Texture("buratino_player2.png");
        pause = new Texture("pause.png");
        pause_sprite = new Sprite(pause);
        player1_sprite = new Sprite(player1);
        player2_sprite = new Sprite(player2);
        health1 = new Texture("player1_health.png");
        health2 = new Texture("player2_health.png");
        health1_sprite = new Sprite(health1);
        health2_sprite = new Sprite(health2);
        pause_menu = new Texture("pause_menu.png");
        pause_menu_sprite = new Sprite(pause_menu);
        pause_menu_sprite.setSize(pause_menu_sprite.getWidth()/4, pause_menu_sprite.getHeight()/4);
        pause_menu_sprite.setPosition(851*w/2550, 260*h/1180);
        health1_sprite.setSize(health1.getWidth()/5, health1.getHeight()/5);
        health2_sprite.setSize(health2.getWidth()/5, health2.getHeight()/5);
        health1_sprite.setPosition(434*w/2550, 951*h/1180);
        health2_sprite.setPosition(1455*w/2550, 951*h/1180);
        pause_sprite.setSize(pause.getWidth()/5, pause.getHeight()/5);
        player1_sprite.setSize(player1.getWidth()/8, player1.getHeight()/8);
        player2_sprite.setSize(player2.getWidth()/8, player2.getHeight()/8);
        player1_sprite.setPosition(727*w/2550, 600*h/1180);
        player2_sprite.setPosition(1859*w/2550, 620*h/1180);
        pause_sprite.setPosition(85*w/2550, 1000*h/1180);
        ground_sprite = new Sprite(ground);
        ground_sprite.setSize(ground.getWidth()/5, ground.getHeight()/5);
        //tank1 = new Texture("spec")
        //tank1_sprite = new Sprite(tank1);
        toggle = new Texture("toggle.png");
        toggle_sprite = new Sprite(toggle);
        toggle_sprite.setSize(toggle.getWidth()/5, toggle.getHeight()/5);
        toggle_sprite.setPosition(210*w/2550, 110*h/1180);
        this.coord = new Vector3();
        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        img_sprite.draw(batch);
        ground_sprite.draw(batch);

        player1_sprite.draw(batch);
        player2_sprite.draw(batch);
        pause_sprite.draw(batch);
        toggle_sprite.draw(batch);
        health1_sprite.draw(batch);
        health2_sprite.draw(batch);
        pause_menu_sprite.draw(batch);
        batch.end();
        handleTouch();
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
        img.dispose();
        pause.dispose();
        ground.dispose();
        player1.dispose();
        player2.dispose();
    }
    public void handleTouch(){
        if(Gdx.input.justTouched()) {
            game.setScreen(new Scene(game));
        }
    }
}

