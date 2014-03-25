package parsing;
import android.util.Log;
/**
 * @author SajRus
 *
 */
public class Meteo {
	//data
	private String lastBuildDate;
	
	/*
	 * yweather:location 
	 */
	private String city;
	//stato
	private String country;
	
	/*
	 * yweather:condition
	 * -text
	 * -temp
	 */
	private int temp;
	
	//descrizione del tempo
	private String text;
	
	public Meteo(){}
	public Meteo(String lastBuildDate, String city, String country, int temp,String text ){
		this.lastBuildDate = lastBuildDate;
		this.city = city;
		this.country = country;
		this.temp = temp;
		this.text = text;
	}

	/*
	 * Getters method
	 */
	public String getlastBuildDate(){
		return lastBuildDate;
	}
	public String getCity(){
		return city;
	}
	public String getCountry(){
		return country;
	}
	public int getTemp(){
		return temp;
	}
	public String getText(){
		return text;
	}
	/*
	 * Setter method
	 */
	public void setlastBuildDate(String lastBuildDate){
		this.lastBuildDate = lastBuildDate;
		Log.i("Data :", lastBuildDate);
	}
	public void setCity(String city){
		this.city = city;
		Log.i("My city:", city);
	}
	public void setCountry(String country){
		this.country = country;
		Log.i("Stato:", country);
	}
	public void setText(String text){
		this.text = text;
		Log.i("Previsioni:", text);
	}
	public void setTemp(int temp){
		this.temp = temp;
		Log.i("Temperatura:", String.valueOf(temp));
	}
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("Previsioni meteo! ");
		sb.append("\n\n");
		sb.append(("Data: ") + getlastBuildDate() );
		sb.append(", \n");
		sb.append("Luogo : "+getCountry());
		sb.append(", ");
		sb.append(getCity());
		sb.append(", \n");
		sb.append("Previsioni: "+ getText());
		sb.append(", ");
		sb.append(String.valueOf((int)((getTemp()-32)/1.8)));
		sb.append("°C.\n");
		
		return sb.toString();
	}
}
