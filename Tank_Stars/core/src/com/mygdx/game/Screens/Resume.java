package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Ground;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Player;
import com.mygdx.game.Serialiser;
import com.mygdx.game.Tanks.Tank;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Resume implements Screen {
    private SpriteBatch batch;
    private Texture img;
    private Sprite img_sprite;

    static MyGdxGame game;
    private Vector3 coord;
    private float w;
    private float h;
    private OrthographicCamera cam;

    public Resume(MyGdxGame game){
        Resume.game = game;
        batch = new SpriteBatch();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        img = new Texture(Gdx.files.internal("resume.png"));
        img_sprite = new Sprite(img);
        img_sprite.setSize(w,h);
        this.w = Gdx.graphics.getWidth();
        this.h = Gdx.graphics.getHeight();
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
        batch.end();
        try {
            this.handleTouch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
    public void handleTouch() throws IOException, ClassNotFoundException {
        if(Gdx.input.justTouched()) {
            coord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(coord);

            float touch_x = coord.x;
            float touch_y = coord.y;
            System.out.println("X: " + touch_x);
            System.out.println("y: " + touch_y);
            if(touch_x >= 25 && touch_x <= 45 && touch_y >= 280 && touch_y <= 320){
                game.setScreen(new MainMenu(game));
            }
            else if(touch_x >= 45 && touch_x <= 210 && touch_y >= 165 && touch_y <= 240){
                if(Serialiser.getCount_saved_games() == 0){
                    System.out.println("No saved games");
                }
                else{
                    Ground myground = null;
                    Player p1 = null;
                    Tank t1 = null;
                    Tank t2 = null;
                    Player p2 = null;
                    int val = Serialiser.getCount_saved_games();
                    FileInputStream filein_p1 = new FileInputStream("/Users/arnav/Desktop/LibGDX_Projects/Tank_Stars(do_edits_here)/assets/player1_1.ser");
                    FileInputStream filein_p2 = new FileInputStream("/Users/arnav/Desktop/LibGDX_Projects/Tank_Stars(do_edits_here)/assets/player2_1.ser");
                    FileInputStream filein_t1 = new FileInputStream("/Users/arnav/Desktop/LibGDX_Projects/Tank_Stars(do_edits_here)/assets/tank1_1.ser");
                    FileInputStream filein_t2 = new FileInputStream("/Users/arnav/Desktop/LibGDX_Projects/Tank_Stars(do_edits_here)/assets/tank2_1.ser");
                    FileInputStream filein_ground = new FileInputStream("/Users/arnav/Desktop/LibGDX_Projects/Tank_Stars(do_edits_here)/assets/ground1_1.ser");
                    ObjectInputStream in_p1 = new ObjectInputStream(filein_p1);
                    ObjectInputStream in_p2 = new ObjectInputStream(filein_p2);
                    ObjectInputStream in_t1 = new ObjectInputStream(filein_t1);
                    ObjectInputStream in_t2 = new ObjectInputStream(filein_t2);
                    ObjectInputStream in_ground = new ObjectInputStream(filein_ground);
                    p1 = (Player)in_p1.readObject();
                    p2 = (Player)in_p2.readObject();
                    t1 = (Tank)in_t1.readObject();
                    t2 = (Tank)in_t2.readObject();
                    myground = (Ground)in_ground.readObject();
                    in_p1.close();
                    in_p2.close();
                    in_t1.close();
                    in_t2.close();
                    in_ground.close();
                    filein_p1.close();
                    filein_p2.close();
                    filein_t1.close();
                    filein_t2.close();
                    filein_ground.close();
                    Serialiser.setCount_saved_games(Serialiser.getCount_saved_games()-1);
                    game.setScreen(new Scene(game, p1, p2, myground, t1, t2));
                }
            }
        }
    }
}

