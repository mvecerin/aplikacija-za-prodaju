package hr.java.vjezbe;

import java.util.OptionalLong;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class UnosPoslovnogKorisnikaController {
		
	@FXML
	private TextField nazivText;
	@FXML
	private TextField webText;
	@FXML
	private TextField emailText;
	@FXML
	private TextField telefonText;
		
	@FXML
	public void unesi() {
		
		String errorString = new String();
		if (nazivText.getText().contentEquals("")) {
			errorString += "Naziv je obavezan podatak!\n";
		}
		if (webText.getText().contentEquals("")) {
			errorString += "Web je obavezan podatak!\n";
		}
		if (emailText.getText().contentEquals("")) {
			errorString += "E-mail je obavezan podatak!\n";
		}
		if (telefonText.getText().contentEquals("")) {
			errorString += "Telefon je obavezan podatak!\n";
		}
		
		if(errorString.contentEquals("")) {
			Alert uspjeh = new Alert(AlertType.INFORMATION);
			uspjeh.setTitle(uspjeh.getAlertType().name());
			uspjeh.setHeaderText("Podaci uspjesno uneseni!");
			OptionalLong maxId = PoslovniKorisniciController.korisnici.stream().mapToLong(Entitet::getId).max();
			Long idKorisnik = maxId.getAsLong()+1;
			try {
				BazaPodataka.pohraniNovogPoslovnogKorisnika(new PoslovniKorisnik(nazivText.getText(), webText.getText(), emailText.getText(), telefonText.getText(), idKorisnik));
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

	}
}
