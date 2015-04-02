package com.stone.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
/*
 * Renderer 作用类似 surfaceview的holder.callback
 */
public class MyRenderer implements Renderer {
	
	private float mRed;
	private float mGreen;
	private float mBlue;
	private float mAngle; //角度
	private long mLastFrameTime;
	//顶点
	private float[] mVertices = {
		-1.0f, -1.0f, 0,
		1.0f, -1.0f, 0,
		0, 1.0f, 0
	};
	private FloatBuffer mVertexBuffer;

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		System.out.println("--onSurfaceCreated--");
		ByteBuffer vertextByteBuffer = ByteBuffer.allocateDirect(mVertices.length * 4);
		vertextByteBuffer.order(ByteOrder.nativeOrder());
		mVertexBuffer = vertextByteBuffer.asFloatBuffer();
		mVertexBuffer.put(mVertices);
		mVertexBuffer.position(0);
	}

	@Override //GLSurfaceView形状改变， 如旋转设备
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		System.out.println("--onSurfaceChanged--");
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		float ratio = ((float) width) / height;
		GLU.gluPerspective(gl, 45f, ratio, 0.1f, 100f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override //每当GLSurfaceView 更新时
	public void onDrawFrame(GL10 gl) {
//		System.out.println("--onDrawFrame--"); //该方法会时刻执行
		gl.glClearColor(mRed, mGreen, mBlue, 1.0f);
		//清除颜色和深度缓冲区，使得屏幕全黑
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		updateAngle();
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0, -7);
		gl.glRotatef(mAngle, 0, 0, 1.0f);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(255f, 255, 255, 0);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	private void updateAngle() {
		long now = System.currentTimeMillis();
		if (mLastFrameTime != 0) {
			mAngle += 100 * (now - mLastFrameTime) / 1000.0f;
		}
		mLastFrameTime = now;
	}

	public void setColor(float mRed, float mGreen, float mBlue) {
		this.mBlue = mBlue;
		this.mGreen = mGreen;
		this.mRed = mRed;
	}

}
