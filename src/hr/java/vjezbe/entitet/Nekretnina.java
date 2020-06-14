package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.CijenaJePreniskaException;

/**
 * Racuna porez na cijenu stana.
 * @author Martina
 */
public interface Nekretnina {

	/**
	 * Racuna porez na cijenu stana i izbacuje iznimku CijenaJePreniskaException.
	 * @param cijena cijena stana
	 * @return porez na cijenu stana
	 * @throws CijenaJePreniskaException ako je cijena manja ili jednaka 10000
	 */
	default public BigDecimal izracunajPorez(BigDecimal cijena) throws CijenaJePreniskaException{
	
		if(cijena.compareTo(new BigDecimal("10000")) <= 0) {
			throw new CijenaJePreniskaException();
		}
		else {
			return cijena.multiply(new BigDecimal("0.3"));
		}
		
	}
}
