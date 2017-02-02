/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortieranlage_v2;

/**
 * Die Klasse Definitions liefert unveränderliche Vorgabewerte, 
 * zum Beispiel die verschiedenen Stati des Pakets im Verlauf der Sortieranlage 
 * oder feste Angaben für Maße, Gewichte etc.
 * 
 * @author MJ
 */
public class Definitions {
    
    /**
     * Erster Status der "Sortieranlage"
     */
    public static final int BEFORESCAN = 1;
    
    /**
     * Zweiter Status der "Sortieranlage"
     */
    public static final int ATSCAN = 2;
    
    /**
     * Dritter Status der "Sortieranlage"
     */
    public static final int AFTERSCAN = 3;
    
    /**
     * Vierter Status der "Sortieranlage"
     */
    public static final int ATMEASURING = 4;
    
    /**
     * Fünfter Status der "Sortieranlage"
     */
    public static final int AFTERMEASURING = 5;
    
    /**
     * Sechster und letzter Status der "Sortieranlage"
     */
    public static final int FINALSTATE = 6;
    
    /**
     * Zum zeitlichen "Auseinanderziehen" der Pakete auf dem Förderband
     */
    public static final int TIMEBETWEENPACKAGES = 1500;  // in milliseconds
    
    /**
     * Volumen des Rollcontainers in Kubikzentimeter: 1.800.000 
     */
    public static final long ROLLCONTAINERVOLUMEN = 1800000;  // in Kubikzentimeter
    
    /**
     * Überlänge, d.h. die max. zulässige Länge einer Paketkante ist 60cm
     */
    public static final int UEBERLAENGE = 60;   // ist eine Kante d. Pakets länger als 60cm
    
    /**
     * Übergewicht, d.h. das max. zulässige Gewicht eines Pakets ist 3000g
     */
    public static final int UEBERGEWICHT = 3000;   // ist das Gewicht grösser als 3kg
    
    /**
     * relativer Pfad für Ausgaben  
     */
    public static final String OUTPUTPATH = "output";
    
    /**
     * 1.Teil der HTML-Ausgabe
     */
    public static final String HTMLHEADER = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n" + 
                                             "<html>\n" +
                                             "<head>\n" +
                                             "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">\n" +
                                             "<title></title>\n" +
                                             "</head>\n";
    
    /**
     * 2.Teil der HTML-Ausgabe
     */
    public static final String HTMLBODY1 = "<body>\n" + 
            "<table width=\"650\" cellpadding=\"4\" cellspacing=\"0\">\n" +
"<col width=\"152\">\n" +
"<col width=\"178\">\n" +
"<col width=\"134\">\n" +
"<col width=\"152\">\n" +
"<tr valign=\"top\">\n" +
"	<td width=\"152\">\n" +
"		<p align=\"center\"><b>Paket</b></p>\n" +
"		<p align=\"center\"><br></p>\n" +
"	</td>\n" +
"	<td width=\"178\" style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">\n" +
"		<p align=\"center\"><b>Barcode</b></p>\n" +
"	</td>\n" +
"	<td width=\"134\" style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">\n" +
"		<p align=\"center\"><b>Status</b></p>\n" +
"	</td>\n" +
"	<td width=\"152\" style=\"border: 1px solid #000000; padding: 0.1cm\">\n" +
"		<p align=\"center\"><b>Kommentar</b></p>\n" +
"	</td>\n" +
"</tr>";
    
    
}
