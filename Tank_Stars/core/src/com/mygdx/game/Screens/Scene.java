package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.mygdx.game.Screens.PauseMenu;
import org.jetbrains.annotations.NotNull;

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
    private final Sprite pause_sprite;
    private final Sprite health1_sprite;
    private final Sprite health2_sprite;
    static MyGdxGame game;
    private final Vector3 coord;
    private final OrthographicCamera cam;

    private final Ground myground;
    private final ShapeRenderer sr;
    private final ShapeRenderer fuelrenderer;

    private int angle;
    private int angle2;
    private Bezier<Vector2> path1;
    public Scene(MyGdxGame game, @NotNull Player p1, Player p2) {
        this.sr = new ShapeRenderer();
        this.fuelrenderer = new ShapeRenderer();
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

        angle = 4;
        angle2 = 4;

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
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        fuelrenderer.setAutoShapeType(true);
        fuelrenderer.begin(ShapeRenderer.ShapeType.Filled);
        fuelrenderer.setColor(0,0,0,1);
        fuelrenderer.rect(0.08f*w,0.05f*h,(float)0.15*w,(float)0.05*h);
        fuelrenderer.setColor(0,0,0,1);
        fuelrenderer.rect(0.76f*w,0.05f*h,(float)0.15*w,(float)0.05*h);
        fuelrenderer.end();
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
        fuelrenderer.dispose();
        batch.dispose();
        img.dispose();
        pause.dispose();
        player1.dispose();
        player2.dispose();
    }
    public void handleTouch(){
        float range;
        double velocity;
        float range2;
        double velocity2;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        ShapeRenderer fsr = new ShapeRenderer();
        fsr.setAutoShapeType(true);
        fsr.begin(ShapeRenderer.ShapeType.Filled);
        fsr.setColor(Color.YELLOW);
        fsr.rect(0.08f*w,0.05f*h,(float)0.15*w,(float)0.05*h);
        fsr.setColor(Color.YELLOW);
        fsr.rect(0.76f*w,0.05f*h,(float)0.15*w,(float)0.05*h);
        fsr.end();

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
            try {
                if (Gdx.input.isKeyPressed(Input.Keys.D) && p1.getTank_chosen().getFuel() > 1) {
                    float slope = myground.getPoints_y().get((int) (2 * p1.getTank_chosen().getX())) - myground.getPoints_y().get((int) (2 * p1.getTank_chosen().getX() - 1));
                    float derivative = (float) Math.atan(slope);
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
                if (Gdx.input.isKeyPressed(Input.Keys.A) && p1.getTank_chosen().getX() > 1 && p1.getTank_chosen().getFuel() > 1) {
                    float slope = myground.getPoints_y().get((int) (2 * p1.getTank_chosen().getX())) - myground.getPoints_y().get((int) (2 * p1.getTank_chosen().getX() - 1));
                    float derivative = (float) Math.atan(slope);
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
                if (Gdx.input.isKeyJustPressed(Input.Keys.W) && angle < 22) {
                    angle += 1;
                    System.out.println("Upper key " + angle2);
                }
                else if (Gdx.input.isKeyJustPressed(Input.Keys.S) && angle > 4){
                    angle -= 1;
                    System.out.println("Lower key " + angle2);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                    float pos = p1.getTank_chosen().getX() + player1.getWidth() / 16;
                    ArrayList<Float> pts = myground.getPoints_y();
                    range = w - pos;
                    velocity = Math.pow(range * 10, 0.5);
                    double fin = velocity * velocity * Math.sin(2 * Math.toRadians(angle)) / 10;
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

                turn = 2;
            }
            catch (MoveException e){
                p1.getTank_chosen().setX(p1.getTank_chosen().getX()-0.5f);
            }
        }
        else if(this.turn == 2){
            try {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && p2.getTank_chosen().getX() > 1 && p2.getTank_chosen().getFuel() > 1) {
                    float slope = myground.getPoints_y().get((int) (2 * p2.getTank_chosen().getX())) - myground.getPoints_y().get((int) (2 * p2.getTank_chosen().getX() - 1));
                    float derivative = (float) Math.atan(slope);
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
                if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && p2.getTank_chosen().getFuel() > 1){
                    float slope = myground.getPoints_y().get((int) (2*p2.getTank_chosen().getX())) - myground.getPoints_y().get((int) (2*p2.getTank_chosen().getX()-1));
                    float derivative = (float) Math.atan(slope);
                    player2_sprite.setRotation((float) Math.toDegrees(derivative));
                    player2_sprite.setOrigin(0,0);
                    p2.getTank_chosen().setX(p2.getTank_chosen().getX()+0.5f);
                    p2.getTank_chosen().setY(myground.getPoints_y().get((int) (2*p2.getTank_chosen().getX())));
                    if(p1.getTank_chosen().getX()>=myground.getPoints_x().size()-1){
                        throw new MoveException("Tank out of screen");
                    }
                    player2_sprite.setPosition((float) (p2.getTank_chosen().getX()-0.01*player2_sprite.getWidth()), p2.getTank_chosen().getY());
                    p2.getTank_chosen().setFuel(p2.getTank_chosen().getFuel() - 1);
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && angle2 < 22) {
                    angle2 += 1;
                    System.out.println("Upper key " + angle2);
                }
                else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && angle2 > 4){
                    angle2 -= 1;
                    System.out.println("Lower key " + angle2);
                }
                if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                    float pos2=p2.getTank_chosen().getX()+player2.getWidth()/16;
                    ArrayList<Float> pts = myground.getPoints_y();
                    range2 = w - pos2;
                    velocity2 = Math.pow(range2 * 10, 0.5);
                    double fin2 = velocity2 * velocity2 * Math.sin(2 * Math.toRadians(angle2)) / 10;
                    int points2 = 4;
                    Vector2[] controlPoints2 = new Vector2[points2];
                    float a = (pos2);
                    float b = (pts.get((int) ( 2*pos2)));
                    Vector2 point01 = new Vector2(a, b);
                    controlPoints2[0] = point01;
                    float a1 = (float) (pos2 - (fin2) / 3);
                    float b1 = pts.get((int) (2*(pos2 - (fin2) / 3))) + h / 5;
                    Vector2 point02 = new Vector2(a1, b1);
                    controlPoints2[1] = point02;
                    float a2 = (float) (pos2 - 2 * (fin2) / 3);
                    float b2 = pts.get((int) ( 2*(pos2 - 2 * (fin2) / 3))) + h / 5;
                    Vector2 point03 = new Vector2(a2, b2);
                    controlPoints2[2] = point03;
                    float a3 = (float) (pos2 - fin2);
                    float b3 = (pts.get((int) ( 2*(pos2 - fin2))));
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

                turn = 1;
            }
            catch (MoveException e){
                p2.getTank_chosen().setX(p2.getTank_chosen().getX()-0.5f);
            }
        }
    }
}

