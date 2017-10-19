package frontend;

import java.io.File;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javafx.scene.paint.Color;

/**
 * StyleUI.java
 * @author Yiqin Zhou
 * Read specific style requirements from XML file
 * Assumptions: valid input from XML file
 * @version 10.04.17
 */

public class StyleUI {
	
	/**
	 * return value from XML file indicating whether the grid should be visible or not
	 * @return
	 */
	
	public boolean gridVisibility() {
		Document doc;
		try {
			doc = getFile("Style.xml");
			doc.getDocumentElement().normalize();
			String message=doc.getElementsByTagName("visibility").item(0).getTextContent();
			if (message.equals("Y")){
			    return true;
		    }
			if (message.equals("N")) {
				return false;
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	/**
	 * return grid shape either be square or triangle
	 * @return
	 */
	
	public String gridShape() {
		Document doc;
		try {
			doc = getFile("Style.xml");
			doc.getDocumentElement().normalize();
			String message=doc.getElementsByTagName("shape").item(0).getTextContent();
			return message;			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * return grid edge type
	 * @return
	 */
	
	public boolean getGridEdge() {
		Document doc;
		try {
			doc = getFile("Style.xml");
			doc.getDocumentElement().normalize();
			String message=doc.getElementsByTagName("gridEdge").item(0).getTextContent();
//			return Integer.parseInt(message);
			return message.equals("Y");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
//		return 0;	
	}
	
	/**
	 * return empty cell's color specified inside XML file, translate it into color
	 * @return
	 */
		
	
	
	public Color emptyColor() {
		Document doc;
		try {
			doc = getFile("Style.xml");
			doc.getDocumentElement().normalize();
			String message=doc.getElementsByTagName("empty").item(0).getTextContent();
			if (message.equals("Water world")){
				return Color.BLUE;
			}
			if (message.equals("Space world")) {
				return Color.BLACK;
			}
			if (message.equals("Normal world")) {
				return Color.WHITE;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Document getFile(String fileName) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		return doc;
	}

}
