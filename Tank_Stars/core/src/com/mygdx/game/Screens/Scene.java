package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Ground;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Player;
import com.mygdx.game.Serialiser;
import com.mygdx.game.Tanks.Tank;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

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
    private final Texture endgame;
    private final Sprite endgame_sprite;
    private final Sprite pause_sprite;
    private final Sprite health1_sprite;
    private final Sprite health2_sprite;
    private final Sprite player1_won;
    private final Sprite player2_won;
    static MyGdxGame game;
    private final Vector3 coord;
    private final OrthographicCamera cam;

    private final Ground myground;
    private final ShapeRenderer sr;
    private final ShapeRenderer fuelrenderer;
    private final ShapeRenderer hr;
    private int count;
    private float range;
    private double velocity;
    private float range2;
    private double velocity2;
    private int angle;
    private int angle2;
    private float fuel1;
    private float fuel2;
    private double fin;
    private double fin2;
    private float h1;
    private float h2;
    private float w;
    private float h;
    int timer;
    private Sound sound;
    private Bezier<Vector2> path1;
    public Scene(MyGdxGame game, @NotNull Player play1, Player play2, Ground myground, Tank t1, Tank t2) {
        this.sr = new ShapeRenderer();
        this.hr = new ShapeRenderer();
        this.fuelrenderer = new ShapeRenderer();
        this.myground = myground; //Using Singleton Design Pattern to make the ground
        Scene.game = game;
        System.out.println(play1);
        System.out.println(play2);
        System.out.println(t1);
        System.out.println(t2);
        this.p2 = play2;
        this.p1 = play1;
        this.p1.setTank_chosen(t1);
        this.p2.setTank_chosen(t2);
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        this.h1 = (w/5)-10;
        this.h2 = (w/5)-10;
        System.out.println(this.p1.getTank_chosen());
        System.out.println(this.p2.getTank_chosen());
        batch = new SpriteBatch();
        img = new Texture(Gdx.files.internal("back_scene.png"));
        img_sprite = new Sprite(img);
        img_sprite.setSize(w,h);
        fuel1=(float)0.15*w;
        fuel2=(float)0.15*w;
        angle = 4;
        angle2 = 4;
        sound = Gdx.audio.newSound(Gdx.files.internal("explosion-6055.mp3"));

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
        endgame = new Texture("EndGame.png");
        endgame_sprite = new Sprite(endgame);
        endgame_sprite.setPosition(970*w/2550, 513*w/2550);
        endgame_sprite.setSize(endgame.getWidth()/6, endgame.getHeight()/6);
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
        player1_sprite.setPosition(p1.getTank_chosen().getX(), this.myground.getPoints_y().get((int) (2*p1.getTank_chosen().getX()))); //Use getX() here
        player2_sprite.setPosition(p2.getTank_chosen().getX(), this.myground.getPoints_y().get((int) (2*p2.getTank_chosen().getX())));
        pause_sprite.setPosition(85*w/2550, 1000*h/1180);

        Texture player1won = new Texture("player1_won.png");
        Texture player2won = new Texture("player2_won.png");
        player1_won = new Sprite(player1won);
        player2_won = new Sprite(player2won);

        this.coord = new Vector3();
        this.turn = 1;
        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false);
        switch (p2.getTank_status()){
            case "Buratino_P2":
                h2= ((float)(p2.getTank_chosen().getHealth_points())/750)*(h2);
                break;
            case "Frost_P2":
                h2= ((float)(p2.getTank_chosen().getHealth_points())/900)*(h2);
                break;
            case "Spectre_P2":
                h2= ((float)(p2.getTank_chosen().getHealth_points())/1050)*(h2);
                break;
        }
        switch (p1.getTank_status()){
            case "Buratino_P1":
                h1= ((float)(p1.getTank_chosen().getHealth_points())/750)*(h1);
                break;
            case "Frost_P1":
                h1= ((float)(p1.getTank_chosen().getHealth_points())/900)*(h1);
                break;
            case "Spectre_P1":
                h1= ((float)(p1.getTank_chosen().getHealth_points())/1050)*(h1);
                break;
        }
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
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        fuelrenderer.setAutoShapeType(true);
        fuelrenderer.begin(ShapeRenderer.ShapeType.Filled);
        fuelrenderer.setColor(0,0,0,1);
        fuelrenderer.rect(0.08f*w,0.05f*h,(float)0.15*w,(float)0.05*h);
        fuelrenderer.setColor(0,0,0,1);
        fuelrenderer.rect(0.76f*w,0.05f*h,(float)0.15*w,(float)0.05*h);
        fuelrenderer.end();

        hr.setAutoShapeType(true);
        hr.begin(ShapeRenderer.ShapeType.Filled);
        hr.setColor(Color.DARK_GRAY);
        hr.rect((3*w/10)-(w/15),951*h/1180,w/5, h/10);
        hr.setColor(Color.DARK_GRAY);
        hr.rect((w/2)+(w/15),951*h/1180,w/5, h/10);
        hr.end();

        batch.begin();
        player1_sprite.draw(batch);
        player2_sprite.draw(batch);
        pause_sprite.draw(batch);
        if(p1.getTank_chosen().getHealth_points() <= 0 || p2.getTank_chosen().getHealth_points() <= 0){
            endgame_sprite.draw(batch);
            if(Gdx.input.justTouched()) {
                coord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                cam.unproject(coord);

                float touch_x = coord.x;
                float touch_y = coord.y;

                if(touch_x >= endgame_sprite.getX() && touch_x <= endgame_sprite.getX() + endgame_sprite.getWidth() && touch_y >= endgame_sprite.getY() && touch_y <= endgame_sprite.getY() + endgame_sprite.getHeight()){
                    game.setScreen(new MainMenu(game));
                }
            }
        }
        batch.end();
        try {
            this.handleTouch();
        } catch (InterruptedException e) {
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
        sr.dispose();
        fuelrenderer.dispose();
        batch.dispose();
        img.dispose();
        pause.dispose();
        player1.dispose();
        player2.dispose();
    }
    public void handleTouch() throws InterruptedException{
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        ShapeRenderer fsr = new ShapeRenderer();
        fsr.setAutoShapeType(true);
        fsr.begin(ShapeRenderer.ShapeType.Filled);
        fsr.setColor(Color.YELLOW);
        fsr.rect(0.08f*w,0.05f*h,fuel1,(float)0.05*h);
        fsr.setColor(Color.YELLOW);
        fsr.rect(0.76f*w,0.05f*h,fuel2,(float)0.05*h);
        fsr.end();

        hr.setAutoShapeType(true);
        hr.begin(ShapeRenderer.ShapeType.Filled);
        hr.setColor(Color.TEAL);
        hr.rect((3*w/10)-(w/15)+5,951*h/1180+5,h1, -10+h/10);
        hr.setColor(Color.TEAL);
        hr.rect((w/2)+(w/15)+5,951*h/1180+5,h2, -10+h/10);
        hr.end();

        if(Gdx.input.justTouched()) {
            coord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(coord);

            float touch_x = coord.x;
            float touch_y = coord.y;

            if (touch_x >= pause_sprite.getX() && touch_x <= (pause_sprite.getX() + pause_sprite.getWidth()) && touch_y >= pause_sprite.getY() && touch_y <= (pause_sprite.getY() + pause_sprite.getHeight())) {
                try{
                    this.serialise();
                }
                catch (IOException e){
                    System.out.println("File not found");
                }
                game.setScreen(new PauseMenu(game));
            }
        }
        if(p1.getTank_chosen().getHealth_points() > 0 && p2.getTank_chosen().getHealth_points() > 0) {
            if (this.turn == 1) {
                try {
                    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && p1.getTank_chosen().getFuel() > 1) {
                        float slope = myground.getPoints_y().get((int) (2 * p1.getTank_chosen().getX())) - myground.getPoints_y().get((int) (2 * p1.getTank_chosen().getX() - 1));
                        float derivative = (float) Math.atan(slope);
                        fuel1 = (float) (fuel1 - 0.36272);
                        player1_sprite.setRotation((float) Math.toDegrees(derivative));
                        player1_sprite.setOrigin(0, 0);
                        p1.getTank_chosen().setX(p1.getTank_chosen().getX() + 0.5f);
                        p1.getTank_chosen().setY(myground.getPoints_y().get((int) (2 * p1.getTank_chosen().getX())));
                        if (p1.getTank_chosen().getX() >= myground.getPoints_x().size() - 1) {
                            throw new MoveException("Tank out of screen");
                        }
                        player1_sprite.setPosition((float) (p1.getTank_chosen().getX() - 0.01 * player1_sprite.getWidth()), p1.getTank_chosen().getY());
                        p1.getTank_chosen().setFuel(p1.getTank_chosen().getFuel() - 1);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && p1.getTank_chosen().getX() > 1 && p1.getTank_chosen().getFuel() > 1) {
                        float slope = myground.getPoints_y().get((int) (2 * p1.getTank_chosen().getX())) - myground.getPoints_y().get((int) (2 * p1.getTank_chosen().getX() - 1));
                        float derivative = (float) Math.atan(slope);
                        fuel1 = (float) (fuel1 - 0.36272);
                        player1_sprite.setRotation((float) Math.toDegrees(derivative));
                        player1_sprite.setOrigin(0, 0);
                        p1.getTank_chosen().setX(p1.getTank_chosen().getX() - 0.5f);
                        p1.getTank_chosen().setY(myground.getPoints_y().get((int) (2 * p1.getTank_chosen().getX())));
                        if (p1.getTank_chosen().getX() >= myground.getPoints_x().size() - 1) {
                            throw new MoveException("Tank out of screen");
                        }
                        player1_sprite.setPosition((float) (p1.getTank_chosen().getX() - 0.01 * player1_sprite.getWidth()), p1.getTank_chosen().getY());
                        p1.getTank_chosen().setFuel(p1.getTank_chosen().getFuel() - 1);
                    }
                    if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && angle < 22) {
                        angle += 1;
                        System.out.println("Upper key " + angle2);
                    }
                    else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && angle > 4) {
                        angle -= 1;
                        System.out.println("Lower key " + angle2);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                        float pos = p1.getTank_chosen().getX() + player1.getWidth() / 16;
                        ArrayList<Float> pts = myground.getPoints_y();
                        range = w - pos;
                        velocity = Math.pow(range * 10, 0.5);
                        fin = velocity * velocity * Math.sin(2 * Math.toRadians(angle)) / 10;
                        int points = 4;
                        Vector2[] controlPoints = new Vector2[points];
                        float x = (pos);
                        float y = (player1.getHeight() / 16 + pts.get((int) (2 * pos)));
                        Vector2 point = new Vector2(x, y);
                        controlPoints[0] = point;
                        float x1 = (float) (pos + (fin) / 3);
                        float y1 = pts.get((int) (2 * (pos + (fin) / 3))) + h / 3;
                        Vector2 point1 = new Vector2(x1, y1);
                        controlPoints[1] = point1;
                        float x2 = (float) (pos + 2 * (fin) / 3);
                        float y2 = pts.get((int) (2 * (pos + 2 * (fin) / 3))) + h / 3;
                        Vector2 point2 = new Vector2(x2, y2);
                        controlPoints[2] = point2;
                        float x3 = (float) (pos + fin);
                        float y3 = (pts.get((int) (2 * (pos + fin))));
                        Vector2 point3 = new Vector2(x3, y3);
                        controlPoints[3] = point3;

                        path1 = new Bezier<>(controlPoints);
                        // setup ShapeRenderer
                        ShapeRenderer srt = new ShapeRenderer();
                        srt.setAutoShapeType(true);
                        for (int i = 0; i < 100; i += 5) {
                            float t = i / 100f;
                            Vector2 st = new Vector2();
                            Vector2 end = new Vector2();
                            path1.valueAt(st, t + 0.01f);
                            path1.valueAt(end, t - 0.01f);
                            srt.begin();
                            Gdx.gl.glLineWidth(2);
                            //sr.setColor(1, 0.53f, 0.53f, 1);
                            srt.line(st.x, st.y, end.x, end.y);
                            srt.line(st.x, st.y, end.x, end.y);
                            srt.end();
                        }
                    }
                    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                        sound.play(1.0f);
                        float pos = p1.getTank_chosen().getX() + player1.getWidth() / 16;
                        ArrayList<Float> pts = myground.getPoints_y();
                        range = calcrange(w,pos);
                        velocity = Math.pow(range * 10, 0.5);
                        fin = velocity * velocity * Math.sin(2 * Math.toRadians(angle)) / 10;
                        int points = 4;
                        Vector2[] controlPoints = new Vector2[points];
                        float x = (pos);
                        float y = (player1.getHeight() / 16 + pts.get((int) (2 * pos)));
                        Vector2 point = new Vector2(x, y);
                        controlPoints[0] = point;
                        float x1 = (float) (pos + (fin) / 3);
                        float y1 = pts.get((int) (2 * (pos + (fin) / 3))) + h / 3;
                        Vector2 point1 = new Vector2(x1, y1);
                        controlPoints[1] = point1;
                        float x2 = (float) (pos + 2 * (fin) / 3);
                        float y2 = pts.get((int) (2 * (pos + 2 * (fin) / 3))) + h / 3;
                        Vector2 point2 = new Vector2(x2, y2);
                        controlPoints[2] = point2;
                        float x3 = (float) (pos + fin);
                        float y3 = (pts.get((int) (2 * (pos + fin))));
                        Vector2 point3 = new Vector2(x3, y3);
                        controlPoints[3] = point3;
                        path1 = new Bezier<>(controlPoints);

                        // setup ShapeRenderer
                        ShapeRenderer srt = new ShapeRenderer();
                        srt.setAutoShapeType(true);
                        for (int i = 0; i < 100; i += 1) {
                            float t = i / 100f;
                            Vector2 st = new Vector2();
                            Vector2 end = new Vector2();
                            path1.valueAt(st, t + 0.01f);
                            path1.valueAt(end, t - 0.01f);
                            srt.begin();
                            Gdx.gl.glLineWidth(2);
                            sr.setColor(Color.ORANGE);
                            srt.line(st.x, st.y, end.x, end.y);
                            srt.line(st.x, st.y, end.x, end.y);
                            srt.end();
                        }

                        System.out.println(Math.abs(p2.getTank_chosen().getX() - p1.getTank_chosen().getX() - fin));
                        if (Math.abs(p2.getTank_chosen().getX() - p1.getTank_chosen().getX() - fin) <= 5 + player1.getWidth() / 16) {
                            p2.getTank_chosen().setHealth_points(p2.getTank_chosen().getHealth_points() - 150);
                            switch (p2.getTank_status()){
                                case "Buratino_P2":
                                    h2= ((float)(p2.getTank_chosen().getHealth_points())/750)*(h2);
                                    break;
                                case "Frost_P2":
                                    h2= ((float)(p2.getTank_chosen().getHealth_points())/900)*(h2);
                                    break;
                                case "Spectre_P2":
                                    h2= ((float)(p2.getTank_chosen().getHealth_points())/1050)*(h2);
                                    break;
                            }
                        }else if (Math.abs(p2.getTank_chosen().getX() - p1.getTank_chosen().getX() - fin) <= 7 + player1.getWidth() / 16) {
                            p2.getTank_chosen().setHealth_points(p2.getTank_chosen().getHealth_points() - 75);
                            switch (p2.getTank_status()){
                                case "Buratino_P2":
                                    h2= ((float)(p2.getTank_chosen().getHealth_points())/750)*(h2);
                                    break;
                                case "Frost_P2":
                                    h2= ((float)(p2.getTank_chosen().getHealth_points())/900)*(h2);
                                    break;
                                case "Spectre_P2":
                                    h2= ((float)(p2.getTank_chosen().getHealth_points())/1050)*(h2);
                                    break;
                            }
                        }else if (Math.abs(p2.getTank_chosen().getX() - p1.getTank_chosen().getX() - fin) <= 10 + player1.getWidth() / 16) {
                            p2.getTank_chosen().setHealth_points(p2.getTank_chosen().getHealth_points() - 50);
                            switch (p2.getTank_status()){
                                case "Buratino_P2":
                                    h2= ((float)(p2.getTank_chosen().getHealth_points())/750)*(h2);
                                    break;
                                case "Frost_P2":
                                    h2= ((float)(p2.getTank_chosen().getHealth_points())/900)*(h2);
                                    break;
                                case "Spectre_P2":
                                    h2= ((float)(p2.getTank_chosen().getHealth_points())/1050)*(h2);
                                    break;
                            }
                        }

                        turn = 2;
                        p2.getTank_chosen().setFuel(250);
                        fuel2=(float)0.15*w;
                        System.out.println(p2.getTank_chosen().getHealth_points());
                    }
                } catch (MoveException e) {
                    p1.getTank_chosen().setX(p1.getTank_chosen().getX() - 0.5f);
                }
            } else if (this.turn == 2) {
                try {
                    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && p2.getTank_chosen().getX() > 1 && p2.getTank_chosen().getFuel() > 1) {
                        float slope = myground.getPoints_y().get((int) (2 * p2.getTank_chosen().getX())) - myground.getPoints_y().get((int) (2 * p2.getTank_chosen().getX() - 1));
                        float derivative = (float) Math.atan(slope);
                        fuel2 = (float) (fuel2 - 0.36272);
                        count += 1;
                        System.out.println(count);
                        System.out.println(fuel2);
                        player2_sprite.setRotation((float) Math.toDegrees(derivative));
                        player2_sprite.setOrigin(0, 0);
                        p2.getTank_chosen().setX(p2.getTank_chosen().getX() - 0.5f);
                        p2.getTank_chosen().setY(myground.getPoints_y().get((int) (2 * p2.getTank_chosen().getX())));
                        if (p1.getTank_chosen().getX() >= myground.getPoints_x().size() - 1) {
                            throw new MoveException("Tank out of screen");
                        }
                        player2_sprite.setPosition((float) (p2.getTank_chosen().getX() - 0.01 * player2_sprite.getWidth()), p2.getTank_chosen().getY());
                        p2.getTank_chosen().setFuel(p2.getTank_chosen().getFuel() - 1);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && p2.getTank_chosen().getFuel() > 1) {
                        float slope = myground.getPoints_y().get((int) (2 * p2.getTank_chosen().getX())) - myground.getPoints_y().get((int) (2 * p2.getTank_chosen().getX() - 1));
                        float derivative = (float) Math.atan(slope);
                        fuel2 = (float) (fuel2 - 0.36272);
                        player2_sprite.setRotation((float) Math.toDegrees(derivative));
                        player2_sprite.setOrigin(0, 0);
                        p2.getTank_chosen().setX(p2.getTank_chosen().getX() + 0.5f);
                        p2.getTank_chosen().setY(myground.getPoints_y().get((int) (2 * p2.getTank_chosen().getX())));
                        if (p1.getTank_chosen().getX() >= myground.getPoints_x().size() - 1) {
                            throw new MoveException("Tank out of screen");
                        }
                        player2_sprite.setPosition((float) (p2.getTank_chosen().getX() - 0.01 * player2_sprite.getWidth()), p2.getTank_chosen().getY());
                        p2.getTank_chosen().setFuel(p2.getTank_chosen().getFuel() - 1);
                    }
                    if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && angle2 < 22) {
                        angle2 += 1;
                        System.out.println("Upper key " + angle2);
                    } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && angle2 > 4) {
                        angle2 -= 1;
                        System.out.println("Lower key " + angle2);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                        float pos2 = p2.getTank_chosen().getX() + player2.getWidth() / 16;
                        ArrayList<Float> pts = myground.getPoints_y();
                        range2 = calcrange(w , pos2);
                        velocity2 = Math.pow(range2 * 10, 0.5);
                        fin2 = velocity2 * velocity2 * Math.sin(2 * Math.toRadians(angle2)) / 10;
                        int points2 = 4;
                        Vector2[] controlPoints2 = new Vector2[points2];
                        float a = (pos2);
                        float b = (pts.get((int) (2 * pos2)));
                        Vector2 point01 = new Vector2(a, b);
                        controlPoints2[0] = point01;
                        float a1 = (float) (pos2 - (fin2) / 3);
                        float b1 = pts.get((int) (2 * (pos2 - (fin2) / 3))) + h / 5;
                        Vector2 point02 = new Vector2(a1, b1);
                        controlPoints2[1] = point02;
                        float a2 = (float) (pos2 - 2 * (fin2) / 3);
                        float b2 = pts.get((int) (2 * (pos2 - 2 * (fin2) / 3))) + h / 5;
                        Vector2 point03 = new Vector2(a2, b2);
                        controlPoints2[2] = point03;
                        float a3 = (float) (pos2 - fin2);
                        float b3 = (pts.get((int) (2 * (pos2 - fin2))));
                        Vector2 point04 = new Vector2(a3, b3);
                        controlPoints2[3] = point04;

                        path1 = new Bezier<Vector2>(controlPoints2);
                        ShapeRenderer sr2 = new ShapeRenderer();
                        sr2.setAutoShapeType(true);
                        for (int i = 0; i < 100; i += 5) {
                            float t = i / 100f;
                            Vector2 st = new Vector2();
                            Vector2 end = new Vector2();
                            path1.valueAt(st, t + 0.01f);
                            path1.valueAt(end, t - 0.01f);

                            sr2.begin();
                            Gdx.gl.glLineWidth(2);
                            sr2.line(st.x, st.y, end.x, end.y);
                            sr2.line(st.x, st.y, end.x, end.y);
                            sr2.end();
                        }
                    }
                    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                        sound.play(1.0f);
                        float pos2 = p2.getTank_chosen().getX() + player2.getWidth() / 16;
                        ArrayList<Float> pts = myground.getPoints_y();
                        range2 = calcrange(w , pos2);
                        velocity2 = Math.pow(range2 * 10, 0.5);
                        fin2 = velocity2 * velocity2 * Math.sin(2 * Math.toRadians(angle2)) / 10;
                        int points2 = 4;
                        Vector2[] controlPoints2 = new Vector2[points2];
                        float a = (pos2);
                        float b = (pts.get((int) (2 * pos2)));
                        Vector2 point01 = new Vector2(a, b);
                        controlPoints2[0] = point01;
                        float a1 = (float) (pos2 - (fin2) / 3);
                        float b1 = pts.get((int) (2 * (pos2 - (fin2) / 3))) + h / 5;
                        Vector2 point02 = new Vector2(a1, b1);
                        controlPoints2[1] = point02;
                        float a2 = (float) (pos2 - 2 * (fin2) / 3);
                        float b2 = pts.get((int) (2 * (pos2 - 2 * (fin2) / 3))) + h / 5;
                        Vector2 point03 = new Vector2(a2, b2);
                        controlPoints2[2] = point03;
                        float a3 = (float) (pos2 - fin2);
                        float b3 = (pts.get((int) (2 * (pos2 - fin2))));
                        Vector2 point04 = new Vector2(a3, b3);
                        controlPoints2[3] = point04;

                        path1 = new Bezier<Vector2>(controlPoints2);
                        ShapeRenderer sr2 = new ShapeRenderer();
                        sr2.setAutoShapeType(true);
                        for (int i = 0; i < 100; i += 1) {
                            float t = i / 100f;
                            Vector2 st = new Vector2();
                            Vector2 end = new Vector2();
                            path1.valueAt(st, t + 0.01f);
                            path1.valueAt(end, t - 0.01f);
                            sr2.begin();
                            Gdx.gl.glLineWidth(2);
                            sr2.setColor(Color.ORANGE);
                            sr2.line(st.x, st.y, end.x, end.y);
                            sr2.line(st.x, st.y, end.x, end.y);
                            sr2.end();
                        }
                        if (Math.abs(p2.getTank_chosen().getX() - p1.getTank_chosen().getX() - fin2) <= 5 + player1.getWidth() / 16) {
                            p1.getTank_chosen().setHealth_points(p1.getTank_chosen().getHealth_points() - 150);
                            switch (p1.getTank_status()){
                                case "Buratino_P1":
                                    h1= ((float)(p1.getTank_chosen().getHealth_points())/750)*(h1);
                                    break;
                                case "Frost_P1":
                                    h1= ((float)(p1.getTank_chosen().getHealth_points())/900)*(h1);
                                    break;
                                case "Spectre_P1":
                                    h1= ((float)(p1.getTank_chosen().getHealth_points())/1050)*(h1);
                                    break;
                            }

                        }else if (Math.abs(p2.getTank_chosen().getX() - p1.getTank_chosen().getX() - fin2) <= 7 + player1.getWidth() / 16) {
                            p1.getTank_chosen().setHealth_points(p1.getTank_chosen().getHealth_points() - 75);
                            switch (p1.getTank_status()){
                                case "Buratino_P1":
                                    h1= ((float)(p1.getTank_chosen().getHealth_points())/750)*(h1);
                                    break;
                                case "Frost_P1":
                                    h1= ((float)(p1.getTank_chosen().getHealth_points())/900)*(h1);
                                    break;
                                case "Spectre_P1":
                                    h1= ((float)(p1.getTank_chosen().getHealth_points())/1050)*(h1);
                                    break;
                            }

                        }else if (Math.abs(p2.getTank_chosen().getX() - p1.getTank_chosen().getX() - fin2) <= 10 + player1.getWidth() / 16) {
                            p1.getTank_chosen().setHealth_points(p1.getTank_chosen().getHealth_points() - 50);
                            switch (p1.getTank_status()){
                                case "Buratino_P1":
                                    h1= ((float)(p1.getTank_chosen().getHealth_points())/750)*(h1);
                                    break;
                                case "Frost_P1":
                                    h1= ((float)(p1.getTank_chosen().getHealth_points())/900)*(h1);
                                    break;
                                case "Spectre_P1":
                                    h1= ((float)(p1.getTank_chosen().getHealth_points())/1050)*(h1);
                                    break;
                            }

                        }
                        turn = 1;
                        p1.getTank_chosen().setFuel(250);
                        fuel1=(float)0.15*w;
                        System.out.println(p1.getTank_chosen().getHealth_points());
                        System.out.println("fin2:" + fin2);
                        System.out.println("xp1:" + p1.getTank_chosen().getX());
                        System.out.println("xp2:" + p2.getTank_chosen().getX());
                    }

                } catch (MoveException e) {
                    p2.getTank_chosen().setX(p2.getTank_chosen().getX() - 0.5f);
                }
            }
        }
        else{

        }
    }
    public static float calcrange(float w, float pos){
        return w-pos;
    }

    public void serialise() throws IOException {
        Serialiser ser = new Serialiser();
        int val = Serialiser.getCount_saved_games();
        System.out.println(val);
        Player p1_to_be_saved = p1.clone();
        Player p2_to_be_saved = p2.clone();
        Tank tank_of_p1 = p1.getTank_chosen().clone();
        Tank tank_of_p2 = p2.getTank_chosen().clone();
        Ground ground_to_be_saved = Ground.getInstance();
        System.out.println(Serialiser.getP1_save().get(val));
        FileOutputStream fileout_p1 = new FileOutputStream(Serialiser.getP1_save().get(val));
        FileOutputStream fileout_p2 = new FileOutputStream(Serialiser.getP2_save().get(val));
        FileOutputStream fileout_t1 = new FileOutputStream(Serialiser.getT1_save().get(val));
        FileOutputStream fileout_t2 = new FileOutputStream(Serialiser.getT2_save().get(val));
        FileOutputStream fileout_ground = new FileOutputStream(Serialiser.getGround_save().get(val));
        ObjectOutputStream out_p1 = new ObjectOutputStream(fileout_p1);
        ObjectOutputStream out_p2 = new ObjectOutputStream(fileout_p2);
        ObjectOutputStream out_t1 = new ObjectOutputStream(fileout_t1);
        ObjectOutputStream out_t2 = new ObjectOutputStream(fileout_t2);
        ObjectOutputStream out_ground = new ObjectOutputStream(fileout_ground);
        out_p1.writeObject(p1_to_be_saved);
        out_p2.writeObject(p2_to_be_saved);
        out_t1.writeObject(tank_of_p1);
        out_t2.writeObject(tank_of_p2);
        out_ground.writeObject(ground_to_be_saved);
        out_p1.close();
        out_p2.close();
        out_t1.close();
        out_t2.close();
        out_ground.close();
        fileout_p1.close();
        fileout_p2.close();
        fileout_t1.close();
        fileout_t2.close();
        fileout_ground.close();
    }
}

