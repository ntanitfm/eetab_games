package com.mygdx.game;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.main.Shisen;

public class AndroidLauncher extends AndroidApplication {
	String TAG = AndroidLauncher.class.getSimpleName();
	FirebaseOperator fbo = new FirebaseOperator();

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Shisen(fbo), config);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode== KeyEvent.KEYCODE_BACK){
			Log.i(TAG, "バックキーが押された");
			DialogFragment dlgFlagment = new DlgFragment();
			dlgFlagment.show(getFragmentManager(), TAG);
			return true;
		}
		return false;
	}
}
