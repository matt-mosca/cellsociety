package frontend;



import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;


/* UserInput.java
 * @author Venkat Subramaniam & Yiqin Zhou
 * Class that parses and interprets xml files so that parameters can be set for various simulations.
 * @version 10.04.17
 */
public class UserInput {
	private static final String GAMEOFLIFETITLE = "Game of Life";
	private static final String SEGREGATIONTITLE = "Segregation";
	private static final String FIRETITLE = "Fire";
	private static final String WATORTITLE = "WaTor";
    private static final String RPSTITLE="Rock Paper Scissors";
	
	  private Document doc;
	  private String simulationType;
	
	  private int type;

	  private double [] finalParams;
	  
	  /*
	   * Constructor for this class. It just initializes this object so that other methods can be called.
	   */
	  public UserInput() {
		  
	  }
	
	  
	  /*
	   * This method returns the correct finalParams instance variable of this class depending on the 
	   * kind of simulation that needs to be created.
	   * @param s
	   */
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
		if (s.equals(RPSTITLE)) {
			getRPS();
		}
		return finalParams;
	  }

	  /*
	   * This method loads in the data related to the Segregation simulation.
	   */
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
	  }


	  /*
	   * This method loads in the data from the xml file related to the Segregation simulation.
	   */
	private void segregationSetUpWithoutType() {
		String satisfactionPercentage = doc.getElementsByTagName("satisfactionPercentage").item(0).getTextContent();
		double[] basic = new double[1];
		basic[0] = Double.parseDouble(satisfactionPercentage);
		getInitialSetUp(doc, basic);
	}
	   
	  /*
	   * This method loads in the data  related to the WaTor simulation.
	   */
	  private void getWaTor() {
	    try { 
		doc = getFile("WaTor.xml");
		doc.getDocumentElement().normalize();
		simulationType=doc.getElementsByTagName("name").item(0).getTextContent();
		waTorSetUpWithoutType();
	    } catch (Exception e) {
		    if (simulationType==null) {
		    	   showError("Simulation name is not given, will use the default value");
			   simulationType="WaTor";
		    	   waTorSetUpWithoutType();	    	
		    }
	    }
	  }

	  /*
	   * This method loads in the data from the xml file related to the WaTor simulation.
	   */
	private void waTorSetUpWithoutType() {
		String minBreedDaysForSharks = doc.getElementsByTagName("minBreedDaysForSharks").item(0).getTextContent();
		String minBreedDaysForFish = doc.getElementsByTagName("minBreedDaysForFish").item(0).getTextContent();
		String maxStarveDaysForSharks = doc.getElementsByTagName("maxStarveDaysForSharks").item(0).getTextContent();
		double[] basic = new double[3];
		basic[0] = Double.parseDouble(maxStarveDaysForSharks);
		basic[1] = Double.parseDouble(minBreedDaysForSharks);
		basic[2] = Double.parseDouble(minBreedDaysForFish);
		getInitialSetUp(doc, basic);
	}
	
	 /*
	  * This method loads in the data related to the Fire simulation.
	  */
	private void getFire() {	
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
     }

	 /*
	  * This method loads in the data from the xml file related to the Fire simulation.
	  */
	private void fireSetUpWithoutType() {
		String probCatch = doc.getElementsByTagName("probCatch").item(0).getTextContent();
		double[] basic = new double[1];
		basic[0] = Double.parseDouble(probCatch);
		getInitialSetUp(doc, basic);
	}
	  

	 /*
	  * This method loads in the data related to the Game of Life simulation.
	  */
	  private void getGameOfLife() {
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
	  
	  /*
		  * This method loads in the data related to the RPS simulation.
		  */
	  private void getRPS() {
		  try {	 
			  doc = getFile("RPS.xml");
			  doc.getDocumentElement().normalize();
			  simulationType=doc.getElementsByTagName("name").item(0).getTextContent();
			  getInitialSetUp(doc, null);
		  } catch(Exception e) {
			  if (simulationType==null) {
				  showError("Simulation name is not given, will use the default value");
				  simulationType = "Rock Paper Scissors";
				  getInitialSetUp(doc,null);
			  }
		  }
	  }


	  
	  
/*
 * This method reads and parses through a particular xml file.
 * @param fileName
 */
	private Document getFile(String fileName) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		return doc;
	}



	/*
	 * This method creates a basic array which contains information loaded from the xml file.
	 * @param doc
	 * @param second
	 */
	private void getInitialSetUp(Document doc, double[] second) {
		double[] things = new double[4];
		String cellNumberHorizontal = doc.getElementsByTagName("cellNumberHorizontal").item(0).getTextContent();
		String cellNumberVertical = doc.getElementsByTagName("cellNumberVertical").item(0).getTextContent();
		String emptyPercentage = doc.getElementsByTagName("emptyPercentage").item(0).getTextContent();
		String redToBlueRatio = doc.getElementsByTagName("redToBlueRatio").item(0).getTextContent();
		String typeS = doc.getElementsByTagName("type").item(0).getTextContent();
		type=Integer.parseInt(typeS);
		things[0] = Double.parseDouble(cellNumberHorizontal);
		things[1] = Double.parseDouble(cellNumberVertical);
		things[2] = Double.parseDouble(emptyPercentage);
		things[3] = Double.parseDouble(redToBlueRatio);
		
		makeFinalArray(things, second);
	}
	
	/*
	 * This method sets the instance variable finalParams to the value that the particular simulation requires.
	 * @param fun
	 * @param second
	 */
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


	  /*
	   * This method returns the type instance variable of this class.
	   */
	public int getType() {
		return type;
	}


	/*
	 * This method sets the type instance variable of this class.
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/*
	 * This method displays an error message should there be an incorrect input.
	 */
	private void showError (String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

			
	

}
