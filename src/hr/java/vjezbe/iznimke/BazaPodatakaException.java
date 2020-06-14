package hr.java.vjezbe.iznimke;

public class BazaPodatakaException extends Exception {

	private static final long serialVersionUID = 6186263838409046177L;
	public BazaPodatakaException(String poruka) {
		super(poruka);
	}
	
	public BazaPodatakaException(Throwable uzrok) {
		super(uzrok);
	}
	
	public BazaPodatakaException(String poruka, Throwable uzrok) {
		super(poruka, uzrok);
	}

	public BazaPodatakaException() {
		super("Greska u radu s bazom podataka\n");
	}
}
