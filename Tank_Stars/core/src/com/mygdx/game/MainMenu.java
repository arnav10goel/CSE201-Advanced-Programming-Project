package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MainMenu implements Screen {
    private SpriteBatch batch;
    private Texture img;
    private Sprite img_sprite;
    private Texture start;
    private Sprite start_sprite;
    private Texture resume;
    private Sprite resume_sprite;
    private Texture exit;
    private Sprite exit_sprite;
    static MyGdxGame game;
    private Vector3 coord;
    private OrthographicCamera cam;

    public MainMenu(MyGdxGame game){
        this.game = game;
        batch = new SpriteBatch();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        img = new Texture("Main_Menu_Pg.png");
        img_sprite = new Sprite(img);
        img_sprite.setSize(w,h);
        start = new Texture("start_button.png");
        start_sprite = new Sprite(start);
        start_sprite.setSize(start.getWidth()/5, start.getHeight()/5);
        start_sprite.setPosition(1796*w/2550, 810*h/1180);
        resume = new Texture("resume_button.png");
        resume_sprite = new Sprite(resume);
        resume_sprite.setSize(resume.getWidth()/5, resume.getHeight()/5);
        resume_sprite.setPosition(1796*w/2550, 504*h/1180);
        exit = new Texture("exit_button.png");
        exit_sprite = new Sprite(exit);
        exit_sprite.setSize(exit.getWidth()/5, exit.getHeight()/5);
        exit_sprite.setPosition(1796*w/2550, 197*h/1180);
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
        resume_sprite.draw(batch);
        start_sprite.draw(batch);
        exit_sprite.draw(batch);
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
        exit.dispose();
        resume.dispose();
        start.dispose();

    }
    void handleTouch(){
        if(Gdx.input.justTouched()){
            coord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(coord);

            float touch_x = coord.x;
            float touch_y = coord.y;

            if((touch_x>= start_sprite.getX()) && touch_x<=(start_sprite.getX()+start_sprite.getWidth()) && (touch_y>=start_sprite.getY()) && touch_y<=(start_sprite.getY()+start_sprite.getHeight())){
                game.setScreen(new ChooseMode(game));
            }
            else if((touch_x>= resume_sprite.getX()) && touch_x <= (resume_sprite.getX()+resume_sprite.getWidth()) && (touch_y>=resume_sprite.getY()) && touch_y<=(resume_sprite.getY()+resume_sprite.getHeight())){
                game.setScreen(new Resume(game));
            }
            else if((touch_x>= exit_sprite.getX()) && touch_x <= (exit_sprite.getX()+exit_sprite.getWidth()) && (touch_y>=exit_sprite.getY()) && touch_y<=(exit_sprite.getY()+exit_sprite.getHeight())){
                Gdx.app.exit();
            }
        }
    }
}
