package com.example.redmoon;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;

import parsing.UserXmlParser;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
/**
 * @author SajRus
 *
 */
public class Terra extends Activity{
		// Tag del log
		private final static String LOG_TAG = "Terra_Activity";
		/*
		 * Identificativo della ProgressDialog di attesa
		 */
		private final static int WAITING_PROGRESS_DIALOG_ID = 1;
		/*
		 * Riferimento alla ProgressDialog
		 */
		private ProgressDialog progressDialog;
		/*
		 * Indirizzo del server a cui accedere
		 */
		private final static String URL_BASE =  "http://weather.yahooapis.com/forecastrss?w=";
		private final static String city = "615702";
		private final static String TARGET_URL = URL_BASE + city;
		private Handler terra_handler;
		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			// Otteniamo il riferimento alla TextView
			final TextView outputMessage = (TextView) findViewById(R.id.outputMessage);
			final TextView dataMessage = (TextView) findViewById(R.id.dataMessage);
			
			outputMessage.setText(R.string.terra_temp);
			
			terra_handler = new Handler(){
				@Override
				public void handleMessage(Message msg){
					// Estrapoliamo il messaggiodi testo dal msg
					dataMessage.setText(msg.obj.toString());
					//Log.i(LOG_TAG, msg.obj.toString());
				}
			};
		}

		/*
		 * Incapsula a logica di invio della richiesta Http
		 */
		public void terraHttpRequest(View button) {
			// Avviamo un Thread con il corrispondente codice di connessione
			Thread workerThread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						// Otteniamo il riferimento al HttpClient attraverso la
						// Application
						RedMoonApp ca = (RedMoonApp)getApplication();
						HttpClient httpClient = ca.getThreadSafeHttpClient();
						// Creiamo la HttpUriRequest relativamente al metodo GET
						HttpGet request = new HttpGet();
						// Impostiamo il valore dell'URI di destinazione
						URI targetUri = new URI(TARGET_URL);
						request.setURI(targetUri);
						// A questo punto invochiamo il server
						httpClient.execute(request, myResponseHandler);
					} catch (Exception e) {
						showMessageOnOutput(e.getMessage());
						//Log.e(LOG_TAG, e.getMessage());
					} finally {
						dismissDialog(WAITING_PROGRESS_DIALOG_ID);
					}

				}

			});
			// Visualizziamo una Dialog di attesa
			showDialog(WAITING_PROGRESS_DIALOG_ID);			
			// Lo avviamo
			workerThread.start();
		}

		@Override
		protected Dialog onCreateDialog(int id) {
			switch (id) {
			case WAITING_PROGRESS_DIALOG_ID:
				progressDialog = new ProgressDialog(this,
						ProgressDialog.STYLE_SPINNER);
				progressDialog.setTitle("HTTP Connection");
				progressDialog.setMessage("Connecting...");
				progressDialog.setIndeterminate(true);
				return progressDialog;
			default:
				return null;
			}
		}

		/**
		 * Invia un messaggio all'Handler relativo al MainThread per la sua
		 * visualizzazione nella TextView di output
		 * 
		 * @param message
		 *            Messaggio da visualizzare
		 */
		private void showMessageOnOutput(String message) {
			Message messageToSend = terra_handler.obtainMessage();
			messageToSend.obj = message;
			terra_handler.sendMessage(messageToSend);
		}

		private final ResponseHandler<String> myResponseHandler = new ResponseHandler<String>(){
			
			//File file = new File("meteo.xml");
			File root = android.os.Environment.getExternalStorageDirectory();
			File dir = new File(root.getAbsolutePath());
			File file = new File(dir, "meteo.txt");
			PrintWriter pw= null;
			FileOutputStream f = null;
			@Override
			public String handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				try{
					f = new FileOutputStream(file);
					pw = new PrintWriter(f);
					}catch(IOException e){e.printStackTrace();}
				// Estraiamo il risultato dalla risposta. Otteniamo quindi il content
				// da cui leggere le informazioni
				InputStream content = response.getEntity().getContent();
				byte[] buffer = new byte[1024];
				int numRead = 0;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while((numRead=content.read(buffer))!=-1){
					baos.write(buffer, 0, numRead);
				}
				// Chiudiamo lo stream
				content.close();
				// Otteniamo il risultato
				String result = new String(baos.toByteArray());
				//Log.i(LOG_TAG,result);
				try{
					pw.print(result);
				}catch(Exception e){e.printStackTrace();
				}finally{
					if(pw != null){
						pw.flush();
						pw.close();
					}
				}
				/*
				 * Creo un istanza del parser
				 */
				UserXmlParser parser = new UserXmlParser();
				/*
				 * Inizio il parsing del input stream
				 * (usiamo un file di appoggio prima)
				 * in: FileInputStream(path)
				 */
				//String resp = readXml.getXml(new FileInputStream(file));
				String dati = parser.parseXml(new FileInputStream(file));
				Log.i(LOG_TAG, dati);
				showMessageOnOutput(dati);
				
				return dati;
			}

		};
}	
