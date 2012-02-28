package tools;

import java.util.ArrayList;
/**
 * 
 * @author menzel
 *
 */
public class Matrix {

	ArrayList<Vergleich> vergleiche = new ArrayList<Vergleich>();
	double[][] aehnlichkeit;
	String[] texte;
	/**
	 * Konstruktor fuer eine Matrix
	 */
	public Matrix() {

//		erstelleMatrix();

	}
	/**
	 * erstellt eine Matrix auf Grundlage der ArrayList vergleiche
	 */
	private void erstelleMatrix() {

		this.texte = new String[(2*vergleiche.size())]; //zu gross, muss angepasst werden
		int i = 0;
		
		
		System.out.println("\nAdjazenzmatrix, die Zahl zeigt die Aehnlichkeit ein, wobei\n " +
				"eine hoehere Zahl eine groessere Differenz der Texte bedeutet:");
		
		for(Vergleich each: vergleiche){

			if(!TitelVorhanden(each.getTitelFirst())){

				texte[i] = each.getTitelFirst(); //fuege ersten namen aus dem vergleich hinzu
				i++;
			}
			if(!TitelVorhanden(each.getTitelSecond())){
				texte[i] = each.getTitelSecond(); //fuege weiten namen hinzu
				i++;
			}
		}

		int j = 1;

		for(String name: texte){
			if(name != null){ //zeige listearry
				System.out.println(j++ + ". " + name);
			
			}
		}

		this.aehnlichkeit = new double[texte.length-countNull()][texte.length-countNull()]; //init aehnlichkeits array


		for(Vergleich each: vergleiche){
			this.aehnlichkeit[getPosition(each.getTitelFirst())][getPosition(each.getTitelSecond())] = each.getAehnlichkeit(); // Indizes der Titel ergibt Schnittpunkt fuer das eintragen der Aehnlichkeit

			//jeder TItel, und auch jeder Schnittpunkt ist 2x im Array:
			this.aehnlichkeit[getPosition(each.getTitelSecond())][getPosition(each.getTitelFirst())] = each.getAehnlichkeit(); // Indizes der Titel ergibt Schnittpunkt fuer das eintragen der Aehnlichkeit

		}

		zeigeMatrix();
	}

	/**
	 * Ermittelt die Position eins Textes fuer die Schnittpunkt-bestimmung in der Matrix
	 * Array Texte ist gleichzusetzen mit der der Nummeriung des Arrays aehnlichkeit!
	 * @param titel der gesucht wird
	 * @return position im array.  -1 wenn nciht vorhanden - loest fehler aus !
	 * @pre titel muss vorhanden sein
	 */
	private int getPosition(String titel) {

		int position = 0;
		for(String name: texte){

			if(name.equalsIgnoreCase(titel)){
				return position;
			}
			position++;
		}
		return -1;
	}
	/**
	 * zaehl die ueberfluessigen null Values in der liste der texte
	 * @return zahl der texte mit dem namen null
	 */
	private int countNull() {

		int anzahl = 0;

		for(String name: texte){
			if((name == null)){
				anzahl++;
			}
		}

		return anzahl;
	}
	/**
	 * Prueft ob der Titel bereits im Array vorhanden ist
	 * @param NameOther  - name des zu vergleichenden textes mit dem array
	 * @return false wenn text nicht vorhanden, true wenn vorhanden
	 */
	private boolean TitelVorhanden(String NameOther) {


		if(texte[0] == null){  //noetig ?
			return false;
		}
		for(String name: texte){
			if(!(name == null)  && name.equalsIgnoreCase(NameOther)){
				return true;
			}
		}
		return false;
	}
	/**
	 * Gibt eine Matrix mit aehnlichkeiten aus
	 * @param aehnlichkeit werte der aehnlichkeit zweier Profile
	 */
	public void zeigeMatrix() {

		int i = aehnlichkeit.length;
		System.out.print("    __");
		for(int j = 0; j < i ; j++){
			System.out.print((j+1) + "_____"); //Zeichne obere Zeile
		}
		System.out.println();


		for(int p = 0; p< i ;p++){ //fuer jede zeile

			System.out.print((p+1) + "|");

			for(int s = 0; s < i ; s++){ //zeile ausgeben

				//Laenge der Zahl ermitteln um dann so viele Leerzeichen auszugeben, damit die Tabelle ordentlich formatiert ist:
				String nr = String.valueOf(Math.round(aehnlichkeit[p][s]));
				int laenge=nr.length();

				for(int l = laenge ; l < 5; l++ ){
					System.out.print(" ");
				}
				System.out.print(Math.round(aehnlichkeit[p][s]) + " |"); //ausgabe der aehnlichkeit
			}
			System.out.println();
		}
	}

	/**
	 * Fuegt einen Vergleich zur Matrix hinzu
	 * @param random - ein Vergleich der der Matrix hinzugefuegt wird
	 * @post - Matrix enthaellt nun den Vergleich, muss dafuer neu erzeugt werden
	 */
	public void addVergleich(Vergleich random){

		vergleiche.add(random);
		erstelleMatrix();
	}
}