package parsing;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
/**
 * @author SajRus
 *
 */
public class ParserHandler extends DefaultHandler{
	
	//Ogetto contenente le informazioni del meteo
	Meteo data = new Meteo();

	@Override
	public void startDocument(){
		Log.i("Parser_handle", "Inizio del documento");
	}
	
	//Salvo i dati parsati nel oggetto data
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	if(qName.equalsIgnoreCase("yweather:location")){
    		Log.i("StartElemXml", attributes.getValue("city"));
    		data.setCity(attributes.getValue("city"));
    		Log.i("StartElemXml", attributes.getValue("country"));
    		data.setCountry(attributes.getValue("country"));
    		}
    	if(qName.equalsIgnoreCase("yweather:condition")){
    		Log.i("StartElemXml", attributes.getValue("text"));
    		data.setText(attributes.getValue("text"));
    		Log.i("StartElemXml", attributes.getValue("temp"));
    		data.setTemp(Integer.valueOf(attributes.getValue("temp")));
    		Log.i("StartElemXml", attributes.getValue("date"));
    		data.setlastBuildDate(attributes.getValue("date"));
    		}	    	
    }
	
	public void endElement() throws SAXException{
		//Log.i("Parser_handle", "Fine del elemento corrente");
	}
	
	@Override
	public void endDocument()throws SAXException{
		Log.i("Parser_handle", "Fine del documento");
		Log.i("Parser_handle", data.toString());	
	}
	
    @Override
	public void characters(char[] ch, int start, int length) throws SAXException {}

    public String getMeteo(){
    	return data.toString();
    }
}
