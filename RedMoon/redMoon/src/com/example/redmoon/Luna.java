package com.example.redmoon;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
/**
 * @author SajRus
 *
 */
public class Luna extends Activity{
			// Tag del log
			private final static String LOG_TAG = "Luna";

			/** Called when the activity is first created. */
			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.main);
				// Otteniamo il riferimento alla TextView
				TextView outputMessage = (TextView) findViewById(R.id.outputMessage);
				outputMessage.setText(R.string.luna_temp);
			}
}
