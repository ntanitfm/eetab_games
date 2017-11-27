package com.mygdx.game.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.item.Config;
import com.mygdx.game.item.Pai;
import com.mygdx.game.main.Shisen;
import com.mygdx.game.result.ResultScreen;
import com.mygdx.game.title.TitleScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * ゲームスクリーンの環境設定を行うクラス。
 * 引数(type)に応じてEasy, Normalモードの画面環境を構築し
 * そのデータをGameScreenへ渡す。
 * Created by ntani on 2017/10/30.
 */

class PlayEnvironment {
    private String TAG = PlayEnvironment.class.getSimpleName();
    Shisen game;
    Table table;                        // 牌を並べるテーブル
    Pai slctedPai;                      // 選択された牌
    PlayJudgement jdg;                  // 条件判定クラス
    PlayConf plycnf;                      // モードごとの牌の設定
    List<Pai> paiList;                  // 牌の配置
    ButtonGroup<ImageButton> paiGroup;  // 牌の制御用
    String mode;                        // モード記録用
    long startTime;                     // 時間記録用

    Action fade;

    // 環境設定
    PlayEnvironment(Shisen game, String mode) {
        Gdx.app.log(TAG, mode + "Mode");
        this.game = game;
        this.mode = mode;
        // 難易度ごとに牌を配置
        plycnf = new PlayConf(mode);
        // 開始時刻記録
        startTime = System.currentTimeMillis();
        // アクション
        fade = Actions.parallel(
                Actions.fadeOut(0.3f),
                Actions.scaleTo(2,2,0.3f)
        );
        // 条件判定用クラス
        jdg = new PlayJudgement(plycnf.ROWS, plycnf.COLS);
        // テーブル設置
        table = new Table(Config.skin);
        table.setFillParent(true);
//        table.debugAll();
        // 牌一覧・状態を格納するリスト
        paiList = new ArrayList<Pai>();
        // 牌制御用
        paiGroup = new ButtonGroup<ImageButton>();
        paiGroup.setMinCheckCount(0);
        paiGroup.setMaxCheckCount(1);
        // 牌配置
        for (int i = 0; i < plycnf.ROWS; i++) {
            for (int j = 0; j < plycnf.COLS; j++) {
                Pai tmpPai = new Pai(plycnf.paiTypeList.remove(0), i, j);
                paiGroup.add(tmpPai.imgButton);
                paiList.add(tmpPai);
                setPaiListener(tmpPai);
                table.add(tmpPai.imgButton).width(plycnf.PAI_WIDTH).height(plycnf.PAI_HEIGHT);
            }
            table.row();
        }
    }

    // 牌選択時の動作
    private void setPaiListener(final Pai pai) {
        Gdx.app.log(TAG, "setPaiListener");
        pai.imgButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(TAG, "Pai Pressed");
                // 直近に選択された牌が無ければ追加。
                if (slctedPai == null){
                    Gdx.app.log(TAG, "no exist selected Pai");
                    slctedPai = pai;
                }
                // 同じ牌を選択したときは、直近の牌を削除
                else if (slctedPai.equals(pai)) {
                    Gdx.app.log(TAG, "equal Pai pressed");
                    slctedPai = null;
                }
                // 異なる牌
                else if (slctedPai.sameType(pai) && jdg.delJudgemnt(pai, slctedPai, paiList)){
                    Gdx.app.log(TAG, "diff pai selected");
                    // 牌の無力化
                    deletePai(paiList, pai, slctedPai);
                    // 全牌が除去されたかの判定
                    if(jdg.isAllPaiDeleted(paiList)) {
                        game.setScreen(new ResultScreen(game, mode, System.currentTimeMillis() - startTime));
                    }
                    slctedPai = null;
                }
                else {
                    slctedPai = pai;
                }
                Gdx.app.log(TAG, "slctedPai = " + slctedPai);
                return true;
            }
        });
    }

    // 牌の無力化
    private void deletePai(List<Pai> paiList, Pai nowPai, Pai slctedPai) {
        Gdx.app.log(TAG, "deletePai");
        // ボタン登録フラグ抹消
        paiList.get(jdg.convPos_Id(nowPai.position)).invisible = true;
        paiList.get(jdg.convPos_Id(slctedPai.position)).invisible = true;
//        nowPai.imgButton.addAction(fade);
//        slctedPai.imgButton.addAction(fade);
        // ボタン不可視化
        nowPai.imgButton.setVisible(false);
        slctedPai.imgButton.setVisible(false);
    }

    // タイトルへ戻るボタン
    TextButton getTitleButton() {
        TextButton txtBtn = new TextButton(Config.TITL, Config.skin);
        txtBtn.setSize(Config.TXTBTN_WIDTH_S, Config.TXTBTN_HEIGHT);
        txtBtn.setPosition(0f, 0f);
        txtBtn.getLabel().setFontScale(Config.TXT_SIZE_SS);
        setBtnListener(txtBtn);
        return txtBtn;
    }
    // result画面へ強制以降(デバッグ用)
    TextButton getResultButton() {
        TextButton txtBtn = new TextButton(Config.RSLT, Config.skin);
        txtBtn.setSize(Config.TXTBTN_WIDTH_S, Config.TXTBTN_HEIGHT);
        txtBtn.setPosition(Config.SCRN_WIDTH - Config.TXTBTN_WIDTH_S, 0f);
        txtBtn.getLabel().setFontScale(Config.TXT_SIZE_SS);
        setBtnListener(txtBtn);
        return txtBtn;
    }
    // スクリーン遷移用リスナー
    private void setBtnListener(final TextButton txtBtn) {
        txtBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                String gamemode = txtBtn.getText().toString();
                Gdx.app.log(TAG, "gameMode = " + gamemode);
                // タイトル画面への遷移
                if(gamemode.equals(Config.TITL)) {
                    game.setScreen(new TitleScreen(game));
                }
                // 結果画面へ遷移
                else if(gamemode.equals(Config.RSLT)) {
                    game.setScreen(new ResultScreen(game, mode, System.currentTimeMillis() - startTime));
                }
                return true;
            }
        });
    }
}
