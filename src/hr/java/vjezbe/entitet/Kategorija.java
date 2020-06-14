package hr.java.vjezbe.entitet;

import java.util.List;


/**
 * Opisuje kategorije oglasa.
 * @author Martina
 */
public class Kategorija <T extends Artikl> extends Entitet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5401132629765773468L;
	private String naziv;
	private List <T> artikli;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kategorija<?> other = (Kategorija<?>) obj;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}

	/**
	 * Inicijalizira objekt klase Kategorija.
	 * @param naziv naziv kategorije
	 * @param artikli niz artikala pod navedenom kategorijom
	 */
	public Kategorija(Long id, String naziv, List<T> artikli) {
		super(id);
		this.naziv = naziv;
		this.artikli = artikli; 
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<T> getArtikli() {
		return artikli;
	}

	public void setArtikli(List<T> artikli) {
		this.artikli = artikli;
	}
	
	public void dodajArtikl(T artikl) {
		artikli.add(artikl);
	}
	
	public T dohvatiArtikl(int i) {
		return artikli.get(i);
	}
	/*
	public List<T> dohvatiListu(){ //radi isto sta i getArtikli()
		return artikli;
	}*/
}
