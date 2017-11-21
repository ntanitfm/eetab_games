package com.mygdx.game.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.main.Shisen;

/**
 * プログラム中に広範に用いられる変数群。
 * インスタンス化を行わずに使用する。
 * Created by ntani on 2017/10/27.
 */

public class Config {
    private Shisen game;
    // 独立変数
    final static public float SCRN_WIDTH            = 1280;     // 仮想スクリーンの幅
    final static public float SCRN_HEIGHT           = 800;      // 仮想スクリーンの高さ
    final static public float TTL_PAI_HEIGHT        = 200;      // タイトル牌の高さ(幅は自動決定)
    final static public float TTL_PAI_ROTATE_SPD    = 2;        // タイトル牌の回転速度
    final static public float TXTBTN_WIDTH_L        = 300;      // ボタン幅サイズ(大)
    final static public float TXTBTN_WIDTH_M        = 150;      // ボタン幅サイズ(中)
    final static public float TXTBTN_WIDTH_S        = 108;      // ボタン幅サイズ(小)
    final static public float TXTBTN_HEIGHT         = 50;       // ボタンの高さ
    final static public float TTLIMG_HEIGHT         = 200;      // タイトルイメージの高さ(幅は自動決定)
    final static public float TXT_SIZE_SS           = 0.4f;     // テキストサイズ(小)
    final static public float TXT_SIZE_S            = 0.5f;     // テキストサイズ(小)
    final static public float TXT_SIZE_M            = 0.8f;     // テキストサイズ(中)
    final static public float HEAD_TXT_SIZE         = 1.2f;       // ヘッド部分タイトルサイズ
    final static public float RSLT_TIME_SIZE        = 1.5f;       // 結果秒数表示サイズ
    // 従属変数
    final static public float TTL_PAI_WIDTH         = TTL_PAI_HEIGHT * 0.7f;
    final static public float SCRN_WIDTH_CTR        = SCRN_WIDTH / 2;
    final static public float SCRN_HEIGHT_CTR       = SCRN_HEIGHT / 2;
    final static public float TTL_PAI_WIDTH_CTR     = TTL_PAI_WIDTH / 2;
    final static public float TTL_PAI_HEIGHT_CTR    = TTL_PAI_HEIGHT / 2;
    final static public float TXTBTN_WIDTH_L_CTR    = TXTBTN_WIDTH_L / 2;
    final static public float TTL_TXTBTN_HEIGHT_CTR = TXTBTN_HEIGHT / 2;
    final static public float TTLIMG_WIDTH          = TTLIMG_HEIGHT * 2.9f;
    final static public float TTLIMG_HEIGHT_CTR     = TTLIMG_HEIGHT / 2;
    final static public float TTLIMG_WIDTH_CTR      = TTLIMG_WIDTH / 2;
    // 状態名
    final static public String NO_SLCT              = "NO_SELECTED";
    final static public String TITL                 = "タイトル";
    final static public String PLAY_LV1             = "ノーマル";
    final static public String PLAY_LV2             = "ハード";
    final static public String RSLT                 = "結果";
    final static public String RANK                 = "ランキング";
    // シーン
    static public SpriteBatch batcher;
    static public OrthographicCamera camera;
    static public Viewport viewport;
    static public Skin skin;

    static public void load() {
        batcher = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCRN_WIDTH, SCRN_HEIGHT, camera);
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
    }

    // 描画用ルーチン
    static public void drawRoutine() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batcher.setProjectionMatrix(camera.combined);
    }
}
