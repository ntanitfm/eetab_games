package com.mygdx.game.ranking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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

    public RankingScreen(Shisen game) {
        Gdx.app.log(TAG, "constractor in Ranking");
        this.game = game;
        this.env = new RankingEnvironment(game);
        goTitle = env.getTitleButton();
        rankTable = env.getTable();
        chgLv1 = env.getLv1RankingButton();
        chgLv2 = env.getLv2RankingButton();
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

    // @TODO このupdateを無くしたい.
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
        // 描画処理
        Config.batcher.begin();
        this.stage.act();
        this.stage.draw();
        Config.batcher.end();
    }
}
