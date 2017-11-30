package com.mygdx.game.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.item.Config;
import com.mygdx.game.main.Shisen;
import com.mygdx.game.title.TitleScreen;

/**
 * ゲーム画面。
 * easy,normalモードを兼用する。
 * Created by ntani on 2017/10/26.
 */

public class PlayScreen extends ScreenAdapter {
    String TAG = PlayScreen.class.getSimpleName();
    long start;
    float time;
    Stage stage;
    Table playTable;
    TextButton titleButton;
    TextButton resultButton;
    PlayEnvironment env;
    Texture background;
    BitmapFont font;

    public PlayScreen(Shisen game, String mode) {
        Gdx.app.log(TAG, "Constructor in Play");
        // 環境設定読み込み(モード選択)
        env = new PlayEnvironment(game, mode);
        // テキストボタン
        titleButton = env.getTitleButton();
        resultButton = env.getResultButton();
        // 牌テーブル読み込み
        playTable = env.table;
        // 背景
        background = Config.getBackground("image/playback.png");
        // 時間
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        font.setColor(0,0,0,1);
        font.getData().setScale(3);
        start = System.currentTimeMillis();
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");
        //  ステージ生成
        stage = new Stage(Config.viewport);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(titleButton);
        stage.addActor(resultButton);
        stage.addActor(playTable);
    }

    @Override
    public void render(float delta) {
        time = 100f - (System.currentTimeMillis() - start) / 1000f;
        if(time < 0) {
            env.game.setScreen(new TitleScreen(env.game));
        }
        Config.drawRoutine();
        // 描画処理
        Config.batcher.disableBlending();
        Config.batcher.begin();
        Config.batcher.draw(background, 0, 0, 1280, 800);
        font.draw(Config.batcher, String.format("time %1$.3f",time), 100f,Config.SCRN_HEIGHT - 10f);
        Config.batcher.end();
        Config.batcher.enableBlending();
        Config.batcher.begin();
        this.stage.act();
        this.stage.draw();
        Config.batcher.end();
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide");
    }
    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose");
    }
}

