package tools;

/**
 * Vergleicht 2 NgrammListen auf Aehnlichkeit. Berechnung der Aehnlichkeit erfolgt ueber folgenden Algorithmus:
 * Summe der Durchschnittlichen Differenz der Position eines Wortes *  Laenge des Ngramms / Anzahl Woerter Gesamt
 * Gerundet auf eine Nachkommstelle
 * @author menzel
 *
 */
public class Vergleich {

	private double aehnlichkeit = 0;
	private String TitelFirst;
	private String TitelSecond;
	
	
	/**
	 * Konstruktor eines Vergleichobjekts erste und zweite listen werden verglichen
	 * @param first erste liste
	 * @param second zweite liste
	 */
	public Vergleich(Profil first, Profil second){

		TitelFirst = first.getName();
		TitelSecond = second.getName();
		this.aehnlichkeit = vergleiche(first, second);

	}

	/**
	 * Vergleicht 2 NgrammListen nach dem oben angegebenen Algorithmus
	 * @param first erste ngrammliste
	 * @param second zweite ngramm liste
	 * @return aehnlichkeit der beiden list als int wert
	 */
	private double vergleiche(Profil first, Profil second) {

		//pruefe ob die zu vergleichenden Texte gleich sind:
		if(testeGleichheit(first, second)){
		System.out.println("Die Texte sind die selben!");
			return 0; 			
		}
		
		if(!ordneTexte(first, second)){	//pruefe reihenfolge, tausche wenn noetig:
			Profil tmp = new Profil(null, 0, null);
			
			tmp = first;
			first  = second;
			second = tmp;
			
		}
		double r = 0;
		int laengeNgramm = 0;
		int anzahl = 0;

		if(first.getListeH().size() == 0 || second.getListeH().size() == 0 ){
			System.err.println("Listen leer !");
		}

		Ladebalken ladebalken = new Ladebalken(first.getListeH().size());
		int i = 0; //laufzahl fuer ladebalken
		int SummeLaenge = 0;

		
		for(Ngramm elem: first.getListeH()){
			
			int posFirst = 0;
					
			display.Con.testAussage("Vergleiche: " + elem.getName() + " UND " + second.getListeH().toString() );
			if(second.getListeH().contains(elem)){
					
				laengeNgramm = elem.getName().length();
				r += (posInSec(elem.getName(), first, second)-posFirst)*laengeNgramm;		//berechne r		
				anzahl+=elem.getAnzahl();
			SummeLaenge += laengeNgramm*elem.getAnzahl(); // um durchschnittliche laenge zu errechnen
			}

			ladebalken.run(i++);
			posFirst++;
			
		}

		double durchschnittlicheLaengeNgramm = SummeLaenge/anzahl;
		
		display.Con.testAussage("Fertig" + r);
		
		System.out.println("\nVon " + (first.getListeH().size()+second.getListeH().size()) + " vorhandenen Ngrammen wurden " +  anzahl + " uebereinstimmungen gefunden," +
				"\ndas entspricht in etwa 1/" + (first.getListeH().size()+second.getListeH().size())/anzahl);
		System.out.println("Die Durchschnittliche Differenz betraegt " + Math.round(r/(anzahl*durchschnittlicheLaengeNgramm)) + " Schritte"); 

		r = Math.round((r*((first.getListeH().size()+second.getListeH().size())/anzahl)) / (durchschnittlicheLaengeNgramm*(first.getListeH().size()+second.getListeH().size())));		
		//teile aehnlichkeit mal der Anteile der Uebereinstimmungen durch laenge der beiden texte repraesentiert durch die laenge ihrer wortlisten

		
		return r;
	}

	/**
	 * Prueft ob die zu vergleichenden Texte in der richtigen Reihenfolge sind, laenge der Profile ist ausschlaggebend
	 * first soll groesser sein, second das kleinere
	 * @param second  erstes Profil das ueberprueft wird
	 * @param first  zweites Profil das ueberprueft wird
	 * @return true wenn sie es sind, false wenn sie es nicht sind
	 */
	private boolean ordneTexte(Profil first, Profil second) {

		if(first.getListeH().size() > second.getListeH().size()){
			return false;
		}
		return true;
	}

	/**
	 * Testet ob 2 Profile gleich oder Ausschnitte des anderen sind
	 * @param first - erste Ngramm Liste
	 * @param second - zweite Ngramm liste
	 * @return true wenn gleich oder Teil des anderen. false wenn ungleich
	 */
	private boolean testeGleichheit(Profil first, Profil second) {

		
		if(first.getName().equalsIgnoreCase(second.getName()) && first.getListeH().size() == (second.getListeH().size())){ 
			return true;
		}
		return false;
	}

	/**
	 * Bestimmt den Abstand zweiter Woerter in den Listen
	 * @param name
	 */
	private int posInSec(String name, Profil first, Profil second) {	

		int posSec = 0;
		Ngramm tmp = new Ngramm(name, 0);
				
		for(Ngramm search: second.getListeH()){
			if(search.equals(tmp) && search.getName() != "~~~~"){
				break;
			}
			posSec++;
		}
		display.Con.testAussage("Pos in sec: " + posSec);
		
		return posSec;
	}

	/**
	 * getter fuer Aehnlichkeit eines Vergleichs
	 * @return double, wert der aehnlichkeit
	 */
	public double getAehnlichkeit(){
		return this.aehnlichkeit;
	}
	
	/**
	 * @return the titelFirst
	 */
	public String getTitelFirst() {
		return TitelFirst;
	}
	
	/**
	 * @return the titelSecond
	 */
	public String getTitelSecond() {
		return TitelSecond;
	}

}