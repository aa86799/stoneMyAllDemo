package com.stone.opengles;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

/*
 * 	注意：glColor系列函数，在参数类型不同时，表示“最大”颜色的值也不同。
	采用f和d做后缀的函数，以1.0表示最大的使用。
	采用b做后缀的函数，以127表示最大的使用。
	采用ub做后缀的函数，以255表示最大的使用。
	采用s做后缀的函数，以32767表示最大的使用。
	采用us做后缀的函数，以65535表示最大的使用。
	这些规则看似麻烦，但熟悉后实际使用中不会有什么障碍。
 */
public class GLRender implements Renderer {
	int one = 0x10000;//65535
	/*
	 * 0x10000是出于OPENGL前期内存节约的考虑，以INT型模拟FLOAT型来表示，
	 * 0x 0001 0000 前面4位表示小数点前，后4位表示小数点后，所以0x10000表示浮点数的1。
	 * 如果用的是FloatBuffer，直接写1.0。
	 */
	
	/*
	 * 2.3以上不能直接用IntBuffer.wrap(int[]) 
	 * //三角形三个顶点
	 *  private IntBuffer triggerBuffer = IntBuffer.wrap(
	 *  			new int[]{ 0,one,0, //上顶点
	 *  						 -one,-one,0, //左下点
									one,-one,0,}); //右下点 
		//正方形的4个顶点
		 private IntBuffer quaterBuffer = IntBuffer.wrap(
		 		new int[]{ one,one,0, -one,one,0, one,-one,0, -one,-one,0});
	 */
	int[] triggerArray = { 	0,one,0, //上顶点
					 		-one,-one,0, //左下点
					 		one,-one,0};
	
	int[] quaterArray = { 	one,one,0, 
							-one,one,0, 
							one,-one,0,
							-one,-one,0};
	
	int[] colorArray ={		one,0,0,one,  
		            		0,one,0,one,  
		            		0,0,one,one};
	@Override
	// 可以 做一些对open的初始化工作
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		// 启用阴影平滑
		gl.glShadeModel(GL10.GL_SMOOTH);

		// 黑色背景
		gl.glClearColor(0, 0, 0, 0);

		// 设置深度缓存
		gl.glClearDepthf(1.0f);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 所作深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);

		// 告诉系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

	}

	@Override
	// 初始化后执行一次，此后当窗口大小改变时执行 可以在此设置场景的大小
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		float ratio = (float) width / height;
		gl.glViewport(0, 0, width, height);// 与屏幕相同大小
		gl.glMatrixMode(GL10.GL_PROJECTION);// 设置投影矩阵(它负责为场景增加透视)
		gl.glLoadIdentity();// 重置投影矩阵，恢复成原始状态
		gl.glFrustumf(ratio, ratio, -1, 1, 1, 10);// 设置视口的大小
		// 选择模型观察矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// 重置模型观察矩阵
		gl.glLoadIdentity();

	}

	@Override
	// 绘图操作
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); // 清除屏幕和深度缓存
		gl.glLoadIdentity();
		/*
		 * 当调用 gl.glLoadIdentity()后，实际上是将当前点移到了屏幕中心， x坐标从左至右，y坐标从下至上，z坐标从里至外
		 * opengl屏幕中心的坐标值是x和y上的0.0f点，
		 * 中心坐标左边为负，右边为正；移向屏幕顶端为正，移向屏幕底端为负；移入屏幕深处为负，移出屏幕为正
		 */
		// 左移 1.5 单位，并移入屏幕 6.0
		gl.glTranslatef(-1.5f, 0.0f, -6.0f);
		
		//开启颜色渲染功能  
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);  
		//设置颜色,平滑着色  
		gl.glColorPointer(4, GL10.GL_FIXED, 0, bufferUtil(colorArray)); 
//		gl.glColorPointer(4, GL10.GL_FLOAT, 0, floatbuffer); 

		
		// 允许设置顶点
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 设置三角形
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, bufferUtil(triggerArray));
		// 绘制三角形
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

		// 重置当前的模型观察矩阵
		gl.glLoadIdentity();
		// 右移 1.5 单位，并移入屏幕 6.0
		gl.glTranslatef(1.5f, 0.0f, -6.0f);

		// 设置和绘制正方形
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, bufferUtil(quaterArray));
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

		// 取消顶点设置
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		//取消颜色设置
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}

	/*
	 * OpenGL 是一个非常底层的画图接口，它所使用的缓冲区存储结构是和我们的 java 程序中不相同的。 Java
	 * 是大端字节序(BigEdian)，而 OpenGL 所需要的数据是小端字节序(LittleEdian)。 所以，我们在将 Java 的缓冲区转化为
	 * OpenGL 可用的缓冲区时需要作一些工作。建立buff的方法如下
	 */
	public Buffer bufferUtil(int[] arr) {
		IntBuffer mBuffer;

		// 先初始化buffer,数组的长度*4,因为一个int占4个字节
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		// 数组排列用nativeOrder
		qbb.order(ByteOrder.nativeOrder());

		mBuffer = qbb.asIntBuffer();
		mBuffer.put(arr);
		mBuffer.position(0);

		return mBuffer;
	}

}
