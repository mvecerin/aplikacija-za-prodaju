package hr.java.vjezbe.entitet;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Opisuje aktivne oglase.
 * @author Martina
 */
public class Prodaja /*extends Entitet*/ {
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 2381807083528610159L;
	private Artikl artikl;
	private Korisnik korisnik;
	private LocalDate datumObjave;
	
	/**
	 * Inicijalizira objekt klase Prodaja.
	 * @param artikl objekt klase artikl
	 * @param korisnik objekt klase Korisnik
	 * @param datumObjave datum objave
	 */
	public Prodaja(Artikl artikl, Korisnik korisnik, LocalDate datumObjave) {
		//super(id);
		this.artikl = artikl;
		this.korisnik = korisnik;
		this.datumObjave = datumObjave;
	}
	
	public Artikl getArtikl() {
		return artikl;
	}
	
	public void setArtikl(Artikl artikl) {
		this.artikl = artikl;
	}
	
	public Korisnik getKorisnik() {
		return korisnik;
	}
	
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	
	public LocalDate getDatumObjave() {
		return datumObjave;
	}
	
	public void setDatumObjave(LocalDate datumObjave) {
		this.datumObjave = datumObjave;
	}
	
	/**
	 * Formatira datum objave.
	 * @param datumObjave datum objave
	 * @return formatirani datum objave
	 */
	public String formatirajDatum() {
		DateTimeFormatter formatiraniObjekt = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		String formatiraniDatum = datumObjave.format(formatiraniObjekt);
		return formatiraniDatum;
	}
	
}
