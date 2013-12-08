package edu.berkeley.cs160.howwasyourday;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SharePhoto extends Activity {
	
	private static final int TAKE_PICTURE_ACTION_CODE = 1;
    private static final int SELECT_PICTURE_ACTION_CODE = 2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_photo);
		(findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {

                // in onCreate or any event where your want the user to
                // select a file
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE_ACTION_CODE);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share_photo, menu);
		return true;
	}

	public void toCamera(View view) {	
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File f = createImageFile();
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
	    startActivityForResult(takePictureIntent, TAKE_PICTURE_ACTION_CODE);
	}
	
	private String currentPhotoPath;
	private File createImageFile() {
		File storageDir = new File(Environment.getExternalStorageDirectory() + "/howwasyourday/");
		storageDir.mkdirs();
	    String timeStamp = 
	            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPG_" + timeStamp;
        File image = new File(storageDir, imageFileName);
        currentPhotoPath = image.getAbsolutePath();
        return image;
	}
	
	private void updateImage() {
		/*BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		Bitmap image = BitmapFactory.decodeFile(currentPhotoPath, options);
		final ImageView imgView = (ImageView)findViewById(R.id.image2);
		imgView.setImageDrawable(new BitmapDrawable(image));*/
		Intent i = new Intent(this, AddComment.class);
		i.putExtra("photo", currentPhotoPath);
		startActivity(i);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TAKE_PICTURE_ACTION_CODE && resultCode == RESULT_OK) {
			updateImage();
		}
		else if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE_ACTION_CODE) {
            Uri selectedImageUri = data.getData();
            currentPhotoPath = getPath(selectedImageUri);
            updateImage();
        }
		

		
	}
	public String getPath(Uri uri) {
        // just some safety built in 
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
}
	
	
	

}
