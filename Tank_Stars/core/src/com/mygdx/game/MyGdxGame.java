package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Screens.LoadScreen;

import java.io.Serializable;

public class MyGdxGame extends Game implements Serializable {

	@Override
	public void create () {
		setScreen(new LoadScreen(this));
	}

}
