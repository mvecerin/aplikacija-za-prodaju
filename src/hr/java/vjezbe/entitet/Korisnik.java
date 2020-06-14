package hr.java.vjezbe.entitet;

/**
 * Opisuje korisnike.
 * @author Martina
 */
public abstract class Korisnik extends Entitet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2817314347483451116L;
	private String email;
	private String telefon;
	
	/**
	 * Inicijalizira objekt klase Korisnik.
	 * @param email email korisnika
	 * @param telefon broj telefona korisnika
	 */
	public Korisnik(Long id, String email, String telefon) {
		super(id);
		this.email = email;
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	/**
	 * Dohvaca informacije o kontaktu
	 * @return informacije o kontaktu
	 */
	public abstract String dohvatiKontakt();
}
