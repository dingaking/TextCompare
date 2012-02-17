package tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 
 * @author menzel
 *
 */
public class Profil {

	private CopyOnWriteArrayList<Ngramm> liste = new CopyOnWriteArrayList<Ngramm>();
	private CopyOnWriteArrayList<Ngramm> listeH = new CopyOnWriteArrayList<Ngramm>();
	private String name = null;

	/**
	 * Konstruktor einer NgrammListe aus einem Text
	 * @param link zur datei
	 * @param n laenge der ngramme
	 * @param Restriktionsenzym
	 */
	public Profil(String link, int n, String Restriktionsenzym){

		if(link!= null){


			String text = leseEin(link);

			if(Restriktionsenzym == null){
				teileAuf(text, n);

			}
			else {
				display.Con.testAussageLate("Genetischer Vergleich initialisiert");
				teileAuf(text, n, Restriktionsenzym);
			}

			bestimmeHaeufigkeiten();
			sortiereNachHaeufigkeit();
			name = frageNachName();
			lagereListeAus(name);

		}
	}// Ende Konstruktor

	/**
	 * Liest einen Text ein
	 * @param link Ort des Texts
	 * @return String des eingelesenen Textes
	 * @throws IOException 
	 */
	private String leseEin(String link){

		String text = "";
		String line;

		BufferedReader dateiStream;
		try {

			dateiStream = new BufferedReader(new FileReader(link));

			while ((line = dateiStream.readLine()) != null)  {			
				text += line;
			}

			display.Con.testAussage(text);
			dateiStream.close();
		} catch (FileNotFoundException e) {

			System.err.println("\nDatei gibt es nicht, Name wird als Text behandelt\n");
			return link;

		} catch (IOException e) {

			System.err.println("IO - Error: ");
			e.printStackTrace();
		} 		
		return text;

	}

	/**
	 * teilt einen String in N-Gramme auf erzeugt Objekte von jedem N-Gramm mit der Anzahl 1
	 * @param text String der aufgeteilt werden soll
	 * @param n - laenge der N-Gramme
	 * @param restriktionsenzym - definiert die stelle an welcher geschnitten werden soll
	 * @post erzeugt ArrayList mit Objekten der N-Gramme
	 */
	private void teileAuf(String text, int n, String restriktionsenzym) {

		Restriktionsenzym cut = new Restriktionsenzym(restriktionsenzym, null);
		String[] parts = new String[text.length()]; //Problem: richtige Groesse vorher bestimmen, oder erweiterbar machen 

		//TODO: Text schneiden um Gewschwindikeit zu erhoehen, pruefen das dadurch keine sequenz zerstoert wird!

		System.out.println(text);
	
		parts = text.split(cut.getErkennungssequenz()); 
		
		String preCut = cut.getErkennungssequenz().substring(0, cut.getSchnittstelle()); //Durch Split entfernten String definieren
		String postCut  = cut.getErkennungssequenz().substring(cut.getSchnittstelle(), cut.getErkennungssequenz().length()); //Durch Split entfernten String definieren
	
		System.out.println(preCut + "...." + postCut);
		
		for(String elem: parts){
			Ngramm random = new Ngramm(preCut + elem + postCut, 1);
			liste.add(random); //gefundenes zur Liste hinzufuegen
		}
	}

/**
 * teilt einen String in N-Gramme auf erzeugt Objekte von jedem N-Gramm mit der Anzahl 1
 * @param text String der aufgeteilt werden soll
 * @param n - laenge der N-Gramme
 * @post erzeugt ArrayList mit Ngramm-Objekten der N-Gramme
 */
private void teileAuf(String text, int n) {

	for(int k = 0; k <n; k++){ // Ngramme um eine Stelle verschieben:
		int j = n;

		text = "~"  + text ; //Verschiebung durch Zusatz von einem Zeichen pro Durchgang
		for(int i = 0; i<text.length()-(text.length()%n);i+=n){

			display.Con.testAussage((String) (text.subSequence(i, j)) + "  pos: " + i + " "  + j );

			Ngramm random = new Ngramm(text.subSequence(i, j), 1);

			liste.add(random);
			j+=n;
		}
	}
}


/**
 * schickt ueber das gui eine Anfrage nach dem Namen des Textes
 * @return name der datei
 */
private String frageNachName() {

	return display.Con.Frage("\nNennen sie Autor und Titel");
}
/**
 * Bestimmt die Haeufigkeit von N-grammen in liste, ueberfuehrt diese in listeH
 * @post- setzt Haeufigkeit der N-gramme auf Anzahl selbiger, ueberfuehrt dabei alles in listeH
 */
private void bestimmeHaeufigkeiten() {

	System.out.println("Parse Text:");
	int j = 0; // laufvariable fuer Ladebalken

	Ladebalken ladebalken = new Ladebalken((int)Math.sqrt(liste.size()));

	//	for(Ngramm elem: liste){
	for(Iterator<Ngramm> i = liste.iterator();i.hasNext();){
		Ngramm elem = (Ngramm) i.next();
		ladebalken.run(2*Math.sqrt(j));

		j++;

		Ngramm tmp = new Ngramm(elem.getName(), elem.getAnzahl());  //erzeuge Element aus einer Zeile der Liste
		display.Con.testAussage("create " + elem.getName() + " "  + elem.getAnzahl());

		liste.remove(elem); // erzeugtes Element aus der Liste loeschen

		do{

			if(liste.contains(tmp)){ //suche ob noch vorhanden:

				tmp.setAnzahl(tmp.getAnzahl()+1);
				display.Con.testAussage("Doppeltes E gefunden " + tmp.getName() +  " anzahl nun: " + tmp.getAnzahl() );

				liste.remove(tmp); //gefundenes entfernen
				display.Con.testAussage("entfernt");
			} 
		}  while (liste.contains(tmp));

		listeH.add(new Ngramm(tmp.getName(), tmp.getAnzahl()));

		display.Con.testAussage("Tmp added back : " + tmp.getName() + "  " + tmp.getAnzahl());

		display.Con.testAussage("Einmal durch naechtes wort");
		i = liste.iterator(); //update iterator
	}
	liste.clear(); //liste nun nutzlos
}

/**
 * Zeigt beide Listen
 * @standart - Deaktiviert
 */
private void zeigeliste(){
	display.Con.testAussage("------------\n" + liste.toString() + "\n");
	display.Con.testAussage("******");
	display.Con.testAussage("------------\n" + listeH.toString() + "\n");

}
/**
 * Die Liste wird nach Haeufigkeit sortiert, simplesort alg.
 * @post listeH nun nach Haeufigkeit sortiert
 */ 
private void sortiereNachHaeufigkeit() {

	zeigeliste();
	Ladebalken ladebalken = new Ladebalken(listeH.size());

	display.Con.testAussage("starte Sortieren");

	System.out.println("\nsortiere");

	//simplesort, sollte durch quicksort oder aehliches ersetzt werden:
	for(int i = listeH.size()-1; i > 1; i--){

		if(listeH.get(i).getAnzahl() > 1){
			for(int j = 0;j <= i-1; j++){
				if(listeH.get(i).getAnzahl() >= listeH.get(j).getAnzahl()){
					exchange(i, j);
					display.Con.testAussage("Tausche");
				}
			}
		}
		ladebalken.run(i);
	}	
	zeigeliste();
}

/**
 * Vertauscht die Elemnte mit den Indizes i und j
 * @param i index des ersten elements
 * @param j index des zweiten elements
 */
private void exchange(int i, int j) {
	Collections.swap(listeH, i, j);
}
/**
 * Lagert eine fertige liste aus
 * @param liste2 liste die ausgelagert wird, mit autor und titel als name
 * @param name - unter welchem die datei gespeichert wird, sollte autor und titel enthalten
 * @post Datei wird erzeugt welche die Liste enthaellt
 * Format:
 * >Wort=Anzahl>
 * Beispiel:
 *>Haus=7>
 */
private void lagereListeAus(String name) {

	String text = "#####" + name + "#####\n" +  this.listeH.toString();

	FileOutputStream output;
	try {

		name  += ".tcs";
		output = new FileOutputStream(name);

		for (int i=0; i < text.length(); i++){

			output.write((byte)text.charAt(i));

		}
		output.close();

		display.Con.testAussage("Datei ist geschrieben!");

	} catch (FileNotFoundException e) {
		System.err.println("Datei konnte nicht geschrieben werden");
		e.printStackTrace();

	}
	catch(IOException e){
		System.err.println("Datei konnte nicht geschrieben werden- IO Err");

		e.printStackTrace();

	}
}

/**
 * @return the liste
 */
public CopyOnWriteArrayList<Ngramm> getListe() {
	return liste;
}

/**
 * @return the listeH
 */
public CopyOnWriteArrayList<Ngramm> getListeH() {
	return listeH;
}

/**
 * @return name of Profil
 */
public String getName() {
	return this.name;
}

/**
 * @param liste the liste to set
 */
public void setListe(CopyOnWriteArrayList<Ngramm> liste) {
	this.liste = liste;
}

/**
 * @param listeH the listeH to set
 */
public void setListeH(CopyOnWriteArrayList<Ngramm> listeH) {
	this.listeH = listeH;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}

/**
 * Kein Effekt bisher
 * @return listeH toString
 * @see java.util.concurrent.CopyOnWriteArrayList#toString()
 */
@Override
public String toString() {
	String text = "";
	for(Ngramm elem: this.listeH){
		text += elem.toString() + "\n";
	}
	return text;
}

/**
 * Contains Methode fuer die Profil listen 
 * @param second - zu vergleichendes Profil
 * @return true wenn die eine Liste die andere enhaellt - rueckpruefung notwendig!
 */
public boolean contains(Profil second) {

	//TODO

	return false;
}

/**
 * @param arg0
 * @return
 * @see java.util.concurrent.CopyOnWriteArrayList#equals(java.lang.Object)
 */
public boolean equals(Object arg0) {
	return listeH.equals(arg0);
}
}