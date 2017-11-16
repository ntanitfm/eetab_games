package com.mygdx.game.help;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.item.Config;
import com.mygdx.game.main.Shisen;
import com.mygdx.game.title.TitleScreen;

/**
 * Created by ntani on 2017/11/16.
 */

public class HelpScreen extends ScreenAdapter {
    private String TAG = HelpScreen.class.getSimpleName();
    Stage stage;
    Image tutorial;
    TextButton goTitle;

    Shisen game;
    public HelpScreen(final Shisen game) {
        Gdx.app.log(TAG, "Constructor");
        this.game = game;
        // ヘルプ画面ｓ
        tutorial = new Image(new Texture("image/tutorial.PNG"));
        tutorial.setFillParent(true);
        tutorial.setPosition(0,0);
        // タイトルへ戻るボタン
        float width = Config.TXTBTN_WIDTH_M;
        float height = Config.TXTBTN_HEIGHT;
        goTitle = new TextButton(Config.TITL, Config.skin);
        goTitle.setSize(width, height);
        goTitle.setPosition(0f, 0f);
        goTitle.getLabel().setFontScale(Config.TXT_SIZE_S);
        goTitle.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TitleScreen(game));
                return true;
            }
        });
}

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");
        stage = new Stage(Config.viewport);
        stage.addActor(tutorial);
        stage.addActor(goTitle);
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
