package frontend;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import backend.Cell;

public class UserSaveSimulation{

	
	public int[][] getBack(){
		  int array[][]=null;
		  try {
			   
			    Document doc = getFile("SavedData.xml");
				doc.getDocumentElement().normalize();
				String title = doc.getElementsByTagName("title").item(0).getTextContent();
			    String maxRow = doc.getElementsByTagName("cellNumberHorizontal").item(0).getTextContent();
			    int maxRowNumber=Integer.parseInt(maxRow);
			    String maxColumn=doc.getElementsByTagName("cellNumberVertical").item(0).getTextContent();
			    int maxColumnNumber=Integer.parseInt(maxColumn);
			    String cells=doc.getElementsByTagName("config").item(0).getTextContent();
			    array = new int[maxRowNumber][maxColumnNumber];
                for  (int i=0;i<maxRowNumber;i++) {
                	     for (int j=0;j<maxColumnNumber;j++) {
                	    	     char t=cells.charAt(i*maxRowNumber+j);
                	    	     array[i][j]=Character.getNumericValue(t) ;
                	    	    
                	     }
                	     
                }
			
				
					
				
			    } catch (Exception e) {
				e.printStackTrace();
			    }
//				return finalParams;
	     
		
		return array;
	}

    public Document getFile(String fileName) throws ParserConfigurationException, SAXException, IOException {
	    File fXmlFile = new File(fileName);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    return doc;
}
	
	
	

	public void save(Cell[][]needSave,String type) {
	
		try {
			
			
			
			String filepath="SavedData.xml";
			
			Document doc=getFile(filepath);

	
            //set type of simulation
			Node title = doc.getElementsByTagName("title").item(0);
			title.setTextContent(type);
			
			//set config
			Node cellNumberHorizontal = doc.getElementsByTagName("cellNumberHorizontal").item(0);
			int i=needSave[0].length;
			int j=needSave.length;
			cellNumberHorizontal.setTextContent(Integer.toString(i));
			
			Node cellNumberVertical = doc.getElementsByTagName("cellNumberVertical").item(0);
			cellNumberVertical.setTextContent(Integer.toString(j));
			
			Node config = doc.getElementsByTagName("config").item(0);
			
			String configCells="";
			for (int rowNumber = 0; rowNumber < needSave[0].length; rowNumber++) {
				for (int columnNumber = 0; columnNumber < needSave.length; columnNumber++) {
					configCells=configCells+needSave[rowNumber][columnNumber].getState();
				}
			}
					

			config.setTextContent(configCells);

			
			
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

			System.out.println("Done");

		   } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		   } catch (TransformerException tfe) {
			tfe.printStackTrace();
		   } catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
		}
		
		
	
		
		
	public static void main(String argv[]) {
		UserSaveSimulation test=new UserSaveSimulation();
		test.getBack();
		
	}
	
	
}
