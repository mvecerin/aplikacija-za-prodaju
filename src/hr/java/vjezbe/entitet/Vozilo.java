package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * Računa grupu osiguranja i kw za Automobil, te definira apstraktnu metodu za računanje grupe osiguranja. 
 * @author Martina
 */
public interface Vozilo {
	
	/**
	 * Pretvara konjske snage u kw.
	 * @param konjskaSnaga podatak o broju konjskih snaga
	 * @return kw
	 */
	default public BigDecimal izracunajKw(BigDecimal konjskaSnaga) {
		return konjskaSnaga.multiply(new BigDecimal("0.7456998"));
	}
	
	/**
	 * Računa grupu osiguranja.
	 * @return grupa osiguranja
	 * @throws NemoguceOdreditiGrupuOsiguranjaException
	 */
	public abstract BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException;
	
	/**
	 * Računa cijenu osiguranja.
	 * @return cijena osiguranja
	 * @throws NemoguceOdreditiGrupuOsiguranjaException
	 */
	default public BigDecimal izracunajCijenuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException{
		
		BigDecimal broj = null;
		if(izracunajGrupuOsiguranja().compareTo(new BigDecimal("1"))==0) {
			broj = new BigDecimal("100");
		}
		else if(izracunajGrupuOsiguranja().compareTo(new BigDecimal("2"))==0) {
			broj = new BigDecimal("300");
		}
		else if(izracunajGrupuOsiguranja().compareTo(new BigDecimal("3"))==0) {
			broj = new BigDecimal("500");
		}
		else if(izracunajGrupuOsiguranja().compareTo(new BigDecimal("4"))==0) {
			broj = new BigDecimal("700");
		}
		else if(izracunajGrupuOsiguranja().compareTo(new BigDecimal("5"))==0) {
			broj = new BigDecimal("900");
		}
		else {
			throw new NemoguceOdreditiGrupuOsiguranjaException();
		}
		return broj;
	}
}
