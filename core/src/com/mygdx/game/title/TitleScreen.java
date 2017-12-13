package com.mygdx.game.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.item.Config;
import com.mygdx.game.main.Shisen;

/**
 * タイトル画面
 * 部品の取得や登録、描画、遷移のみを行う。
 * 部品の生成は裏方のTitleEnvironmentに任せている。
 * Created by ntani on 2017/10/23.
 */

public class TitleScreen extends ScreenAdapter {
    String TAG = TitleScreen.class.getSimpleName();
    TitleEnvironment env;
    Stage stage;
    Image shisenImg;
    Image titlePai;
    TextButton easyButton;
    TextButton normalButton;
    TextButton rankingButton;
    ImageButton info;
    ImageButton help;
    Texture background;

    // 部品生成
    public TitleScreen(Shisen game) {
        Gdx.app.log(TAG, "Constructor in Title");
        env = new TitleEnvironment(game);
        // タイトル
        shisenImg = env.getTitleImg();
        // 中央回転牌
        titlePai = env.getTitlePai();
        // テキストボタン
        easyButton = env.getTitleTextButton(Config.PLAY_LV1, 200);
        normalButton = env.getTitleTextButton(Config.PLAY_LV2, 270);
        rankingButton = env.getTitleTextButton(Config.RANK, 340);
        // ライセンス表示ボタン
        info = env.getInfoButton();
        // 遊び方表示ボタン
        help = env.getHelpButton();

        background = Config.getBackground("image/back.png");

    }

    // 部品登録
    @Override
    public void show() {
        Gdx.app.log(TAG, "show");
        // ステージ生成
        stage = new Stage(Config.viewport);
        Gdx.input.setInputProcessor(stage);
        // ウィジェット追加
        stage.addActor(shisenImg);
        stage.addActor(titlePai);
        stage.addActor(easyButton);
        stage.addActor(normalButton);
        stage.addActor(rankingButton);
        stage.addActor(info);
        stage.addActor(help);
    }

    @Override
    public void render(float delta) {
        Config.drawRoutine();
        // ここに描画処理
        Config.batcher.disableBlending();
        Config.batcher.begin();
        Config.batcher.draw(background, 0, 0, 1280, 800);
        Config.batcher.end();
        Config.batcher.enableBlending();
        Config.batcher.begin();
        stage.act();
        stage.draw();
        Config.batcher.end();
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose");
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
    }
}
