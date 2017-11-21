package com.mygdx.game.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
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
    final static public float TXTBTN_WIDTH_S        = 105;      // ボタン幅サイズ(小)
    final static public float TXTBTN_HEIGHT         = 50;       // ボタンの高さ
    final static public float TTLIMG_HEIGHT         = 200;      // タイトルイメージの高さ(幅は自動決定)
    final static public float TXT_SIZE_S            = 1f;       // テキストサイズ(小)
    final static public float TXT_SIZE_M            = 1.3f;       // テキストサイズ(小)
    final static public float HEAD_TXT_SIZE         = 2.2f;       // ヘッド部分タイトルサイズ
    final static public float RSLT_TIME_SIZE        = 3.5f;       // 結果秒数表示サイズ
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
    static public DistanceFieldShader distanceFieldShader;

    static public void load() {
        batcher = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCRN_WIDTH, SCRN_HEIGHT, camera);
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        distanceFieldShader = new DistanceFieldShader();

        // フォント生成
        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/default/font.fnt"));

        // フィルタ設定
        Texture.TextureFilter minFilter = Texture.TextureFilter.MipMapLinearNearest;
        Texture.TextureFilter magFilter = Texture.TextureFilter.Linear;
        for (int i = 0; i < font.getRegions().size; i++) {
            Gdx.app.log("@@@", "num : "+i);
            font.getRegion(i).getTexture().setFilter(minFilter, magFilter);
        }

//        skin = new Skin();
        skin.add("default-font", font, BitmapFont.class);
//        skin.load(Gdx.files.internal("skins/uiskin.json"));
    }

    // 描画用ルーチン
    static public void drawRoutine() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0.5f, 1f, 0f, 1f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batcher.setProjectionMatrix(camera.combined);
    }

    // シェーダ
    public static class DistanceFieldShader extends ShaderProgram {
        public DistanceFieldShader () {
            super(Gdx.files.internal("shader/distancefield.vert"), Gdx.files.internal("shader/distancefield.frag"));
            if (!isCompiled()) {
                throw new RuntimeException("Shader compilation failed:\n" + getLog());
            }
        }
        public void setSmoothing (float smoothing) {
            float delta = 0.5f * MathUtils.clamp(smoothing, 0, 1);
            setUniformf("u_lower", 0.5f - delta);
            setUniformf("u_upper", 0.5f + delta);
        }
    }
}
