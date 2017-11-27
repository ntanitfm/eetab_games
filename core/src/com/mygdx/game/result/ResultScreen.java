package com.mygdx.game.result;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.main.Shisen;
import com.mygdx.game.item.Config;

/**
 * Created by ntani on 2017/11/01.
 */

public class ResultScreen extends ScreenAdapter {
    private String TAG = ResultScreen.class.getSimpleName();
    Stage stage;
    Label finishedTime;
    Label resultTitle;
    TextButton goTitle;
    TextButton goRanking;
    ResultEnvironment env;

    TextureRegion background;

    public ResultScreen(Shisen game, String gameMode, long elapsedTime) {
        Gdx.app.log(TAG, "constructor in Result");
        env = new ResultEnvironment(game, gameMode, elapsedTime);
        finishedTime = env.getTimeLabel();
        resultTitle = env.getModeLabel();
        goTitle = env.getTitleButton();
        goRanking = env.getRankingButton();
        background = new TextureRegion(new Texture(Gdx.files.internal("image/back2.png")));
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");
        //  ステージ生成
        stage = new Stage(Config.viewport);
        stage.addActor(finishedTime);
        stage.addActor(resultTitle);
        stage.addActor(goTitle);
        stage.addActor(goRanking);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Config.drawRoutine();
        Config.batcher.disableBlending();
        Config.batcher.begin();
        Config.batcher.draw(background, 0, 0, 1280, 800);
        Config.batcher.end();
        Config.batcher.enableBlending();
        // 描画処理
        Config.batcher.begin();
        this.stage.act();
        this.stage.draw();
        Config.batcher.end();
    }
}
