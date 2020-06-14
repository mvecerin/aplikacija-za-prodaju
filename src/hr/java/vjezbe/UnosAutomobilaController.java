package hr.java.vjezbe;

import java.math.BigDecimal;
import java.util.OptionalLong;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class UnosAutomobilaController {
	
	@FXML
	private TextField naslovText;
	@FXML
	private TextField opisText;
	@FXML
	private TextField snagaKsText;
	@FXML
	private TextField cijenaText;
	@FXML 
	private ComboBox<Stanje> stanjeCombo; 
		
	@FXML
	public void unesi() {
		
		String errorString = new String();
		if (naslovText.getText().contentEquals("")) {
			errorString += "Naslov je obavezan podatak!\n";
		}
		if (opisText.getText().contentEquals("")) {
			errorString += "Opis je obavezan podatak!\n";
		}
		if (snagaKsText.getText().contentEquals("")) {
			errorString += "Snaga je obavezan podatak!\n";
		}
		if (cijenaText.getText().contentEquals("")) {
			errorString += "Cijena je obavezan podatak!\n";
		}
		
		if(errorString.contentEquals("")) {
			Alert uspjeh = new Alert(AlertType.INFORMATION);
			uspjeh.setTitle(uspjeh.getAlertType().name());
			uspjeh.setHeaderText("Podaci uspjesno uneseni!");
			try {
				AutomobiliController.automobili = BazaPodataka.dohvatiAutomobilePremaKriterijima(null);
				OptionalLong maxId = AutomobiliController.automobili.stream().mapToLong(Entitet::getId).max();
				Long idArtikl = maxId.getAsLong()+1;
				BazaPodataka.pohraniNoviAutomobil(new Automobil(naslovText.getText(), opisText.getText(), 
						new BigDecimal(cijenaText.getText()), new BigDecimal(snagaKsText.getText()), stanjeCombo.getValue(), idArtikl));
			} catch (BazaPodatakaException e1) {
				e1.printStackTrace();
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

		stanjeCombo.getItems().addAll(Stanje.values());
		stanjeCombo.setValue(Stanje.izvrsno);
	}
}
