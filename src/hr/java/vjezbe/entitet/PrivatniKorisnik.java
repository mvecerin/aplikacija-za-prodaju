package hr.java.vjezbe.entitet;

/**
 * Opisuje privatnog korisnika.
 * @author Martina
 */
public class PrivatniKorisnik extends Korisnik {

	/**
	 * 
	 */
	private static final long serialVersionUID = 610165501757130613L;
	private String ime, prezime;

	/**
	 * Inicijalizira objekt klase PrivatniKorisnik.
	 * @param ime ime korisnika
	 * @param prezime prezime korisnika
	 * @param email email korisnika
	 * @param telefon broj telefona korisnika
	 */

	public PrivatniKorisnik(String ime, String prezime, String email, String telefon, Long id) {
		super(id, email, telefon);
		this.ime = ime;
		this.prezime = prezime;
	}

	@Override
	public String toString() {
		return ime + ", " + prezime + ", email:" + super.getEmail() + ", tel:" + super.getTelefon();
	}
	
	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	/**
	 * Dohvaca informacije o kontaktu za aktivni oglas.
	 */
	@Override
	public String dohvatiKontakt() {
		return ("Osobni podaci prodavatelja: " + getIme() + " " + getPrezime() 
				+ ", mail: " + super.getEmail() + ", tel: " + super.getTelefon());
	}
	
}
