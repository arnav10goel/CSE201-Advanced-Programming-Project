package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Scene implements Screen{
    SpriteBatch batch;
    Texture player1;
    Sprite player1_sprite;

    Texture player2;
    Sprite player2_sprite;
    Texture img;
    Sprite img_sprite;
    Texture ground;
    Sprite ground_sprite;
    Texture tank1;
    Sprite tank1_sprite;
    static MyGdxGame game;
    Vector3 coord;
    OrthographicCamera cam;
    public Scene(MyGdxGame game) {
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
        player1_sprite = new Sprite(player1);
        player2_sprite = new Sprite(player2);
        player1_sprite.setSize(player1.getWidth()/8, player1.getHeight()/8);
        player2_sprite.setSize(player2.getWidth()/8, player2.getHeight()/8);
        player1_sprite.setPosition(727*w/2550, 600*h/1180);
        player2_sprite.setPosition(1859*w/2550, 620*h/1180);
        ground_sprite = new Sprite(ground);
        ground_sprite.setSize(ground.getWidth()/5, ground.getHeight()/5);
        //tank1 = new Texture("spec")
        //tank1_sprite = new Sprite(tank1);
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
        batch.end();

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

    }
}

