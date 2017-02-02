/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortieranlage_v2;

/**
 * Die Klasse Paket stellt das Paketobjekt zur Verfügung
 * 
 * @author MJ
 */
public class Paket {
    
    public static int anzahl = 0;
    public String id;
    public String dimensions;
    public int weight;
    public int id_number;
    public int[] dims = {0,0,0,0};   //  Laenge, Breite, Höhe in cm und Gewicht in Gramm
    public long tstamp;
    public boolean passedscan;
    public int status = 0;

    
    /**
     * Konstruktor des Pakets. 
     * Aufgerufen wird der Konstruktor in {@link PackageXMLParser} 
     * 
     * @param id komplette, eingelesene ID des Pakets
     * @param dimensions Laenge, Hoehe, Breite in der Form "AxBxC"
     * @param weight Gewicht 
     * @param tstamp Zeitstempel in msec., beginnt beim Einlesen der Pakete mit 0
     */
    public Paket(String id, String dimensions, int weight, long tstamp) {
        anzahl++;
        this.id=id;
        this.dimensions=dimensions;
        this.weight=weight;
        this.tstamp=tstamp;
        this.passedscan=false;
        this.status=Definitions.BEFORESCAN;
    }


}
