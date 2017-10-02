package frontend;

/*
 * Authors: Venkat S., Yiqin Zhou
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;

public class UserInput {
	private static final String GAMEOFLIFETITLE = "Game of Life";
	private static final String SEGREGATIONTITLE = "Segregation";
	private static final String FIRETITLE = "Fire";
	private static final String WATORTITLE = "WaTor";
	
	  private Document doc;
	  private String simulationType;
	
	  private int type;

	  private double [] finalParams;
	  
	  public UserInput() {
		  
	  }
	
	  
	  public double[] getArray(String s) {
		if(s.equals(WATORTITLE)) {
			getWaTor();
		}
		if(s.equals(FIRETITLE)) {
			getFire();
		}
		if(s.equals(SEGREGATIONTITLE)) {
			getSegregation();
		}
		if(s.equals(GAMEOFLIFETITLE)) {
			getGameOfLife();
		}
		return finalParams;
	  }

	  private void getSegregation() {
	
		
	    try {
	    

		doc = getFile("Segregation.xml");
	
		doc.getDocumentElement().normalize();
		simulationType=doc.getElementsByTagName("name").item(0).getTextContent();
		
		segregationSetUpWithoutType();

		
		
			
		
	    } catch (Exception e) {
	    	    if (simulationType==null) {
		    showError("Simulation name is not given, will use the default value");
			simulationType="Segregation";
			segregationSetUpWithoutType();
	    	    }
			
		
	    }
//	    return finalParams;
	  }


	public void segregationSetUpWithoutType() {
		
		
		String satisfactionPercentage = doc.getElementsByTagName("satisfactionPercentage").item(0).getTextContent();

		double[] basic = new double[1];
		basic[0] = Double.parseDouble(satisfactionPercentage);
		getInitialSetUp(doc, basic);
	}
	  
	  
	  private void getWaTor() {
		
		
	    try {
	    
		doc = getFile("WaTor.xml");
		doc.getDocumentElement().normalize();
		simulationType=doc.getElementsByTagName("name").item(0).getTextContent();
		waTorSetUpWithoutType();
//		makeFinalArray();
	    } catch (Exception e) {
		    if (simulationType==null) {
		    	   showError("Simulation name is not given, will use the default value");
			   simulationType="WaTor";
		    	   waTorSetUpWithoutType();
		    	
		    }
	    }
//	    return finalParams;
	  }


	public void waTorSetUpWithoutType() {
		String minBreedDaysForSharks = doc.getElementsByTagName("minBreedDaysForSharks").item(0).getTextContent();
		String minBreedDaysForFish = doc.getElementsByTagName("minBreedDaysForFish").item(0).getTextContent();
		String maxStarveDaysForSharks = doc.getElementsByTagName("maxStarveDaysForSharks").item(0).getTextContent();
		double[] basic = new double[3];
		basic[0] = Double.parseDouble(maxStarveDaysForSharks);
		basic[1] = Double.parseDouble(minBreedDaysForSharks);
		basic[2] = Double.parseDouble(minBreedDaysForFish);
		getInitialSetUp(doc, basic);
	}
	  
	



	public void getFire() {
			
			
		    try {
		    
			doc = getFile("Fire.xml");
			doc.getDocumentElement().normalize();
			simulationType=doc.getElementsByTagName("name").item(0).getTextContent();
		    fireSetUpWithoutType();

			
			
				
			
		    } catch (Exception e) {
		    	if (simulationType==null) {
			    	   showError("Simulation name is not given, will use the default value");
				   simulationType="Fire";
			    	   fireSetUpWithoutType();
			    	
			    }
		    }
//			return finalParams;
     }


	public void fireSetUpWithoutType() {
		String probCatch = doc.getElementsByTagName("probCatch").item(0).getTextContent();

		double[] basic = new double[1];
		basic[0] = Double.parseDouble(probCatch);
		getInitialSetUp(doc, basic);
	}
	  
	  public void getGameOfLife() {
			
			
		    try {
		    
			doc = getFile("GameOfLife.xml");
			doc.getDocumentElement().normalize();
			simulationType=doc.getElementsByTagName("name").item(0).getTextContent();
			getInitialSetUp(doc, null);
			
		    } catch (Exception e) {
		    	if (simulationType==null) {
			    	   showError("Simulation name is not given, will use the default value");
				   simulationType="Game Of Life";
				   getInitialSetUp(doc, null);
			    	
			    }
		    }

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
		String typeS = doc.getElementsByTagName("type").item(0).getTextContent();
		type=Integer.parseInt(typeS);
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


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}
	
	public void showError (String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

			
	

}
