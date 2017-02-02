/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortieranlage_v2;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.swing.Timer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Die Klasse PackageXMLParser bildet die Schnittstelle 
 * zwischen dem Datenmodell und dem "View" der Sortieranlage 
 * 
 * @author MJ
 */

public class PackageXMLParser {

    ArrayList <Paket> diePakete;
    static long tstampcounter = 0;
    
    /**
     * Konstruktor des PackageXMLParsers
     */
    public PackageXMLParser() {
        
        ActionListener aListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {  
                tstampcounter++;
            }
        };
        
        Timer timer4stamp = new Timer(1, aListener);
        timer4stamp.addActionListener(aListener);
        
        timer4stamp.start();
        
        
    }
    
    /**
     * Methode liest die XML-Datei aus und gibt alle Pakete als ArrayList zurück
     * @param filename Dateiname "packages.xml"
     * @return Pakete als ArrayList 
     */
    public ArrayList parse(String filename) {
        diePakete = new ArrayList<Paket>();
        String id;
        String dimensions;
        int weight;
        try {
            // Parse the XML file "packages.xml"
            File packages = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(packages);
            doc.getDocumentElement().normalize();

            System.out.println("root of xml file " + doc.getDocumentElement().getNodeName());
            NodeList nodes = doc.getElementsByTagName("package");
            System.out.println("==========================");

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    id = getValue("id", element);
                    dimensions = getValue("dimensions", element);
                    weight = Integer.parseInt(getValue("weight", element));
                    diePakete.add(new Paket(id, dimensions, weight, tstampcounter));
                }
                Thread.sleep(50);
            }
        } catch (Exception ex) {
                ex.printStackTrace();
          }
        return diePakete;
    }

    
     /**
     * Methode ermittelt im XML-Tree die Nodes und gibt deren Wert / Inhalt zurück
     * @param tag
     * @param element 
     * @return Inhalt des Knotens (Node)
     */
    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }

}
