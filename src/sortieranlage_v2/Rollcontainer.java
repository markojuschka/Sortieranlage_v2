/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortieranlage_v2;


import java.util.ArrayList;
import java.io.*;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
/* derzeit herausgenommen, um die pdfbox Bibliothek mit ca. 10MB zu sparen
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Desktop;
*/

/**
 * Die Klasse Rollcontainer stellt ein Objekt Rollcontainer zur Verfügung, 
 * in das Pakete einsortiert werden. Ist der Füllstand von 85% erreicht, 
 * wird ein Frachtbrief gedruckt (Methode printFrachtbrief) und d. Rollcontainer wird geleert
 * @author MJ
 */
public class Rollcontainer {
    
    public String name;
    public int fuellstand;
    public ArrayList <Paket> packages_in_Container = new ArrayList<Paket>();
    public boolean toprint = false;
    public String textFrachtbrief;
    
    /**
     * Konstruktor des Rollcontainers
     * 
     * @param name Die Nummer des Rollcontainers wird als String übergeben 
     */
    public Rollcontainer(String name){
        this.name=name;  // erhaelt die Nummer der Region (bei 0: Aussortiercontainer)
        this.fuellstand=0;
    }

    /**
     * Methode fügt dem Rollcontainer ein Paket hinzu
     * @param p Paket
     */
    public void addPackage(Paket p) {
       packages_in_Container.add(p); 
    }
    
    /**
     * Methode leert den Rollcontainer
     */
    public void empty_all() {
       packages_in_Container.clear();
    }
    
    /**
     * Methode schreibt den Frachtbrief in eine HTML-Datei
     * @param region Nummer der Region
     */
    public void printFrachtbrief(int region) {
        // einen Zeitstempel setzen
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        String zeitSuffix = simpleDateFormat.format(new Date());
        
        String html = Definitions.HTMLHEADER + Definitions.HTMLBODY1;
         
        
        for (Paket x: packages_in_Container) {
            textFrachtbrief = textFrachtbrief + "Paket " + x.id + "\n";
            
            // setze die HTML-Datei zusammen
            // 1. Spalte: Paket
            html = html + "<tr valign=\"top\">\n" + 
                    "<td width=\"152\" style=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">\n" + 
			"<p align=\"center\">Paket"+ x.id + "</p>\n" + 
                        "</td>\n";
            // 2. Spalte: Barcode
            html = html +  
                    "<td width=\"178\" style=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">\n" + 
			"<p align=\"center\"><img src=\""+ x.id + ".jpg\" alt=\"Barcode\"></p>\n" + 
                        "</td>\n";
            // 3. Spalte: Status
            html = html + 
                    "<td width=\"134\" style=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">\n" + 
			"<p align=\"center\">Sortieranlage verlassen</p>\n" + 
                        "</td>\n";
            // 4. Spalte: Kommentar
            html = html +  
                    "<td width=\"152\" style=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">\n" + 
			"<p align=\"center\"><br></p>\n" + 
                        "</td>\n" + 
                        "</tr>\n";
        } // end of for
        
        // Abschluss der HTML-Datei
        html = html + "</table>\n" + "<br><b>Frachtbrief für Region " + region + ", erstellt: " + zeitSuffix + "</b></br></body>\n" + "</html>\n";
        
        try {
                /**
                 * ursprünglich wurde ein Textfile erzeugt
                 * 
                 * schreibe den Textinhalt des Frachtbriefs in eine Textdatei in den Unterordner "output"
		 * String content = textFrachtbrief;
                 * File file = new File(Definitions.OUTPUTPATH + "/Frachtbrief_"+region+"_" + zeitSuffix + ".txt");
                */
            
                File file = new File(Definitions.OUTPUTPATH + "/Frachtbrief_"+region+"_" + zeitSuffix + ".html");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(html);
		bw.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
        
        // Submenü-Eintrag für Frachtbrief
        AnlageFrame.addFrachtbriefMenuentry("Frachtbrief_"+region+"_" + zeitSuffix);
    }

    /**
     * Methode schreibt den Frachtbrief in eine PDF-Datei
     * @param region Nummer der Region
     */
    /* derzeit herausgenommen, um die pdfbox Bibliothek mit ca. 10MB zu sparen
    public void printFrachtbriefasPDF(int region) {
    
        // einen Zeitstempel setzen
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        String zeitSuffix = simpleDateFormat.format(new Date());
        
        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage( page );

        // Create a new font object selecting one of the PDF base fonts
        PDFont font = PDType1Font.HELVETICA_BOLD;

        try {
            // Start a new content stream which will "hold" the to be created content
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont( font, 12 );
            contentStream.moveTextPositionByAmount(80, 700);
            contentStream.drawString("Frachtbrief für Region " + region + ", erstellt: " + zeitSuffix);
            contentStream.endText();

            // Make sure that the content stream is closed:
            contentStream.close();
            try {
                // Save the results and ensure that the document is properly closed:
                document.save(Definitions.OUTPUTPATH + "/Frachtbrief_"+region+"_" + zeitSuffix + ".pdf");
                document.close();
            } catch (COSVisitorException ce) {  }
        } catch (IOException ie) {  }
        
    }  // end of method "printFrachtbriefasPDF"
    */
    
    
}
