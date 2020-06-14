package hr.java.vjezbe;

import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrivatniKorisniciController {
		
	@FXML
	private Button pretrazi;
	@FXML
	private TextField imeText;
	@FXML
	private TextField prezimeText;
	@FXML
	private TextField emailText;
	@FXML
	private TextField telefonText;
	
			
	@FXML
	private TableView<PrivatniKorisnik> tablica;
	
	static List<PrivatniKorisnik> korisnici;
	
	@FXML
	private TableColumn<PrivatniKorisnik, String> imeKorisnikColumn;
	@FXML
	private TableColumn<PrivatniKorisnik, String> prezimeKorisnikColumn;
	@FXML
	private TableColumn<PrivatniKorisnik, String> emailKorisnikColumn;
	@FXML
	private TableColumn<PrivatniKorisnik, String> telefonKorisnikColumn;


	
	
	@FXML
	public void prikaziKorisnike() {
		List<PrivatniKorisnik> privLista = korisnici.stream()
				.filter(a -> a.getIme().toLowerCase().contains(imeText.getText().toLowerCase()))
				.filter(a -> a.getPrezime().toLowerCase().contains(prezimeText.getText().toLowerCase()))
				.filter(a -> a.getEmail().toLowerCase().contains(emailText.getText().toLowerCase()))
				.filter(a -> a.getTelefon().toLowerCase().contains(telefonText.getText().toLowerCase()))
				.collect(Collectors.toList());

        
		ObservableList<PrivatniKorisnik> ObsLista = FXCollections.observableArrayList(privLista);
		tablica.setItems(ObsLista);	
	}
	
	
	@FXML
	public void initialize() {
		
		try {
			korisnici = BazaPodataka.dohvatiPrivatneKorisnikePremaKriterijima(null);
		} catch (BazaPodatakaException e) {
			e.printStackTrace();
		}
		
		imeKorisnikColumn.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("ime"));
		prezimeKorisnikColumn.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("prezime"));
		emailKorisnikColumn.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("email"));
		telefonKorisnikColumn.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("telefon"));
		
		ObservableList<PrivatniKorisnik> listaKorisnika = FXCollections.observableArrayList(korisnici);
		
		
		tablica.setItems(listaKorisnika);
	}
}
