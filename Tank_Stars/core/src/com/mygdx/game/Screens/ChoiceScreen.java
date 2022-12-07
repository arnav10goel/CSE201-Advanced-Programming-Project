package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Player;
import com.mygdx.game.Tanks.Buratino;
import com.mygdx.game.Tanks.Frost;
import com.mygdx.game.Tanks.Spectre;
import com.mygdx.game.Tanks.Tank;

import java.util.ArrayList;

public class ChoiceScreen implements Screen {

    private SpriteBatch batch;
    private Texture img;
    private Sprite img_sprite;
    private Texture choose;
    private Sprite choose_sprite;
    private Texture left_arrow;
    private Sprite left_arrow_sprite;

    private Texture right_arrow;
    private Sprite right_arrow_sprite;

    static MyGdxGame game;

    static ArrayList<Player> players = new ArrayList<>();
    private Vector3 coord;
    private OrthographicCamera cam;

    private String status;

    public ChoiceScreen(MyGdxGame game, String texture, String status){
        this.status = status;
        ChoiceScreen.game = game;
        batch = new SpriteBatch();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        img = new Texture(Gdx.files.internal(texture));
        img_sprite = new Sprite(img);
        img_sprite.setSize(w,h);
        choose = new Texture("choose.png");
        choose_sprite = new Sprite(choose);
        choose_sprite.setSize(2*choose.getWidth()/9, 2*choose.getHeight()/9);
        choose_sprite.setPosition(w*1780/2550, h*120/1180);

        right_arrow = new Texture(Gdx.files.internal("right_arrow.png"));
        right_arrow_sprite = new Sprite(right_arrow);

        right_arrow_sprite.setSize(2*right_arrow_sprite.getWidth()/9, 2*right_arrow_sprite.getHeight()/9);
        left_arrow = new Texture(Gdx.files.internal("left_arrow.png"));
        left_arrow_sprite = new Sprite(left_arrow);
        left_arrow_sprite.setSize(2*left_arrow_sprite.getWidth()/9, 2*left_arrow_sprite.getHeight()/9);
        left_arrow_sprite.setPosition(w*1626/2550, h*560/1180);
        right_arrow_sprite.setPosition(w*2460/2550, h*560/1180);

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
        if(this.status.equals("Buratino_P1") || this.status.equals("Buratino_P2") || this.status.equals("Frost_P1") || this.status.equals("Frost_P2")){
            right_arrow_sprite.draw(batch);
        }
        if(this.status.equals("Frost_P1") || this.status.equals("Frost_P2") || this.status.equals("Spectre_P1") || this.status.equals("Spectre_P2")){
            left_arrow_sprite.draw(batch);
        }
        choose_sprite.draw(batch);
        batch.end();
        this.handleTouch();
    }

    public String getStatus() {
        return status;
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

    void handleTouch(){
        if(Gdx.input.justTouched()){
            coord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(coord);

            float touch_x = coord.x;
            float touch_y = coord.y;

            if(touch_x >= right_arrow_sprite.getX() && touch_x <= (right_arrow_sprite.getX() + right_arrow_sprite.getWidth()) && touch_y >= right_arrow_sprite.getY() && touch_y <= (right_arrow_sprite.getY() + right_arrow_sprite.getHeight())){
                switch (this.getStatus()) {
                    case "Buratino_P1":
                        game.setScreen(new ChoiceScreen(game, "Frost_P1.png", "Frost_P1"));
                        break;
                    case "Buratino_P2":
                        game.setScreen(new ChoiceScreen(game, "Frost_P2.png", "Frost_P2"));
                        break;
                    case "Frost_P1":
                        game.setScreen(new ChoiceScreen(game, "Spectre_P1.png", "Spectre_P1"));
                        break;
                    case "Frost_P2":
                        game.setScreen(new ChoiceScreen(game, "Spectre_P2.png", "Spectre_P2"));
                        break;
                }
            }
            else if(touch_x >= left_arrow_sprite.getX() && touch_x <= (left_arrow_sprite.getX() + left_arrow_sprite.getWidth()) && touch_y >= left_arrow_sprite.getY() && touch_y <= (left_arrow_sprite.getY() + left_arrow_sprite.getHeight())){
                switch (this.getStatus()) {
                    case "Frost_P1":
                        game.setScreen(new ChoiceScreen(game, "Buratino_P1.png", "Buratino_P1"));
                        break;
                    case "Frost_P2":
                        game.setScreen(new ChoiceScreen(game, "Buratino_P2.png", "Buratino_P2"));
                        break;
                    case "Spectre_P1":
                        game.setScreen(new ChoiceScreen(game, "Frost_P1.png", "Frost_P1"));
                        break;
                    case "Spectre_P2":
                        game.setScreen(new ChoiceScreen(game, "Frost_P2.png", "Frost_P2"));
                        break;
                }
            }
            else if (touch_x >= choose_sprite.getX() && touch_x <= (choose_sprite.getX() + choose_sprite.getWidth()) && touch_y >= choose_sprite.getY() && touch_y <= (choose_sprite.getY() + choose_sprite.getHeight())){
                if(this.getStatus().equals("Buratino_P1") || this.getStatus().equals("Frost_P1") || this.getStatus().equals("Spectre_P1")){
                    switch (this.status) {
                        case "Buratino_P1":
                            Tank tank_new = new Buratino();
                            Player current_player = new Player("Player1", tank_new);
                            ChoiceScreen.players.add(current_player);
                            break;
                        case "Frost_P1":
                            Tank tank_new2 = new Frost();
                            Player current_player2 = new Player("Player1", tank_new2);
                            ChoiceScreen.players.add(current_player2);
                            break;
                        case "Spectre_P1":
                            Tank tank_new3 = new Spectre();
                            Player current_player3 = new Player("Player1", tank_new3);
                            ChoiceScreen.players.add(current_player3);
                            break;
                    }
                    game.setScreen(new ChoiceScreen(game, "Buratino_P2.png", "Buratino_P2"));
                }
                else{
                    switch (this.getStatus()) {
                        case "Buratino_P2":
                            Tank tank_new = new Buratino();
                            Player current_player = new Player("Player2", tank_new);
                            ChoiceScreen.players.add(current_player);
                            break;
                        case "Frost_P2":
                            Tank tank_new2 = new Frost();
                            Player current_player2 = new Player("Player2", tank_new2);
                            ChoiceScreen.players.add(current_player2);
                            break;
                        case "Spectre_P2":
                            Tank tank_new3 = new Spectre();
                            Player current_player3 = new Player("Player2", tank_new3);
                            ChoiceScreen.players.add(current_player3);
                            break;
                    }
                    game.setScreen(new Scene(game));
                }
            }
        }
    }
}
