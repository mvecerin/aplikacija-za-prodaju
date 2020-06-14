package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.CijenaJePreniskaException;

/**
 * Opisuje tip artikla.
 * @author Martina
 */
public class Stan extends Artikl implements Nekretnina {

	
	private static final long serialVersionUID = 3477936863404459736L;

	int kvadratura;
	
	private static final Logger logger = LoggerFactory.getLogger(Stan.class);
	
	/**
	 * Inicijalizira objekt klase Stan, uz parametre nadklase sadrži i kvadraturu.
	 * @param naslov naslov stana
	 * @param opis opis stana
	 * @param cijena cijena stana
	 * @param kvadratura kvadratura stana
	 */
	public Stan(String naslov, String opis, BigDecimal cijena, int kvadratura, Stanje stanje, Long id) {
		super(naslov, opis, cijena, stanje, id);
		this.kvadratura = kvadratura;
	}
	
	@Override
	public String toString() {
		return super.getNaslov() + ", " + super.getOpis() + ", cijena:" + super.getCijena() + ", kvadratura:" + kvadratura + ", stanje:" + super.getStanje();
	}
	
	public int getKvadratura() {
		return kvadratura;
	}

	public void setKvadratura(int kvadratura) {
		this.kvadratura = kvadratura;
	}

	/**
	 * Ispisuje tekst aktivnog oglasa za stan, te hvata iznimku CijenaJePreniskaException.
	 */
	@Override
	public String tekstOglasa() {
		String string = null;
		try {
			izracunajPorez(super.getCijena());
			 string = ("Naslov nekretnine: " + super.getNaslov()
				+ "\nOpis nekretnine: " + super.getOpis()
				+ "\nKvadratura nekretnine: " + kvadratura  //TODO
				+ "\nStanje nekretnine: " + super.getStanje()
				+ "\nPorez na nekretnine: " + izracunajPorez(super.getCijena())		
				+ "\nCijena nekretnine: " + super.getCijena());      
		}
		
		catch (CijenaJePreniskaException e) {
			logger.error(e.getMessage(), e);
			string = ("Naslov nekretnine: " + super.getNaslov()
			+ "\nOpis nekretnine: " + super.getOpis()
			+ "\nKvadratura nekretnine: " + kvadratura
			+ "\nStanje nekretnine: " + super.getStanje()
			+ "\nPorez na nekretnine: " + e.getMessage()
			+ "\nCijena nekretnine: " + super.getCijena());    
		}
		return string;
	}

}
