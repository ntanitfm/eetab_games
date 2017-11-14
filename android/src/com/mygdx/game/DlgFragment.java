package com.mygdx.game;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ntani on 2017/11/14.
 */

public class DlgFragment extends DialogFragment {
    private String TAG = DlgFragment.class.getSimpleName();
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("ゲームを終了しますか？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i(TAG, "YES selected");
                        getActivity().finish();
                    }
                })
                .setNegativeButton("ゲームに戻る", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i(TAG, "NO Selected");
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
