package com.mygdx.game.main;

import com.badlogic.gdx.Game;
import com.mygdx.game.item.Config;
import com.mygdx.game.title.TitleScreen;

public class Shisen extends Game {
	public DatabaseOperator dbo;

	public Shisen(DatabaseOperator dbo) {
		this.dbo = dbo;
	}

	@Override
	public void create () {
		Config.load();
		setScreen(new TitleScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		Config.dispose();
	}
}
