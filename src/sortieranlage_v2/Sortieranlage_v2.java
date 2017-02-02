/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortieranlage_v2;

import java.util.ArrayList;
import java.io.*;

/**
 * Die Klasse Sortieranlage_v2 stellt mit ihren Methoden 
 * Abläufe und Aktionen während des Sortiervorgangs zur Verfügung: 
 * Start, Scannen, Wiegen etc.
 * 
 * @author user2
 */
public class Sortieranlage_v2 {

    static Rollcontainer[] rollcontainer = new Rollcontainer[10];
    static ArrayList <Paket> packages = new ArrayList<Paket>();
    static int stretchfactor = 0;
    
    /**
     * Methode initiert den Start der Sortieranlage, holt die Paketdaten aus der XML-Datei "packages.xml" 
     * und erstellt die Rollcontainer-Objekte
     */
    public static void start() {
        
        // erstelle subdirectory "output" für Barcodes & Frachtbriefe
	boolean success = (new File(Definitions.OUTPUTPATH)).mkdir();
	if (success) {
		System.out.println("Directory: " + Definitions.OUTPUTPATH + " created");
	}	
        
        // erstelle die Liste mit den eingegangenen Paketen
        PackageXMLParser parser = new PackageXMLParser();
        packages = parser.parse("packages.xml");
        
        for (Paket x: packages) {
            x.tstamp = x.tstamp + stretchfactor*Definitions.TIMEBETWEENPACKAGES;
            System.out.println("Paket mit der ID: " + x.id);
            System.out.println("Paket mit Dimensionen: " + x.dimensions);
            System.out.println("Paket mit dem Gewicht: " + x.weight);
            System.out.println("Paket mit Zeitstempel: " + x.tstamp);
            stretchfactor++;         
        }       
        
        // Gib die Anzahl der eingelesenen Pakete aus
        System.out.println("Anzahl Pakete. " + Paket.anzahl);
        
        
        // Erstelle die Rollcontainer
        // Nummer 1-9 (entspricht hier Index 0...8) sind die Regionen (Leitzonen)
        
        for (int x=0; x<10; x++) {
            rollcontainer[x] = new Rollcontainer(""+x);
        }
        
    }
    
    
    /**
     * Methode sortiert das Paket p in den entsprechenden Rollcontainer der Region 
     * bzw. nach Vorgaben der Grenzwerte in {@link Definitions} in den Rollcontainer für manuelle Bearbeitung
     * @param p Paket
     * @param id_number Nummer (Leitzahl) der Region
     * @param dims Laenge, Breite, Hoehe, Gewicht
     * @return Nummer der Region und Füllstand des Rollcontainers (%) in einem int-Array
     */
    public static int[] sortierePaket(Paket p, int id_number, int[] dims){
        int[] region_Fuellstand = {0,0}; // 1. Wert ist der Regionsindex, 2. Wert der Fuellstand in Prozent
        
        // bestimme aufgrund der nach 'ID' stehenden Ziffer die Region, genauer: Listenindex d. Region!!!
        region_Fuellstand[0] = id_number-1;
        int region = region_Fuellstand[0];  // Hilfsvariable f. Rollcontainer (zwecks einfacherer Zuordnung)
        
        // bestimme das Volumen von Paket p
        long volumen = dims[0]*dims[1]*dims[2];
        //System.out.println("Länge: "+dims[0]+"  Breite: "+dims[1]+"  Höhe: "+dims[2]+"  Volumen: "+volumen);
       
        // eventuell Aussortierung wegen Überlänge einer Kante oder Gewicht zu hoch
        if ((dims[0]>Definitions.UEBERLAENGE) || (dims[1]>Definitions.UEBERLAENGE) || (dims[2]>Definitions.UEBERLAENGE) || (dims[3]>Definitions.UEBERGEWICHT)) {
            // in den 10. Rollcontainer
            region_Fuellstand[0] = 9;
            rollcontainer[9].addPackage(p);
        }
        
        // Prozentanteil des Volumens (Paket p) am gesamten Rollcontainer
        int prozent = (int)Math.round((volumen*100/Definitions.ROLLCONTAINERVOLUMEN));
        
        // Rollcontainer mit Paket befüllen und den Fuellstand des besagten Rollcontainers aktualisieren 
        rollcontainer[region].addPackage(p);
        rollcontainer[region].fuellstand = rollcontainer[region].fuellstand + prozent;
        
        // Container bereits voll (>85%) ?
        if (rollcontainer[region].fuellstand > 85) {
            // TODO: Ladeliste drucken
            System.out.println("!!!LADELISTE WIRD GEDRUCKT!!!");
            rollcontainer[region].toprint = true;
            rollcontainer[region].textFrachtbrief = "Frachtbrief für Region " + id_number + "\n";
            rollcontainer[region].printFrachtbrief(id_number);
            /* derzeit herausgenommen, um die pdfbox Bibliothek mit ca. 10MB zu sparen
            rollcontainer[region].printFrachtbriefasPDF(id_number);
            */
            rollcontainer[region].empty_all();
            rollcontainer[region].fuellstand = 0;
        }
        System.out.println("Region "+id_number+" - Fuellstand (in %): " + rollcontainer[region].fuellstand);
        region_Fuellstand[1] = rollcontainer[region].fuellstand;    
        // beide Werte zurückgeben, Index0: Region und Index1: neuer Füllstand in %
        return region_Fuellstand;
    }
    
    /**
     * Methode ermittelt die Zielregion des Pakets, indem die 
     * ID "gescannt" und damit die Postleitzahl-Region ermittelt wird
     * @param p Paket
     * @return Nummer der Region
     */
    // return digit after "ID" as integer
    public static int scan(Paket p) {
        return Integer.parseInt(p.id.substring(2,3));
    }
    
    /**
     * Methode ermittelt die Ausmasse und das Gewicht des Pakets 
     * @param p Paket
     * @return Länge, Breite, Hoehe und Gewicht des Pakets in einem int-Array
     */
    public static int[] measuring(Paket p) {
        int[] dimensions = {0,0,0,0};
        // der Reihe nach: Laenge, Breite, Hoehe...
        dimensions[0] = Integer.parseInt(p.dimensions.substring(0, p.dimensions.indexOf("x")));
	dimensions[1] = Integer.parseInt(p.dimensions.substring(p.dimensions.indexOf("x")+1,p.dimensions.lastIndexOf("x")));
	dimensions[2] = Integer.parseInt(p.dimensions.substring(p.dimensions.lastIndexOf("x")+1));
        
        dimensions[3] = p.weight;
        return dimensions;
    }
     
}
