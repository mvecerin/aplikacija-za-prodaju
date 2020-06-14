package hr.java.vjezbe;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

import javafx.scene.layout.BorderPane;

public class PocetnaController {
	
	
	public void prikaziPretraguAutomobila() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("Automobili.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziPretraguStana() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("Stanovi.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziPretraguUsluga() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("Usluge.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziPretraguPrivatnihKorisnika() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("PrivatniKorisnici.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziPretraguPoslovnihKorisnika() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("PoslovniKorisnici.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziPretraguProdaje() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("Prodaje.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void unosAutomobila() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosAutomobila.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void unosStana() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosStana.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void unosUsluga() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosUsluge.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void unosPrivatnihKorisnika() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosPrivatnogKorisnika.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void unosPoslovnihKorisnika() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosPoslovnogKorisnika.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void unosProdaje() {
		try {
			BorderPane center = FXMLLoader.load(getClass().getResource("UnosProdaje.fxml"));
			Main.setCenterPane(center);	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
