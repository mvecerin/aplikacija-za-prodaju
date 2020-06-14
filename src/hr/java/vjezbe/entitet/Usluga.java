package hr.java.vjezbe.entitet;


import java.math.BigDecimal;

/**
 * Opisuje tip artikla.
 * @author Martina
 */
public class Usluga extends Artikl {
	
	private static final long serialVersionUID = 5689090441680996947L;

	/**
	 * Inicijalizira objekt klase Usluga.
	 * @param naslov naslov usluge
	 * @param opis opis usluge
	 * @param cijena cijena usluge
	 */
	public Usluga(String naslov, String opis, BigDecimal cijena, Stanje stanje, Long id) {
		super(naslov, opis, cijena, stanje, id);
	}
	
	@Override
	public String toString() {
		return super.getNaslov() + ", " + super.getOpis() + ", cijena:" + super.getCijena() + ", stanje:" + super.getStanje();
	}
	
	/**
	 * Ispisuje tekst aktivnog oglasa.
	 */
	@Override
	public String tekstOglasa() {
		return ("Naslov usluge: " + super.getNaslov()
		+ "\nOpis usluge: " + super.getOpis()
		+ "\nStanje usluge: " + super.getStanje()
		+ "\nCijena usluge: " + super.getCijena());
	}

}
