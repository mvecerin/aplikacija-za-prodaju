package hr.java.vjezbe.iznimke;

/**
 * Opisuje iznimku prevelikog unosa kw.
 * @author Martina
 */
public class NemoguceOdreditiGrupuOsiguranjaException extends Exception {

	private static final long serialVersionUID = -2291535264076831445L;
	
	/**
	 * Inicijalizira iznimku.
	 * @param poruka poruka iznimke
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(String poruka) {
		super(poruka);
	}
	
	/**
	 * Inicijalizira iznimku.
	 * @param uzrok uzrok greške
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(Throwable uzrok) {
		super(uzrok);
	}
	
	/**
	 * Inicijalizira iznimku.
	 * @param poruka poruka iznimke
	 * @param uzrok uzrok greške
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(String poruka, Throwable uzrok) {
		super(poruka, uzrok);
	}
	
	/**
	 * Inicijalizira iznimku.
	 */
	public NemoguceOdreditiGrupuOsiguranjaException() {
		super("Previše kw, ne mogu odrediti grupu osiguranja.");
	}

}
