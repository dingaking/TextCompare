package tools;

/**
 * 
 * @author menzel
 *
 */
public class Restriktionsenzym {
	
	
	private String erkennungssequenz = "";
	private int schnittstelle = 0;
	private String name = "";
	
	/**
	 * Konstruktor eines Restriktionsenzym
	 * @param erkennungssequenz - im Format AT|CC, wobei der Vertikale Strich die Schnittstelle anzeigt
	 */
	public Restriktionsenzym(String erkennungssequenz, String name) {
		
		this.name = name;
		this.erkennungssequenz = erzeugeErkennungssequenz(erkennungssequenz);
		this.schnittstelle = holeSchnittstelle(erkennungssequenz);
		

	}
	/**
	 * Findet die Schnittstelle anhand des vertialen Striches
	 * @param erkennungssequenz
	 * @return positon der schnittstelle
	 */
	private int holeSchnittstelle(String erkennungssequenz) {

		for(int i = 0; i < erkennungssequenz.length(); i++){
			if(erkennungssequenz.charAt(i) == '|'){
				display.Con.testAussageLate("Schnittstelle gefunden bei : " + i);
				return i;
			}
		}
		if(!erkennungssequenz.contains("|")){
			System.err.println("Keine gueltige Schnittstelle gesetzt");
		}
		return 0;
	}
	
	/**
	 * erzeugt die erkennungssequenz, schneidet dafuer den vertikalen stricht weg
	 * @param erkennungssequenz
	 * @return erkennungssequenz(TMP) ohne schnittstelle
	 */
	private String erzeugeErkennungssequenz(String erkennungssequenz) {

		String erkennungssequenzTMP = "";
		
		for(int i = 0; i < erkennungssequenz.length(); i++){
			if(erkennungssequenz.charAt(i) != '|'){
				erkennungssequenzTMP +=  String.valueOf(erkennungssequenz.charAt(i));
			}
		}
		display.Con.testAussageLate("Erkennungssequenz des RE ist : "+ erkennungssequenzTMP);
		return erkennungssequenzTMP;
	}
	
	/**Getter fuer Erkennungssequenz
	 * @return the erkennungssequenz
	 */
	public String getErkennungssequenz() {
		return erkennungssequenz;
	}
	/**
	 * Getter fuer die Schnittstelle
	 * @return the schnittstelle
	 */
	public int getSchnittstelle() {
		return schnittstelle;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
