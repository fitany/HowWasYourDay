package edu.berkeley.cs160.howwasyourday;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;

public class PieView extends View {
	Paint p;
	private ArrayList<Integer> aList = new ArrayList<Integer>();
	int width;
	int height;
	int bar_width;
	int bar_height;
	int bar_height1;
	int c[] = { Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA,
	         Color.DKGRAY, Color.LTGRAY, Color.BLACK, Color.rgb(230, 300, 250)};

	public PieView(Context context, ArrayList<Integer> data) {
	    super(context);
	    p = new Paint();
	    aList = data;
	}


	public void draw(Canvas canvas) {
	    int x = getWidth();
	    int y = getHeight();
	    float t = getTotal();
	    p.setColor(Color.parseColor("#78777D"));
	    p.setStyle(Style.STROKE);
	    p.setStrokeWidth(2);
	    canvas.drawRect(0, 0, x - 1, y - 1, p);
	    int n = aList.size();
	    float curPos = -90;
	    p.setStyle(Style.FILL);
	    RectF rect = new RectF(20, 20, x - 20, y - 20);
	    for (int i = 0; i < n; i++) {
	        p.setColor(c[i]);
	        float thita = (t == 0) ? 0 : 360 * aList.get(i) / t;
	        canvas.drawArc(rect, curPos, thita, true, p);
	        curPos = curPos + thita;
	    }
	}

	private float getTotal() {
	    int total = 0;
	    for (int i = 0; i < aList.size(); i++) {
	        total = total + aList.get(i);
	    }
	    return total;
	 }
}