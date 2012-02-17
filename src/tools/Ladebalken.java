package tools;

/**
 * Klasse fuer einen Ladebalken
 * @author menzel
 *
 */
public class Ladebalken {
	
	private double schritte = 0;

	/**
	 * Konstruktor Ladebalken
	 * @param max
	 */
	public Ladebalken(int max){
		
		this.schritte = max/100;
	}
	
	/**
	 * Zeigt wenn augerufen den Fortschirtt ueber den Ladebalken
	 * @param fortschritt - laufzahl in der Schleife ueber die der Ladebalken visualisiert wird
	 */
	public void run(double fortschritt){

		if(fortschritt%this.schritte == 0){
			System.out.print("-");
		}
	}
	
}
