package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	Sprite img_sprite;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		img = new Texture("tankstars_loadpage.png");
		img_sprite = new Sprite(img);

		img_sprite.setSize(w,h);
	}

	@Override
	public void render () {

		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		img_sprite.draw(batch);
		//batch.draw(img, 10, 10);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
