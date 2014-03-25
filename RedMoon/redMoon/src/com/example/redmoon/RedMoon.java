package com.example.redmoon;
import android.net.Uri;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TabHost;
/**
 * @author SajRus
 *
 */
@SuppressWarnings("deprecation")
public class RedMoon extends TabActivity {
	/*Finestra di dialogo 
	 * può avere solo testo o fino a tre pulsanti*/
	AlertDialog aboutUs;
	Button devInfo;
	static final int INFO_DIALOG_ID = 0;
	// Tag del log
	private final static String LOG_TAG = "RedMoon_MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("RedMoon vi tiene in contatto con il cielo e le stelle...")
		.setCancelable(true)
		.setNeutralButton("Developer", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.twitter.com/SajmirRusi")));
			}			
		});
		aboutUs = builder.create();
		// Otteniamo il TabHost per la gestione dei tab
				TabHost tabHost = getTabHost();
				// Aggiungiamo il tab relativo ai MenuItem Terra, Sole, Luna
				tabHost.addTab(tabHost.newTabSpec("Terra").setContent(
						new Intent(this, Terra.class)).setIndicator(
						"Terra"));
				tabHost.addTab(tabHost.newTabSpec("Sole").setContent(
						new Intent(this, Sole.class)).setIndicator(
						"Sole"));	
				tabHost.addTab(tabHost.newTabSpec("Luna").setContent(
						new Intent(this, Luna.class)).setIndicator(
						"Luna"));	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Accediamo agli eventuali MenuItem di sistema
		super.onCreateOptionsMenu(menu);
		Log.i(LOG_TAG, "onCreateOptionsMenu");
		// Impostiamo l'ordine iniziale
		int order = Menu.FIRST;
		// Creiamo il primo gruppo di MenuItem
		int GROUPA = 0;
		menu.add(GROUPA, 0, order++, R.string.settings);
		menu.add(GROUPA, 1, order++, R.string.aboutus_string);
		menu.add(GROUPA, 2, order++, R.string.exit_menu_string);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		Log.i(LOG_TAG, "onPrepareOptionsMenu");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(LOG_TAG, "onOptionsItemSelected Called!");
		if (item.getItemId() == 0) {
			// IMPOSTAZIONI
			return true;
		} else if (item.getItemId() == 1) {
			//Info
			showDialog(INFO_DIALOG_ID);
			return true;
		}else if (item.getItemId() == 2){
			//chiama il metodo quite
			finish();
		}
		// Importante richiamare l'implementazione padre nel caso
		// di item non gestiti
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected Dialog onCreateDialog(int id){
		switch(id){
		case INFO_DIALOG_ID:
			return aboutUs;
		}
	
		return null;
		
	}
}
