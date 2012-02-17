package tools;

/**
 * Vergleicht 2 NgrammListen auf Aehnlichkeit. Berechnung der Aehnlichkeit erfolgt ueber folgenden Algorithmus:
 * Summe der Durchschnittlichen Differenz der Position eines Wortes in den Listen* Laenge des Ngramms * Anzahl 
 * gefundener Woerter / Anzahl Woerter Gesamt
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

		
		if(testeGleichheit(first, second)){
			System.out.println("Die Texte sind entweder gleich oder einer ist ein Aussschnitt des anderen!");
			return 0;
		}
		
		
		//TODO Ladebalken
		double r = 0;
		int laengeNgramm = 0;
		int anzahl = 0;

		if(first.getListeH().size() == 0 || second.getListeH().size() == 0 ){
			System.err.println("Listen leer !");
		}

		for(Ngramm elem: first.getListeH()){
			
			int posFirst = 0;
					
			display.Con.testAussage("Vergleiche: " + elem.getName() + " UND " + second.getListeH().toString() );
			if(second.getListeH().contains(elem)){
					
				laengeNgramm = elem.getName().length();
				r += (posInSec(elem.getName(), first, second)-posFirst)*laengeNgramm;		//berechne r		
				anzahl++;
			}
			posFirst++;
		}

		display.Con.testAussage("Fertig" + r);
		
		System.out.println("Von " + (first.getListeH().size()+second.getListeH().size()) + " vorhandenen Ngrammen wurden " +  anzahl + " uebereinstimmungen gefunden," +
				"\ndas entspricht 1/" + (first.getListeH().size()+second.getListeH().size())/anzahl);
		System.out.println("Die Durchschnittliche Differenz betraegt: " + Math.round(((r/anzahl))) + " Schritte");
		
		r /= first.getListeH().size()+second.getListeH().size();		//teile aehnlichkeit durch laenge der beiden texte repraesentiert durch die laenge ihrer wortlisten
		
		return Math.round(r); //return gerundetes r
	}

	/**
	 * Testet ob 2 Profile gleich oder Ausschnitte des anderen sind
	 * @param first - erste Ngramm Liste
	 * @param second - zweite Ngramm liste
	 * @return true wenn gleich oder Teil des anderen. false wenn ungleich
	 */
	private boolean testeGleichheit(Profil first, Profil second) {

		if(first.equals(second) || first.contains(second) || second.contains(first)){
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