package hr.java.vjezbe;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;

public class UnosProdajeController {
	
	@FXML
	private ComboBox<Artikl> artiklCombo;
	@FXML
	private ComboBox<Korisnik> korisnikCombo;
	@FXML
	private DatePicker datumPicker;
		
	@FXML
	public void unesi() {
		
		String errorString = new String();
		if (artiklCombo.getValue().equals(null)) {
			errorString += "Artikl je obavezan podatak!\n";
		}
		if (korisnikCombo.getValue().equals(null)) {
			errorString += "Korisnik je obavezan podatak!\n";
		}
		if (datumPicker.getValue().equals(null)) {
			errorString += "Datum je obavezan podatak!\n";
		}
		
		if(errorString.isEmpty()) {
			Alert uspjeh = new Alert(AlertType.INFORMATION);
			uspjeh.setTitle(uspjeh.getAlertType().name());
			uspjeh.setHeaderText("Podaci uspjesno uneseni!");

			try {
				BazaPodataka.pohraniNovuProdaju(new Prodaja(artiklCombo.getValue(), korisnikCombo.getValue(), datumPicker.getValue()));
			} catch (BazaPodatakaException e) {
				e.printStackTrace();
			}
			uspjeh.showAndWait();
		}
		else {
			Alert neuspjeh = new Alert(AlertType.ERROR);
			neuspjeh.setTitle(neuspjeh.getAlertType().name());
			neuspjeh.setHeaderText(errorString);
			neuspjeh.showAndWait();
		}	
	}
	

	@FXML
	public void initialize() {
		artiklCombo.getItems().setAll(BazaPodataka.dohvatiArtikle());			
		korisnikCombo.getItems().setAll(BazaPodataka.dohvatiKorisnike());
		
	}
}
