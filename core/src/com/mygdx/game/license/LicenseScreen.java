package com.mygdx.game.license;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.item.Config;
import com.mygdx.game.main.Shisen;

/**
 * Created by ntani on 2017/11/10.
 */

public class LicenseScreen extends ScreenAdapter {
    String TAG = LicenseScreen.class.getSimpleName();
    LicenseEnvironment env;
    Shisen game;
    Stage stage;
    TextButton titleButton;
    Table table;

    public LicenseScreen(Shisen game) {
        this.game = game;
        this.env = new LicenseEnvironment(game);
        this.titleButton = env.getTitleButton();
        this.table = env.getTable();
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");
        // ステージ生成
        stage = new Stage(Config.viewport);
        Gdx.input.setInputProcessor(stage);
        // ウィジェット追加
        stage.addActor(titleButton);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Config.drawRoutine();
        Config.batcher.begin();
        stage.act();
        stage.draw();
        Config.batcher.end();
    }
}
