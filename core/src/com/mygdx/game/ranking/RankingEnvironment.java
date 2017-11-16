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

    RankingEnvironment(Shisen game, String mode) {
        Gdx.app.log(TAG, "constractor");
        this.game = game;
        SCREEN_MODE = Config.NO_SLCT;
        crntVMode = viewMode = mode;
    }

    Table getTable() {
        Gdx.app.log(TAG, "getTable called");
        resultList = game.dbo.read();
        showList = getByKey(viewMode);
        Table table = new Table();
        table.padLeft(200f).padRight(200f);
        table.padTop(50f).padBottom(50f);
        table.setFillParent(true);
        table.debugAll();
        Label rankingMode = getModeLabel(viewMode);
        table.add(rankingMode).colspan(4).fillX();
        table.row().padTop(20).padBottom(20);
        // 案内用ラベル宣言
        List<Label> labels = new ArrayList<Label>();
        labels.add(new Label("順位", Config.skin));
        labels.add(new Label("名前", Config.skin));
        labels.add(new Label("タイム", Config.skin));
        labels.add(new Label("日付", Config.skin));
        tableSetter(labels, table, 0.7f);
        int number = 1;
        for(ResultData rd : showList) {
            Gdx.app.log(TAG, rd.toString());
            table.row();
            labels.add(new Label("" + (number++), Config.skin));
            labels.add(new Label(rd.name, Config.skin));
            labels.add(new Label(rd.generateSec(), Config.skin));
            labels.add(new Label(rd.date, Config.skin));
            tableSetter(labels, table, 0.5f);
            if(number > 10) break;
        }
        return table;
    }

    // テーブルにラベルを追加
    private void tableSetter(List<Label> labelList, Table table, float fontScale) {
        for(Label label : labelList) {
            label.setColor(0,0,0,1);
            label.setFontScale(fontScale);
            table.add(label).expand();
        }
        labelList.clear();
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
        Label label = new Label(mode + "モードランキング", Config.skin);
        label.setFontScale(1f);
        label.setSize(width, height);
        label.setPosition(Config.SCRN_WIDTH_CTR - width / 2, Config.SCRN_HEIGHT - height);
        label.setColor(0,0,0,1);
        label.setAlignment(Align.center);
        return label;
    }


    // タイトルへ戻るボタン
    TextButton getTitleButton() {
        float width = Config.TXTBTN_WIDTH_M;
        float height = Config.TXTBTN_HEIGHT;
        TextButton txtBtn = new TextButton(Config.TITL, Config.skin);
        txtBtn.setSize(width, height);
        txtBtn.setPosition(0f, 0f);
        txtBtn.getLabel().setFontScale(Config.TXT_SIZE_S);
        setBtnListener(txtBtn);
        return txtBtn;
    }

    // easyランキングモード
    TextButton getLv1RankingButton() {
        float width = Config.TXTBTN_WIDTH_M;
        float height = Config.TXTBTN_HEIGHT;
        TextButton txtBtn = new TextButton(Config.PLAY_LV1, Config.skin);
        txtBtn.setSize(width, height);
        txtBtn.setPosition(Config.SCRN_WIDTH - width, height + 10f);
        txtBtn.getLabel().setFontScale(Config.TXT_SIZE_S);
        setChgModeListener(txtBtn);
        return txtBtn;
    }

    // hardランキングモード
    TextButton getLv2RankingButton() {
        float width = Config.TXTBTN_WIDTH_M;
        float height = Config.TXTBTN_HEIGHT;
        TextButton txtBtn = new TextButton(Config.PLAY_LV2, Config.skin);
        txtBtn.setSize(width, height);
        txtBtn.setPosition(Config.SCRN_WIDTH - width, 0f);
        txtBtn.getLabel().setFontScale(Config.TXT_SIZE_S);
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
