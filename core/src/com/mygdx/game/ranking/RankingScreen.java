package com.mygdx.game.ranking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.item.Config;
import com.mygdx.game.main.Shisen;

/**
 * Created by ntani on 2017/11/08.
 */

public class RankingScreen extends ScreenAdapter {
    private String TAG = RankingScreen.class.getSimpleName();
    Shisen game;
    Stage stage;
    TextButton goTitle;
    TextButton chgLv1;
    TextButton chgLv2;
    Table rankTable;
    RankingEnvironment env;

    TextureRegion background;

    public RankingScreen(Shisen game, String mode) {
        Gdx.app.log(TAG, "constractor in Ranking");
        this.game = game;
        this.env = new RankingEnvironment(game, mode);
        goTitle = env.getTitleButton();
        rankTable = env.getTable();
        chgLv1 = env.getLv1RankingButton();
        chgLv2 = env.getLv2RankingButton();
        background = new TextureRegion(Config.getBackground("image/rankingback.png"));
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");
        // ステージ生成
        stage = new Stage(Config.viewport);
        stage.addActor(goTitle);
        stage.addActor(chgLv1);
        stage.addActor(chgLv2);
        stage.addActor(rankTable);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    void update() {
        // 画面に変更があれば、画面を更新
        if(env.isRankingChangeed()) {
            rankTable.remove();
            rankTable =  env.getTable();
            stage.addActor(rankTable);
        }
    }
    void draw() {
//        Gdx.app.log(TAG, "draw");
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

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
    }
}
