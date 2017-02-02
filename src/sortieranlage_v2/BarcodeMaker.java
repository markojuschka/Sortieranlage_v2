/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortieranlage_v2;

import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import java.io.*;
/**
 * Die Klasse BarcodeMaker generiert einen Barcode, Typ128B, 
 * und stellt über Methoden das Speichern und Darstellen der Barcodes zur verfügung 
 * 
 * @author MJ
 */
public class BarcodeMaker {
    
    public Barcode barcode;
    
     /**
     * Konstruktor BarcodeMaker
     * 
     * @param text Die vollständige ID des Barcodes wird an den Konstruktor übergeben 
     */
    public BarcodeMaker(String text) {
        try {
            barcode = BarcodeFactory.createCode128B(text);
            barcode.setBarWidth(1);
        } catch (BarcodeException e) {
            // Error handling
        }
    }
    
    
    /**
     * Methode schreibt den erzeugten Barcode als jpg-Datei 
     * @param id ID des Pakets
     */
    public void writeBarcodetoFile(String id) {
        try {
            File f = new File(Definitions.OUTPUTPATH + "/" + id + ".jpg");
            // Let the barcode image handler do the hard work
            BarcodeImageHandler.saveJPEG(barcode, f);
        } catch (Exception e) {
            // Error handling here
        }
    }
    
    /**
     * Methode stellt den aktuell erzeugten Barcode im Panel dar 
     *
     */
    public void displaynextBarcode() {
        AnlageFrame.jPanel1.removeAll();
        AnlageFrame.jPanel1.add(barcode);
        AnlageFrame.jPanel1.setVisible(true);
        AnlageFrame.jPanel1.repaint();
    }
}
