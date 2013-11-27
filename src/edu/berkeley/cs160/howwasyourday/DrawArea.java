package edu.berkeley.cs160.howwasyourday;

import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class DrawArea extends View {
	
	
	private Paint paint;
	private Path path;
	private int color;
	private int size;
	private Canvas c;
	private Bitmap bitmap;
	private int height;
	private int width;
	
	public DrawArea(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		color = Color.BLACK;
		size = 6;
		setUp(color, size);
	}
	
	@Override
	 protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
	     super.onSizeChanged(xNew, yNew, xOld, yOld);
	     
	     width = xNew;
	     height = yNew;
	     bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
		 c = new Canvas(bitmap);
	}
	
	private void setUp(int color, int size) {
		paint = new Paint();
		path = new Path();
		paint.setAntiAlias(true);
	    paint.setColor(color);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setStrokeWidth(size);
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
    	
		c.drawPath(path, paint);
    	
    	canvas.drawBitmap(bitmap, 
				null,
		new Rect(0, 0, width, height), null);
    	
    }
	
	public void setColor(int color) {
		this.color = color;
		paint.setColor(color);
	}
	public void text(String inp) {
		paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(20);
		c.drawText(inp,50, 230, paint);
	}
	
	public void setSize(int size) {
		this.size = size;
		paint.setStrokeWidth(size);
	}
	
	public void moveTo(float x, float y) {
		path.moveTo(x, y);
	}

	public void lineTo(float x, float y) {
		path.lineTo(x, y);
	}
	
	public void save() {
		setUp(color, size);
	}
	
	public void saveBitmap(FileOutputStream fos) {
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
		clear();
	}
	
	public void clear() {
		bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
		c = new Canvas(bitmap);
		invalidate();
	}

}
