package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PauseMenu implements Screen{
    private SpriteBatch batch;
    private Texture pause_menu;
    private Sprite pause_menu_sprite;
    private Texture img;
    private static int ResumeFlag;
    private Sprite img_sprite;

    private Texture pause;
    private Sprite pause_sprite;

    private float w;
    private float h;

    static MyGdxGame game;
    private Vector3 coord;
    private OrthographicCamera cam;
    public PauseMenu(MyGdxGame game) {
        this.game = game;
        batch = new SpriteBatch();
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        img = new Texture(Gdx.files.internal("back_scene.png"));
        img_sprite = new Sprite(img);
        img_sprite.setSize(w,h);
        pause = new Texture("pause.png");
        pause_sprite = new Sprite(pause);
        pause_menu = new Texture("pause_menu.png");
        pause_menu_sprite = new Sprite(pause_menu);
        pause_menu_sprite.setSize(pause_menu_sprite.getWidth()/4, pause_menu_sprite.getHeight()/4);
        pause_menu_sprite.setPosition(810*w/2550, 260*h/1180);

        pause_sprite.setSize(pause.getWidth()/5, pause.getHeight()/5);
        pause_sprite.setPosition(85*w/2550, 1000*h/1180);
        //tank1 = new Texture("spec")
        //tank1_sprite = new Sprite(tank1);
        this.coord = new Vector3();
        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false);
        this.ResumeFlag = 0;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        img_sprite.draw(batch);
        pause_sprite.draw(batch);
        pause_menu_sprite.draw(batch);
        batch.end();
        try {
            handleTouch();
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
        pause.dispose();
    }
    public void handleTouch() throws IOException, ClassNotFoundException { // WILL CHANGE WHEN I WILL IMPLEMENT SERIALISATION
        if(Gdx.input.justTouched()) {
            coord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(coord);

            float touch_x = coord.x;
            float touch_y = coord.y;
            if(touch_x >= 1078*w/2550 && touch_x <= 1500*w/2550 && touch_y >= 630*h/1180 && touch_y <= 750*h/1180){ //RESUME BUTTON
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
                this.ResumeFlag = 1;
                game.setScreen(new Scene(game, p1, p2, myground, t1, t2));
            }
            else if(touch_x >= 1078*w/2550 && touch_x <= 1500*w/2550 && touch_y >= 460*h/1180 && touch_y <= 580*h/1180){
                Gdx.app.exit();
            }
            else if(touch_x >= 1078*w/2550 && touch_x <= 1500*w/2550 && touch_y >= 286*h/1180 && touch_y <= 410*h/1180){
                game.setScreen(new MainMenu(game));
            }

        }
    }

    public static int getResumeFlag() {
        return ResumeFlag;
    }

    public static void setResumeFlag(int resumeFlag) {
        ResumeFlag = resumeFlag;
    }
}

