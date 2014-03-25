package parsing;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
/**
 * @author SajRus
 *
 */
public class UserXmlParser {
	public String parseXml(InputStream in){
		String previsioni = new String("");
		try{
			//Otteno un parser SAX based
			SAXParserFactory spf = SAXParserFactory.newInstance();
			
			SAXParser sp = spf.newSAXParser();
			
			//Create parser from factory
			XMLReader parser = sp.getXMLReader();
			
			ParserHandler handler = new ParserHandler();
			
			//register handler with parser
			parser.setContentHandler(handler);
			
			//create an input source from the XML input strem
			InputSource source = new InputSource(in);
			
			//parsiamo il documento
			parser.parse(source);
			
			previsioni = handler.getMeteo();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		}catch(SAXException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
		}
		return previsioni;
	}
}
