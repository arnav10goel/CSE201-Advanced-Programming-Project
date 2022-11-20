package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class LoadScreen implements Screen {
    SpriteBatch batch;
    Texture img;
    Sprite img_sprite;

    static MyGdxGame game;
    Vector3 coord;
    OrthographicCamera cam;

    public LoadScreen(MyGdxGame game){
        LoadScreen.game = game;
        batch = new SpriteBatch();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        img = new Texture(Gdx.files.internal("tankstars_loadpage.png"));
        img_sprite = new Sprite(img);
        img_sprite.setSize(w,h);
        this.coord = new Vector3();
        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        img_sprite.draw(batch);
        //batch.draw(img, 10, 10);
        batch.end();

        this.handleTouch();
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
    }

    void handleTouch(){
        if(Gdx.input.justTouched()){
            coord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(coord);

            float touch_x = coord.x;
            float touch_y = coord.y;

            if(touch_x >= img_sprite.getX() && touch_x <= (img_sprite.getX() + img_sprite.getWidth()) && touch_y >= img_sprite.getY() && touch_y <= (img_sprite.getY() + img_sprite.getHeight())){
                game.setScreen(new MainMenu(game));
            }
        }
    }
}