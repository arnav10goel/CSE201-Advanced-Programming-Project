package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Ground;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Player;
import com.mygdx.game.Screens.PauseMenu;

public class Scene implements Screen{

    private Player p1; //Player 1 Object
    private int turn; //Indicates whose turn if == 1 then P1; if == 2 then P2
    private Player p2; //Player 2 Object
    private final SpriteBatch batch;
    private Texture player1 = null;
    private final Sprite player1_sprite;

    private Texture player2 = null;
    private final Sprite player2_sprite;
    private final Texture img;
    private final Sprite img_sprite;
    private final Texture pause;
    private final Sprite pause_sprite;
    private final Sprite health1_sprite;
    private final Sprite health2_sprite;
    static MyGdxGame game;
    private final Vector3 coord;
    private final OrthographicCamera cam;

    private final Ground myground;
    private final ShapeRenderer sr;
    public Scene(MyGdxGame game, Player p1, Player p2) {
        this.sr = new ShapeRenderer();
        this.myground = Ground.getInstance(); //Using Singleton Design Pattern to make the ground
        Scene.game = game;
        this.p2 = p2;
        this.p1 = p1;
        batch = new SpriteBatch();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        img = new Texture(Gdx.files.internal("back_scene.png"));
        img_sprite = new Sprite(img);
        img_sprite.setSize(w,h);

        switch (p1.getTank_status()){
            case "Buratino_P1":
                player1 = new Texture("buratino_player1.png");
                break;
            case "Frost_P1":
                player1 = new Texture("frost_player1.png");
                break;
            case "Spectre_P1":
                player1 = new Texture("spectre_player1.png");
                break;
        }
        switch (p2.getTank_status()){
            case "Buratino_P2":
                player2 = new Texture("buratino_player2.png");
                break;
            case "Frost_P2":
                player2 = new Texture("frost_player2.png");
                break;
            case "Spectre_P2":
                player2 = new Texture("spectre_player2.png");
                break;
        }

        pause = new Texture("pause.png");
        pause_sprite = new Sprite(pause);
        player1_sprite = new Sprite(player1);
        player2_sprite = new Sprite(player2);
        Texture health1 = new Texture("player1_health.png");
        Texture health2 = new Texture("player2_health.png");
        health1_sprite = new Sprite(health1);
        health2_sprite = new Sprite(health2);
        health1_sprite.setSize(health1.getWidth()/5, health1.getHeight()/5);
        health2_sprite.setSize(health2.getWidth()/5, health2.getHeight()/5);
        health1_sprite.setPosition(434*w/2550, 951*h/1180);
        health2_sprite.setPosition(1455*w/2550, 951*h/1180);
        pause_sprite.setSize(pause.getWidth()/5, pause.getHeight()/5);
        player1_sprite.setSize(player1.getWidth()/8, player1.getHeight()/8);
        player2_sprite.setSize(player2.getWidth()/8, player2.getHeight()/8);
        p1.getTank_chosen().setX(0.08f*w);
        p1.getTank_chosen().setY(this.myground.getPoints_y().get((int) (0.16f*w)));
        p2.getTank_chosen().setX(0.75f*w);
        p2.getTank_chosen().setY(this.myground.getPoints_y().get((int) (1.5f*w)));
        player1_sprite.setPosition(0.08f*w, this.myground.getPoints_y().get((int) (0.16f*w)));
        player2_sprite.setPosition(0.75f*w, this.myground.getPoints_y().get((int) (1.5f*w)));
        pause_sprite.setPosition(85*w/2550, 1000*h/1180);

        this.coord = new Vector3();
        this.turn = 1;
        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        img_sprite.draw(batch);
        batch.end();
        this.myground.plot(sr);
        batch.begin();
        player1_sprite.draw(batch);
        player2_sprite.draw(batch);
        pause_sprite.draw(batch);
        health1_sprite.draw(batch);
        health2_sprite.draw(batch);
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
        sr.dispose();
        batch.dispose();
        img.dispose();
        pause.dispose();
        player1.dispose();
        player2.dispose();
    }
    public void handleTouch(){
        if(Gdx.input.justTouched()) {
            coord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(coord);

            float touch_x = coord.x;
            float touch_y = coord.y;

            if (touch_x >= pause_sprite.getX() && touch_x <= (pause_sprite.getX() + pause_sprite.getWidth()) && touch_y >= pause_sprite.getY() && touch_y <= (pause_sprite.getY() + pause_sprite.getHeight())) {
                game.setScreen(new PauseMenu(game));
            }
        }
        if(this.turn == 1){
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                float slope = myground.getPoints_y().get((int) (2*p1.getTank_chosen().getX())) - myground.getPoints_y().get((int) (2*p1.getTank_chosen().getX()-1));
                float derivative = (float) Math.atan(slope);
                player1_sprite.setRotation((float) Math.toDegrees(derivative));
                player1_sprite.setOrigin(0,0);
                p1.getTank_chosen().setX(p1.getTank_chosen().getX()+0.5f);
                p1.getTank_chosen().setY(myground.getPoints_y().get((int) (2*p1.getTank_chosen().getX())));
                player1_sprite.setPosition((float) (p1.getTank_chosen().getX()-0.01*player1_sprite.getWidth()), p1.getTank_chosen().getY());
            }
            turn = 2;
        }
        else if(this.turn == 2){
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                float slope = myground.getPoints_y().get((int) (2*p2.getTank_chosen().getX())) - myground.getPoints_y().get((int) (2*p2.getTank_chosen().getX()-1));
                float derivative = (float) Math.atan(slope);
                player2_sprite.setRotation((float) Math.toDegrees(derivative));
                player2_sprite.setOrigin(0,0);
                p2.getTank_chosen().setX(p2.getTank_chosen().getX()-0.5f);
                p2.getTank_chosen().setY(myground.getPoints_y().get((int) (2*p2.getTank_chosen().getX())));
                player2_sprite.setPosition((float) (p2.getTank_chosen().getX()-0.01*player2_sprite.getWidth()), p2.getTank_chosen().getY());
            }
            turn = 1;
        }
    }
}

