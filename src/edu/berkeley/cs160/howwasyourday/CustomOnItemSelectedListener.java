package edu.berkeley.cs160.howwasyourday;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
 
public class CustomOnItemSelectedListener implements OnItemSelectedListener {
  String selected;
	
  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
	  
  }
  
  public void getItemSelected(AdapterView<?> parent, View view, int pos,long id){
	  selected = parent.getItemAtPosition(pos).toString();
  }
 
  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
  }
 
}