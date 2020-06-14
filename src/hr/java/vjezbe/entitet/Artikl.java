package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Opisuje artikl.
 * @author Martina
 */
public abstract class Artikl extends Entitet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3072856779337823701L;
	private String naslov;
	private String opis;
	private BigDecimal cijena;
	private Stanje stanje;
	
	

	/**
	 * Inicijalizira objekt klase Artikl.
	 * @param naslov naslov artikla
	 * @param opis opis artikla
	 * @param cijena cijena artikla
	 * @param stanje stanje artikla
	 */
	public Artikl(String naslov, String opis, BigDecimal cijena, Stanje stanje, Long id) {
		super(id);
		this.naslov = naslov;
		this.opis = opis;
		this.cijena = cijena;
		this.stanje = stanje;
	}

	
	public Stanje getStanje() {
		return stanje;
	}

	public void setStanje(Stanje stanje) {
		this.stanje = stanje;
	}

	public String getNaslov() {
		return naslov;
	}
	
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public BigDecimal getCijena() {
		return cijena;
	}

	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}
	
	/**
	 * Dohvaca tekst oglasa za artikl.
	 * @return tekst oglasa
	 */
	public abstract String tekstOglasa();
	
}
