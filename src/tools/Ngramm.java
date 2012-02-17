package tools;
/**
 * 
 * @author menzel
 *
 */
public class Ngramm implements Comparable<Ngramm> {


	private String name = null;
	private int anzahl = 0;


	/**
	 * Konstruktor fuer ein Ngramm
	 * @param name - String des Ngramms
	 * @param anzahl - haeufigkeit in der Liste, standartmaessig 1
	 */
	Ngramm(CharSequence name, int anzahl){

		this.name = (String) name;
		this.anzahl = anzahl;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Ngramm other = (Ngramm) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the anzahl
	 */
	public int getAnzahl() {
		return anzahl;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param anzahl the anzahl to set
	 */
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}

	/**
	 * toString methode fuer Ngramme
	 */
	@Override
	public String toString() {

		return (">" + this.name + "=" + this.anzahl + ">\n" );
	}
	/**
	 * Override compareTo
	 * Vergleicht zwei Ngramme nach ihrer Haeufigkeit 
	 * @param arg0 - Ngramm das verglichen wird mit den aktuellen Ngramm
	 */
	@Override
	public int compareTo(Ngramm arg0) {

		Ngramm other = (Ngramm) arg0;
		display.Con.testAussage("vergleiche:" + other.getName() + " UND " + this.name);

		return ((Integer)this.anzahl).compareTo((Integer)other.getAnzahl());
	}

	/**
	 * @param anotherString
	 * @return
	 * @see java.lang.String#equalsIgnoreCase(java.lang.String)
	 */
	public boolean equals(String anotherString) {

		return name.equals(anotherString);
	}

}
