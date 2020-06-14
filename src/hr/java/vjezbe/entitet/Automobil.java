package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * Opisuje tip artikla automobil.
 * @author Martina
 */
public class Automobil extends Artikl implements Vozilo {

	private static final long serialVersionUID = 3240503714876758948L;

	BigDecimal snagaKs;
	
	private static final Logger logger = LoggerFactory.getLogger(Automobil.class);
	
	/**
	 * Inicijalizira objekt klase Automobil.
	 * @param naslov naslov automobila
	 * @param opis opis automobila
	 * @param cijena cijena automobila
	 * @param snagaKs broj konjskih snaga automobila
	 */
	public Automobil(String naslov, String opis, BigDecimal cijena,  BigDecimal snagaKs, Stanje stanje, Long id) {
		super(naslov, opis, cijena, stanje, id);
		this.snagaKs = snagaKs;
	}
	
	@Override
	public String toString() {
		return super.getNaslov() + ", " + super.getOpis() + ", snaga:" + snagaKs + ", cijena:" + super.getCijena() + ", stanje:" + super.getStanje();
	}
	
	public BigDecimal getSnagaKs() {
		return snagaKs;
	}
	
	public void setSnagaKs(BigDecimal snagaKs) {
		this.snagaKs = snagaKs;
	}

	/**
	 * Racuna grupu osiguranja ovisno o broju konjskih snaga automobila i baca iznimku NemoguceOdreditiGrupuOsiguranjaException.
	 */
	@Override
	public BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException{ 
	
		if(snagaKs.compareTo(new BigDecimal("100")) <= 0) {
			return new BigDecimal("1");
		}
		else if(snagaKs.compareTo(new BigDecimal("300")) <= 0) {
			return new BigDecimal("2");
		}
		else if(snagaKs.compareTo(new BigDecimal("500")) <= 0) {
			return new BigDecimal("3");
		}
		else if(snagaKs.compareTo(new BigDecimal("700")) <= 0) {
			return new BigDecimal("4");
		}
		else if(snagaKs.compareTo(new BigDecimal("1000")) <= 0) {
			return new BigDecimal("5");
		}
		else {
			throw new NemoguceOdreditiGrupuOsiguranjaException();
		}
	}

	/**
	 * Dohvaca tekst oglasa za automobil i hvata iznimku NemoguceOdreditiGrupuOsiguranjaException.
	 */
	@Override
	public String tekstOglasa() {
		String string = null;
		try {
			 string = ("Naslov automobila: " + super.getNaslov() 
					+ "\nOpis automobila: " + super.getOpis() 
					+ "\nSnaga automobila: " + snagaKs
					+ "\nStanje automobila: " + super.getStanje()
					+ "\nIzračun osiguranja automobila: " + izracunajCijenuOsiguranja()
					+ "\nCijena automobila: " + super.getCijena());         
		}
		
		catch (NemoguceOdreditiGrupuOsiguranjaException e) {
			logger.error(e.getMessage(), e);
			
			string = ("Naslov automobila: " + super.getNaslov() 
			+ "\nOpis automobila: " + super.getOpis() 
			+ "\nSnaga automobila: " + snagaKs
			+ "\nStanje automobila: " + super.getStanje()
			+ "\nIzračun osiguranja automobila: " + e.getMessage()
			+ "\nCijena automobila: " + super.getCijena());    
		}
		return string;
	}

}
