package com.tdcm.camera;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {
	int takePhotoActionCode = 0;
	public ImageView imageView;
	public File outputFileName;
	public String filePath;
	public String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.img);
		
		try {
			outputFileName = createImageFile(".jpg");
			filePath = outputFileName.getPath();
			Log.v(TAG, "outputFileName"+ outputFileName.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFileName));
		startActivityForResult(intent, takePhotoActionCode);

	}

	private File createImageFile(String fileExtensionToUse) throws IOException {

		File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"MyImages");

		if (!storageDir.exists()) {
			if (!storageDir.mkdir()) {
				Log.d(TAG, "was not able to create it");
			}
		}
		if (!storageDir.isDirectory()) {
			Log.d(TAG, "Don't think there is a dir there.");
		}

		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "FOO_" + timeStamp + "_image";

		File image = File.createTempFile(imageFileName, fileExtensionToUse,storageDir);

		return image;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == takePhotoActionCode && resultCode == RESULT_OK) {
			imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
