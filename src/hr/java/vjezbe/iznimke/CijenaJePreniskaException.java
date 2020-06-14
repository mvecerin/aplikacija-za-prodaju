package hr.java.vjezbe.iznimke;

/**
 * Opisuje iznimku preniskog unosa cijene.
 * @author Martina
 */
public class CijenaJePreniskaException extends RuntimeException {

	private static final long serialVersionUID = 2555341954833609776L;
	
	/**
	 * Inicijalizira iznimku.
	 * @param poruka poruka greške
	 */
	public CijenaJePreniskaException(String poruka) {
		super(poruka);
	}
	
	/**
	 * Inicijalizira iznimku.
	 * @param uzrok uzrok greške
	 */
	public CijenaJePreniskaException(Throwable uzrok) {
		super(uzrok);
	}
	
	/**
	 * Inicijalizira iznimku.
	 * @param poruka poruka greške
	 * @param uzrok uzrok greške
	 */
	public CijenaJePreniskaException(String poruka, Throwable uzrok) {
		super(poruka, uzrok);
	}
	
	/**
	 * Inicijalizira iznimku.
	 */
	public CijenaJePreniskaException() {
		super("Cijena ne smije biti manja od 10000kn");
	}

}
