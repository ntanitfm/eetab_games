package com.mygdx.game.help;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.item.Config;
import com.mygdx.game.main.Shisen;

/**
 * Created by ntani on 2017/11/16.
 */

public class HelpScreen extends ScreenAdapter {
    private String TAG = HelpScreen.class.getSimpleName();
    Stage stage;

    Shisen game;
    public HelpScreen(Shisen game) {
        Gdx.app.log(TAG, "Constructor");
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");
        stage = new Stage(Config.viewport);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Config.drawRoutine();
        // 描画処理
        Config.batcher.begin();
        this.stage.act();
        this.stage.draw();
        Config.batcher.end();
    }

    @Override
    public void dispose() {
        Config.batcher.dispose();
        this.stage.dispose();
        Config.skin.dispose();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }
}
