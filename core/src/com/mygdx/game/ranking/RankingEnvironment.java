package com.mygdx.game.ranking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.item.Config;
import com.mygdx.game.item.ResultData;
import com.mygdx.game.main.Shisen;
import com.mygdx.game.title.TitleScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntani on 2017/11/08.
 */

class RankingEnvironment {
    private String TAG = RankingEnvironment.class.getSimpleName();
    Shisen game;
    List<ResultData> resultList;
    List<ResultData> showList;
    String SCREEN_MODE;
    String viewMode;
    String crntVMode;

    RankingEnvironment(Shisen game) {
        Gdx.app.log(TAG, "constractor");
        this.game = game;
        SCREEN_MODE = Config.NO_SLCT;
        crntVMode = viewMode = Config.PLAY_LV1;
    }

    Table getTable() {
        Gdx.app.log(TAG, "getTable called");
        resultList = game.dbo.read();
        showList = getByKey(viewMode);
        Table table = new Table();
        table.padLeft(100f);
        table.padRight(100f);
        table.setFillParent(true);
        Label rankingMode = getModeLabel(viewMode);
        table.add(rankingMode).colspan(3).fillX();
        int number = 1;
        for(ResultData rd : showList) {
            Gdx.app.log(TAG, rd.toString());
            table.row();
            Label num  = new Label("" + (number++),Config.skin);
            Label name = new Label(rd.name, Config.skin);
            Label time = new Label(rd.generateSec(), Config.skin);
            num.setColor(0,0,0,1);
            name.setColor(0,0,0,1);
            time.setColor(0,0,0,1);
            num.setFontScale(3);
            name.setFontScale(3);
            time.setFontScale(3);
            table.add(num).expand();
            table.add(name).expand();
            table.add(time).expand();
            if(number > 10) break;
        }
        return table;
    }

    // キーによるリストの絞り込み
    private List<ResultData> getByKey(String key) {
        List<ResultData> retList = new ArrayList<ResultData>();
        for(ResultData rd : resultList) {
            if(key.equals(rd.mode)) retList.add(rd);
        }
        return retList;
    }

    // ランキングモードが変更されたか
    boolean isRankingChangeed() {
        // 変更なし
        if(viewMode.equals(crntVMode)) {
            return false;
        }
        // 変更あり
        else {
            Gdx.app.log(TAG, "change ranking mode to " + viewMode);
            crntVMode = viewMode;
            return true;
        }
    }

    // ランキングモード表示用ラベル
    Label getModeLabel(String mode){
        float width = 500f;
        float height = 100f;
        Label label = new Label(mode + " mode Ranking", Config.skin);
        label.setFontScale(2);
        label.setSize(width, height);
        label.setPosition(Config.SCRN_WIDTH_CTR - width / 2, Config.SCRN_HEIGHT - height);
        label.setColor(0,0,0,1);
        label.setAlignment(Align.center);
        return label;
    }


    // タイトルへ戻るボタン
    TextButton getTitleButton() {
        float width = Config.TXTBTN_WIDTH_S;
        float height = Config.TXTBTN_HEIGHT;
        TextButton txtBtn = new TextButton(Config.TITL, Config.skin);
        txtBtn.setSize(width, height);
        txtBtn.setPosition(0f, 0f);
        setBtnListener(txtBtn);
        return txtBtn;
    }

    // easyランキングモード
    TextButton getLv1RankingButton() {
        float width = Config.TXTBTN_WIDTH_S;
        float height = Config.TXTBTN_HEIGHT;
        TextButton txtBtn = new TextButton(Config.PLAY_LV1, Config.skin);
        txtBtn.setSize(width, height);
        txtBtn.setPosition(Config.SCRN_WIDTH - width, height + 10f);
        setChgModeListener(txtBtn);
        return txtBtn;
    }

    // hardランキングモード
    TextButton getLv2RankingButton() {
        float width = Config.TXTBTN_WIDTH_S;
        float height = Config.TXTBTN_HEIGHT;
        TextButton txtBtn = new TextButton(Config.PLAY_LV2, Config.skin);
        txtBtn.setSize(width, height);
        txtBtn.setPosition(Config.SCRN_WIDTH - width, 0f);
        setChgModeListener(txtBtn);
        return txtBtn;
    }

    // ボタン名をそのままSCREEN_MODEへ渡すリスナー
    private void setBtnListener(final TextButton txtBtn) {
        txtBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                String mode = txtBtn.getText().toString();
                if(mode.equals(Config.TITL)) {
                    game.setScreen(new TitleScreen(game));
                }
                return true;
            }
        });
    }

    // ランキング表示モードを変更するリスナー
    private void setChgModeListener(final TextButton txtBtn) {
        txtBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                viewMode = txtBtn.getText().toString();
                return true;
            }
        });
    }

}
