package frontend;

/*
 * Authors: Venkat S., Yiqin Zhou
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
	  private double [] finalParams;
	  
	  public UserInput() {
		  
	  }
	



	  public double[] getSegregation() {
	
		
	    try {
	    
		Document doc = getFile("Segregation.xml");
		doc.getDocumentElement().normalize();
		String simulationType=doc.getElementsByTagName("name").item(0).getTextContent();
		String satisfactionPercentage = doc.getElementsByTagName("satisfactionPercentage").item(0).getTextContent();
//		finalParams = new String[SEG_ARRAY_LENGTH];
		double[] basic = new double[1];
		basic[0] = Double.parseDouble(satisfactionPercentage);
		getInitialSetUp(doc, basic);
//		makeFinalArray(fun);
		
		
			
		
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    return finalParams;
	  }
	  
	  
	  public double[] getWaTor() {
		
		
	    try {
	    
		Document doc = getFile("WaTor.xml");
		doc.getDocumentElement().normalize();	
		String minBreedDaysForSharks = doc.getElementsByTagName("minBreedDaysForSharks").item(0).getTextContent();
		String minBreedDaysForFish = doc.getElementsByTagName("minBreedDaysForFish").item(0).getTextContent();
		String maxStarveDaysForSharks = doc.getElementsByTagName("maxStarveDaysForSharks").item(0).getTextContent();
		double[] basic = new double[3];
		basic[0] = Double.parseDouble(maxStarveDaysForSharks);
		basic[1] = Double.parseDouble(minBreedDaysForSharks);
		basic[2] = Double.parseDouble(minBreedDaysForFish);
		getInitialSetUp(doc, basic);
//		makeFinalArray();
		
		
	
		
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    return finalParams;
	  }
	  
	



	public double[] getFire() {
			
			
		    try {
		    
			Document doc = getFile("Fire.xml");
			doc.getDocumentElement().normalize();	
		    String probCatch = doc.getElementsByTagName("probCatch").item(0).getTextContent();
//		    finalParams = new String[SEG_ARRAY_LENGTH];
		    double[] basic = new double[1];
		    basic[0] = Double.parseDouble(probCatch);
			getInitialSetUp(doc, basic);
//			makeFinalArray(fun);
			
			
				
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }
			return finalParams;
     }
	  
	  public double[] getGameOfLife() {
			
			
		    try {
		    
			Document doc = getFile("GameOfLife.xml");
			doc.getDocumentElement().normalize();	
			getInitialSetUp(doc, null);
//			makeFinalArray(fun);
			
				
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }
			return finalParams;
   }




	public Document getFile(String fileName) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		return doc;
	}




	public void getInitialSetUp(Document doc, double[] second) {
		double[] things = new double[4];
//		String title = doc.getElementsByTagName("name").item(0).getTextContent();
		String cellNumberHorizontal = doc.getElementsByTagName("cellNumberHorizontal").item(0).getTextContent();
		String cellNumberVertical = doc.getElementsByTagName("cellNumberVertical").item(0).getTextContent();
		String emptyPercentage = doc.getElementsByTagName("emptyPercentage").item(0).getTextContent();
		String redToBlueRatio = doc.getElementsByTagName("redToBlueRatio").item(0).getTextContent();
//		things[0] = title;
		things[0] = Double.parseDouble(cellNumberHorizontal);
		things[1] = Double.parseDouble(cellNumberVertical);
		things[2] = Double.parseDouble(emptyPercentage);
		things[3] = Double.parseDouble(redToBlueRatio);
		makeFinalArray(things, second);
//		return things;
	}
	
	  private void makeFinalArray(double[] fun, double[] second) {
		   if (second==null) {
			   finalParams = fun;
			   return;
		   }
		   double[] thing = new double[fun.length+second.length];
		   for (int i=0; i<fun.length; i++) {
			  thing[i] = fun[i];
		   }
		   for (int i=fun.length; i<thing.length; i++) {
			   thing[i]= second[i-fun.length];
		   }
		   this.finalParams = thing;
		  }
			
	

}
