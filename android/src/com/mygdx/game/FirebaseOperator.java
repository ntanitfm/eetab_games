package com.mygdx.game;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.item.Config;
import com.mygdx.game.result.ResultData;
import com.mygdx.game.main.DatabaseOperator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * FireBaseOperator
 *
 * 抽象化されたDbo(DataBaseOperator)の
 * Androidにおける処理の定義が行われたクラス
 *
 * tf.thcp@gmail.comアカウントのGAMEsプロジェクトに接続されている
 *
 * Created by ntani on 2017/11/07.
 */

public class FirebaseOperator implements DatabaseOperator {
    private String TAG = FirebaseOperator.class.getSimpleName();
    private List<ResultData> resultList;
    private DatabaseReference dbRef;
    private ValueEventListener vel;
    private FirebaseAuth mAuth;

    public FirebaseOperator() {
        Log.i(TAG, "constructor");
        // Firebaseインスタンス
        dbRef = FirebaseDatabase.getInstance().getReference("Results");
        // データ読み込み(リスナー)
        vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onDataChange called");
                resultList = new ArrayList<>();
                for (DataSnapshot res : dataSnapshot.getChildren()) {
                    resultList.add(res.getValue(ResultData.class));
                }
                // DB初期化
                if(resultList.size() == 0) {
                    Log.i(TAG, "Firebase init");
                    for(int i=0; i<10; i++) {
                        dbRef.push().setValue(new ResultData(Config.PLAY_LV1, 120000, " **** "));
                        dbRef.push().setValue(new ResultData(Config.PLAY_LV2, 120000, " **** "));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCancelled is called");
            }
        };
        dbRef.addValueEventListener(vel);
        Log.i(TAG, "constructor finished");
    }
    @Override
    public void push(ResultData res) {
        Log.i(TAG, "push called");
        dbRef.push().setValue(res);
    }
    @Override
    public List<ResultData> read() {
        Log.i(TAG, "read called");
//        dbRef.addListenerForSingleValueEvent(vel);
        if (resultList == null) {
            return new ArrayList<>();
        }
        // 並べ替え
        Collections.sort(resultList, new Comparator<ResultData>() {
            @Override
            public int compare(ResultData o1, ResultData o2) {
                long comp = o1.time - o2.time;
                if(comp<0) return -1;
                else return 1;
            }
        });
        return resultList;
    }
}
