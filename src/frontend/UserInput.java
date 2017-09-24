package frontend;

/*
 * Author: Venkat S.
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class UserInput {
	



	  public void getSegregation() {

	    try {

		File fXmlFile = new File("Segregation.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();

		String title = doc.getElementsByTagName("name").item(0).getTextContent();
		String satisfactionPercentage = doc.getElementsByTagName("satisfactionPercentage").item(0).getTextContent();
		String cellNumberHorizontal = doc.getElementsByTagName("cellNumberHorizontal").item(0).getTextContent();
		String cellNumberVertical = doc.getElementsByTagName("cellNumberVertical").item(0).getTextContent();
		String emptyPercentage = doc.getElementsByTagName("emptyPercentage").item(0).getTextContent();
		String redToBlueRatio = doc.getElementsByTagName("redToBlueRatio").item(0).getTextContent();
		System.out.println(title);
		System.out.println(satisfactionPercentage);
		System.out.println(cellNumberHorizontal);
		System.out.println(cellNumberVertical);
		

		
		
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	  }

	

}
