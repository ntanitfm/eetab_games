package com.mygdx.game.main;

import com.mygdx.game.result.ResultData;

import java.util.List;

/**
 * DataBaseOperator
 *
 * データを保存したり取得したりするための
 * 各プラットフォームに依存しない
 * 抽象化されたインターフェイス
 *
 * @func push
 * データベース上にResultDataの内容を書き込み
 *
 * @func read()
 * データベースからResultData型のリストを取得。
 * リストはtimeの少ない順に並べ替えが行なわれている。
 *
 * Created by ntani on 2017/11/07.
 */

public interface DatabaseOperator {
    void push(ResultData res);
    List<ResultData> read();
}
