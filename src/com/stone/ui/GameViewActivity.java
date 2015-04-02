package com.stone.ui;


import com.stone.view.GameView2;

import android.app.Activity;
import android.os.Bundle;

public class GameViewActivity extends Activity
{
	private GameView2 mGameView = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		mGameView = new GameView2(this);
		
		setContentView(mGameView);
	}
}
