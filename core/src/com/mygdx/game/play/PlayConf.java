package com.mygdx.game.play;

import com.mygdx.game.item.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 選択された難易度に応じて
 * 牌の配置を設定する
 * Created by ntani on 2017/11/01.
 */

class PlayConf {
    int ROWS, COLS;                 // 盤上の行数と列数
    float PAI_HEIGHT, PAI_WIDTH;    // 各牌のサイズ
    List<String> paiTypeList;       // 牌の種類の配列

    PlayConf(String mode) {
        if(mode.equals(Config.PLAY_LV1)) setLv1Conf();
        if(mode.equals(Config.PLAY_LV2)) lv2EnvConf();
        // 牌並べ替え
        Collections.shuffle(paiTypeList);
    }
    // EASYモード情報
    private void setLv1Conf() {
        ROWS = 6;
        COLS = 9;
        PAI_HEIGHT = (Config.SCRN_HEIGHT * 0.95f) / ROWS;
        PAI_WIDTH = (Config.SCRN_WIDTH * 0.77f) / COLS;
        paiTypeList = new ArrayList<String>(Arrays.asList(
            "0","1","2","9","10","11","18","19","20","27","28","29","30",
            "0","1","2","9","10","11","18","19","20","27","28","29","30",
            "0","1","2","9","10","11","18","19","20","27","28","29","30",
            "0","1","2","9","10","11","18","19","20","27","28","29","30",
            "31","31"
        ));
    }
    // FULLモード情報
    private void lv2EnvConf() {
        ROWS = 8;
        COLS = 17;
        PAI_HEIGHT = (Config.SCRN_HEIGHT * 0.87f) / ROWS;
        PAI_WIDTH = (Config.SCRN_WIDTH * 0.87f) / COLS;
        paiTypeList = new ArrayList<String>();
        for (int i = 0; i <= 33; i++) {
            for (int j = 0; j < 4; j++) {
                paiTypeList.add("" + i);
            }
        }
    }
}
