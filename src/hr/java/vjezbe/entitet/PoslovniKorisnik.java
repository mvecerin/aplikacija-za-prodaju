package hr.java.vjezbe.entitet;

/**
 * Opisuje poslovnog korisnika.
 * @author Martina
 */
public class PoslovniKorisnik extends Korisnik {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8562127677356397199L;
	String naziv, web;
	
	/**
	 * Inicijalizira objekt klase PoslovniKorisnik.
	 * @param naziv naziv tvrtke
	 * @param email email tvrtke
	 * @param web web tvrtke
	 * @param telefon broj telefona tvrtke
	 */
	public PoslovniKorisnik(String naziv, String web, String email,String telefon, Long id) {
		super(id, email, telefon);
		this.naziv = naziv;
		this.web = web;
	}

	@Override
	public String toString() {
		return naziv + ", web:" + web + ", email:" + super.getEmail() + ", tel:" + super.getTelefon();
	}
	
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getWeb() {
		return web;
	}
	
	public void setWeb(String web) {
		this.web = web;
	}

	/**
	 * Dohvaca informacije o kontaktu za aktivni oglas.
	 */
	@Override
	public String dohvatiKontakt() {
		return ("Naziv tvrtke: " + getNaziv() + ", mail: " + super.getEmail() 
		+ ", tel: " + super.getTelefon() + ", web: " + getWeb());
	}

}
