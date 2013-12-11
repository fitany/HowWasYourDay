package edu.berkeley.cs160.howwasyourday;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class DrawDoodle extends Activity {
	
	DrawArea drawArea;
	OnTouchListener touchListener;
	String currentPath;
	int preColor = Color.BLACK;
	boolean erased = false;
	Button redBtn;
	Button yellowBtn;
	Button blueBtn;
	Button greenBtn;
	Button purpleBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_draw_doodle);
		
		drawArea = (DrawArea) findViewById(R.id.drawArea);
		redBtn = (Button) findViewById(R.id.red_button);
		yellowBtn = (Button) findViewById(R.id.yellow_button);
		blueBtn = (Button) findViewById(R.id.blue_button);
		greenBtn = (Button) findViewById(R.id.green_button);
		purpleBtn = (Button) findViewById(R.id.purple_button);
		
		touchListener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				float y = event.getY();
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						drawArea.moveTo(x, y);
						break;
					case MotionEvent.ACTION_MOVE:
						drawArea.lineTo(x, y);
						break;
					case MotionEvent.ACTION_UP:
						drawArea.lineTo(x, y);
						drawArea.save();
						break;
				}
				drawArea.invalidate();
				return true;
			}
		}; 
		
		drawArea.setOnTouchListener(touchListener);
		
////		EditText text = (EditText)findViewById(R.id.editText);
//		text.setOnEditorActionListener(new OnEditorActionListener() {
//
//			@Override
//			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
//				if (arg2.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//					String value = arg0.getText().toString();
//					drawArea.text(value);
//					arg0.setVisibility(TextView.INVISIBLE);
//					return true;
//				}
//				return false;
//			}
//			
//		});
	} 
	
	public void clear(View v) {
		drawArea.clear();
	}
	
	public void getColor(View v) {
		int loc = 0;
		if(v.getId()== R.id.blue_button) {
			Toast.makeText(this, "You have chosen blue.",
			Toast.LENGTH_SHORT).show();
			loc = Color.BLUE;
			resetButton("blue");
		} else if (v.getId()== R.id.red_button) {
			Toast.makeText(this, "You have chosen red.",
					Toast.LENGTH_SHORT).show();
					resetButton("red");
					loc = Color.RED;
		} else if (v.getId()== R.id.green_button) {
			Toast.makeText(this, "You have chosen green.",
					Toast.LENGTH_SHORT).show();
					loc = Color.GREEN; 
					resetButton("green");
		} else if (v.getId()== R.id.yellow_button) {
			Toast.makeText(this, "You have chosen yellow.",
					Toast.LENGTH_SHORT).show();
					loc = Color.YELLOW; 
					resetButton("yellow");
		} else if (v.getId()== R.id.purple_button) {
			Toast.makeText(this, "You have chosen purple.",
					Toast.LENGTH_SHORT).show();
					loc = this.getResources().getColor(R.color.purple);
					resetButton("purple");
		} else if (v.getId()== R.id.black_button) {
			Toast.makeText(this, "You have chosen black",
					Toast.LENGTH_SHORT).show();
					loc = Color.BLACK; 
					resetButton("all");
		}  else {
			Toast.makeText(this, "some error happen",
					Toast.LENGTH_SHORT).show();
		}
		drawArea.setColor(loc);
	}
	
	@SuppressLint("NewApi")
	private void resetButton(String s) {
		if (s.equals("red")) {
			redBtn.setBackground(this.getResources().getDrawable(R.drawable.red_button_pressed));
			blueBtn.setBackground(this.getResources().getDrawable(R.drawable.blue_button));
			yellowBtn.setBackground(this.getResources().getDrawable(R.drawable.yellow_button));
			purpleBtn.setBackground(this.getResources().getDrawable(R.drawable.purple_button));
			greenBtn.setBackground(this.getResources().getDrawable(R.drawable.green_button));
		} else if (s.equals("blue")) {
			redBtn.setBackground(this.getResources().getDrawable(R.drawable.red_button));
			blueBtn.setBackground(this.getResources().getDrawable(R.drawable.blue_button_pressed));
			yellowBtn.setBackground(this.getResources().getDrawable(R.drawable.yellow_button));
			purpleBtn.setBackground(this.getResources().getDrawable(R.drawable.purple_button));
			greenBtn.setBackground(this.getResources().getDrawable(R.drawable.green_button));
		} else if (s.equals("green")) {
			redBtn.setBackground(this.getResources().getDrawable(R.drawable.red_button));
			blueBtn.setBackground(this.getResources().getDrawable(R.drawable.blue_button));
			yellowBtn.setBackground(this.getResources().getDrawable(R.drawable.yellow_button));
			purpleBtn.setBackground(this.getResources().getDrawable(R.drawable.purple_button));
			greenBtn.setBackground(this.getResources().getDrawable(R.drawable.green_button_pressed));
		} else if (s.equals("purple")) {
			redBtn.setBackground(this.getResources().getDrawable(R.drawable.red_button));
			blueBtn.setBackground(this.getResources().getDrawable(R.drawable.blue_button));
			yellowBtn.setBackground(this.getResources().getDrawable(R.drawable.yellow_button));
			purpleBtn.setBackground(this.getResources().getDrawable(R.drawable.purple_button_pressed));
			greenBtn.setBackground(this.getResources().getDrawable(R.drawable.green_button));
		} else if (s.equals("yellow")) {
			redBtn.setBackground(this.getResources().getDrawable(R.drawable.red_button));
			blueBtn.setBackground(this.getResources().getDrawable(R.drawable.blue_button));
			yellowBtn.setBackground(this.getResources().getDrawable(R.drawable.yellow_button_pressed));
			purpleBtn.setBackground(this.getResources().getDrawable(R.drawable.purple_button));
			greenBtn.setBackground(this.getResources().getDrawable(R.drawable.green_button));
		} else {
			redBtn.setBackground(this.getResources().getDrawable(R.drawable.red_button));
			blueBtn.setBackground(this.getResources().getDrawable(R.drawable.blue_button));
			yellowBtn.setBackground(this.getResources().getDrawable(R.drawable.yellow_button));
			purpleBtn.setBackground(this.getResources().getDrawable(R.drawable.purple_button));
			greenBtn.setBackground(this.getResources().getDrawable(R.drawable.green_button));
		}
	}
	
	public void getEraser(View v) {
		preColor = drawArea.getColor();
		drawArea.setColor(Color.WHITE);
		drawArea.setSize(7);
		erased = true;
	}
	
	public void getStroke(View v) {
		int size = 10;
		if (erased) {
			drawArea.setColor(preColor);
		}
		if (v.getId()== R.id.Pencil) {
			Toast.makeText(this, "You have chosen pencil.",
					Toast.LENGTH_SHORT).show();
			size = 4;
		} else if (v.getId()== R.id.Brush) {
			Toast.makeText(this, "You have chosen brush.",
					Toast.LENGTH_SHORT).show();
			size= 10;
		}  else {
			
		}
		drawArea.setSize(size);
	}
	
	
//	public void showText (View v) {
//		if (v.getId()== R.id.Text) {
//			Toast.makeText(this, "You have chosen text.",
//					Toast.LENGTH_SHORT).show();
//		}
//		EditText text = (EditText)findViewById(R.id.editText);
//		text.setVisibility(View.VISIBLE);
//
//	}
	
	public void done(View v){
		String path = Environment.getExternalStorageDirectory().toString() + File.separator + "Doodle";
		File storageDir = new File(path);
		if (!storageDir.exists()) {
			storageDir.mkdirs();
		}
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "HWYD_" + timeStamp + ".PNG";
        File image = new File(storageDir, imageFileName);
        currentPath = image.getAbsolutePath();
        FileOutputStream fos = null;
        
        try {           
            fos = new FileOutputStream(image);
            drawArea.saveBitmap(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		Intent i = new Intent(this, AddComment.class);
		i.putExtra("path", currentPath);
		i.putExtra("type", "doodle");
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.draw_doodle, menu);
		return true;
	}
	
	public void home(View v) {
		Intent i = new Intent(this, Timeline.class);
		startActivity(i);
	}
	

}
