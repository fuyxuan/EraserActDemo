package com.example.eraseractdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class EraseView extends View {

	private boolean isMove = false;
	private Bitmap bitmap = null;
	private Bitmap frontBitmap = null;
	private Path path;
	private Canvas mCanvas;
	private Paint paint;

	public EraseView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (mCanvas == null) {
			EraseBitmp();
		}
		canvas.drawBitmap(bitmap, 0, 0, null);
		mCanvas.drawPath(path, paint);// 繪製一個路徑
		super.onDraw(canvas);
	}

	public void EraseBitmp() {

		bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Bitmap.Config.ARGB_4444);

		frontBitmap = CreateBitmap(Color.GRAY, getWidth(), getHeight());// 灰色圖片

		// 
		paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);// 設置畫筆的風格：空心
		paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));// 設置圖片交叉時的顯示模式
		paint.setAntiAlias(true);// 防據齒
		paint.setDither(true);// 防抖動
		paint.setStrokeJoin(Paint.Join.ROUND);// 設置結合處的樣子，ROUND為圓弧
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(20);// 設置描邊寬度

		path = new Path();

		mCanvas = new Canvas(bitmap);// 設置一個帶圖片的畫布
		mCanvas.drawBitmap(frontBitmap, 0, 0, null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		float ax = event.getX();
		float ay = event.getY();

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			isMove = false;
			path.reset();
			path.moveTo(ax, ay);
			invalidate();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			isMove = true;
			path.lineTo(ax, ay);
			invalidate();
			return true;
		}
		return super.onTouchEvent(event);
	}

	public Bitmap CreateBitmap(int color, int width, int height) {
		int[] rgb = new int[width * height];

		for (int i = 0; i < rgb.length; i++) {
			rgb[i] = color;
		}
		return Bitmap.createBitmap(rgb, width, height, Config.ARGB_8888);
	}

}
