package com.stone.opengles;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class BaseActivity extends Activity {
	GLRender render;
	GLSurfaceView glView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		render = new GLRender();
		glView = new GLSurfaceView(getApplicationContext());
		glView.setRenderer(render);
		setContentView(glView);
	}
}
