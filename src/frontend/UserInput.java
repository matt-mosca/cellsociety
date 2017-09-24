package frontend;

/*
 * Author: Venkat S., Yiqin Zhou
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

public class UserInput {
//	private static final int NORMAL_ARRAY_LENGTH = 5;
//	private static final int WATOR_ARRAY_LENGTH = 8;
//	private static final int SEG_ARRAY_LENGTH = 6;
//	  private String title;
//	  private String cellNumberHorizontal;
//	  private String cellNumberVertical;
//	  private String emptyPercentage;
//	  private String redToBlueRatio;
//	  private String satisfactionPercentage;
//	  private String minBreedDaysForSharks;
//	  private String minBreedDaysForFish;
//	  private String maxStarveDaysForSharks;
//	  private String probCatch;
	  private String[] finalParams;
	  
	  public UserInput() {
		  
	  }
	



	  public void getSegregation() {
	
		
	    try {
	    
		Document doc = getFile("Segregation.xml");
		doc.getDocumentElement().normalize();	
		String satisfactionPercentage = doc.getElementsByTagName("satisfactionPercentage").item(0).getTextContent();
//		finalParams = new String[SEG_ARRAY_LENGTH];
		String[] basic = new String[1];
		basic[0] = satisfactionPercentage;
		getInitialSetUp(doc, basic);
//		makeFinalArray(fun);
		
			
		
	    } catch (Exception e) {
		e.printStackTrace();
	    }
		
	  }
	  
	  
	  public void getWaTor() {
		
		
	    try {
	    
		Document doc = getFile("WaTor.xml");
		doc.getDocumentElement().normalize();	
		String minBreedDaysForSharks = doc.getElementsByTagName("minBreedDaysForSharks").item(0).getTextContent();
		String minBreedDaysForFish = doc.getElementsByTagName("minBreedDaysForFish").item(0).getTextContent();
		String maxStarveDaysForSharks = doc.getElementsByTagName("maxStarveDaysForSharks").item(0).getTextContent();
//		finalParams = new String[WATOR_ARRAY_LENGTH];
		String[] basic = new String[3];
		basic[0] = minBreedDaysForSharks;
		basic[1] = minBreedDaysForFish;
		basic[2] = maxStarveDaysForSharks;
		getInitialSetUp(doc, basic);
//		makeFinalArray();
		
	
		
	    } catch (Exception e) {
		e.printStackTrace();
	    }
		
	  }
	  
	



	public void getFire() {
			
			
		    try {
		    
			Document doc = getFile("Fire.xml");
			doc.getDocumentElement().normalize();	
		    String probCatch = doc.getElementsByTagName("probCatch").item(0).getTextContent();
//		    finalParams = new String[SEG_ARRAY_LENGTH];
		    String[] basic = new String[0];
		    basic[0] = probCatch;
			getInitialSetUp(doc, basic);
//			makeFinalArray(fun);
			
			
				
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }
			
     }
	  
	  public void getGameOfLife() {
			
			
		    try {
		    
			Document doc = getFile("GameOfLife.xml");
			doc.getDocumentElement().normalize();	
			getInitialSetUp(doc, null);
//			makeFinalArray(fun);
			
				
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }
			
   }




	public Document getFile(String fileName) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		return doc;
	}




	public void getInitialSetUp(Document doc, String[] second) {
		String[] things = new String[5];
		String title = doc.getElementsByTagName("name").item(0).getTextContent();
		String cellNumberHorizontal = doc.getElementsByTagName("cellNumberHorizontal").item(0).getTextContent();
		String cellNumberVertical = doc.getElementsByTagName("cellNumberVertical").item(0).getTextContent();
		String emptyPercentage = doc.getElementsByTagName("emptyPercentage").item(0).getTextContent();
		String redToBlueRatio = doc.getElementsByTagName("redToBlueRatio").item(0).getTextContent();
		things[0] = title;
		things[1] = cellNumberHorizontal;
		things[2] = cellNumberVertical;
		things[3] = emptyPercentage;
		things[4] = redToBlueRatio;
		makeFinalArray(things, second);
//		return things;
	}
	
	  private void makeFinalArray(String[] fun, String[] second) {
		   if (second==null) {
			   finalParams = fun;
			   return;
		   }
		   String[] thing = new String[fun.length+second.length];
		   for (int i=0; i<fun.length; i++) {
			  thing[i] = fun[i];
		   }
		   for (int i=fun.length; i<finalParams.length; i++) {
			   thing[i]= second[i];
		   }
		   this.finalParams = thing;
		  }
			
	

}
