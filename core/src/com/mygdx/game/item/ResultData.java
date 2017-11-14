package com.mygdx.game.item;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 結果データの格納
 * Created by ntani on 2017/11/02.
 */

public class ResultData {
    public String mode;
    public long time;
    public String name;
    public String date;

    // Firebase用の空のコンストラクタ
    public ResultData() {}

    public ResultData(String mode, long time, String name) {
        this.mode = mode;
        this.name = name;
        this.time = time;
        // 日付の取得
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        this.date = sdf.format(cal.getTime());
    }

    // Firebase用のGetter, Setter
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "mode = " + mode + ", time = " + time + ", name = " + name + ", data =  " + date;
    }

    public String generateSec() {
        return "" + (time / 1000f) + "秒";
    }
}
