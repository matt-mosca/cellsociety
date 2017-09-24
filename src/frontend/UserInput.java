package frontend;

/*
 * Author: Venkat S.
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

public class UserInput {
	  String title;
	  String cellNumberHorizontal;
	  String cellNumberVertical;
	  String emptyPercentage;
	  String redToBlueRatio;
	  String satisfactionPercentage;
	  String minBreedDaysForSharks;
	  String minBreedDaysForFish;
	  String maxStarveDaysForSharks;
	  String probCatch;
	  
	  public UserInput() {
		  
	  }
	



	  public void getSegregation() {
	
		
	    try {
	    
		Document doc = getFile("Segregation.xml");
		doc.getDocumentElement().normalize();	
		satisfactionPercentage = doc.getElementsByTagName("satisfactionPercentage").item(0).getTextContent();
		getInitialSetUp(doc);
		
			
		
	    } catch (Exception e) {
		e.printStackTrace();
	    }
		
	  }
	  
	  
	  public void getWaTor() {
		
		
	    try {
	    
		Document doc = getFile("WaTor.xml");
		doc.getDocumentElement().normalize();	
		minBreedDaysForSharks = doc.getElementsByTagName("minBreedDaysForSharks").item(0).getTextContent();
		minBreedDaysForFish = doc.getElementsByTagName("minBreedDaysForFish").item(0).getTextContent();
		maxStarveDaysForSharks = doc.getElementsByTagName("maxStarveDaysForSharks").item(0).getTextContent();
		getInitialSetUp(doc);
		
	
		
	    } catch (Exception e) {
		e.printStackTrace();
	    }
		
	  }
	  
	  public void getFire() {
			
			
		    try {
		    
			Document doc = getFile("Fire.xml");
			doc.getDocumentElement().normalize();	
		    probCatch = doc.getElementsByTagName("probCatch").item(0).getTextContent();
			getInitialSetUp(doc);
			
				
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }
			
     }
	  
	  public void getGameOfLife() {
			
			
		    try {
		    
			Document doc = getFile("GameOfLife.xml");
			doc.getDocumentElement().normalize();	
			getInitialSetUp(doc);
			
				
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }
			
   }




	public Document getFile(String fileName)
			throws ParserConfigurationException,
			SAXException, IOException {
		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		return doc;
	}




	public void getInitialSetUp(
			Document doc) {
		title = doc.getElementsByTagName("name").item(0).getTextContent();
		cellNumberHorizontal = doc.getElementsByTagName("cellNumberHorizontal").item(0).getTextContent();
		cellNumberVertical = doc.getElementsByTagName("cellNumberVertical").item(0).getTextContent();
		emptyPercentage = doc.getElementsByTagName("emptyPercentage").item(0).getTextContent();
		redToBlueRatio = doc.getElementsByTagName("redToBlueRatio").item(0).getTextContent();
	}

	

}
