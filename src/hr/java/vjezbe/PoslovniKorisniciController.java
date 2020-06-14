package hr.java.vjezbe;

import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PoslovniKorisniciController {
		
	@FXML
	private Button pretrazi;
	@FXML
	private TextField nazivText;
	@FXML
	private TextField webText;
	@FXML
	private TextField emailText;
	@FXML
	private TextField telefonText;
	
			
	@FXML
	private TableView<PoslovniKorisnik> tablica;
	
	static List<PoslovniKorisnik> korisnici;
	
	@FXML
	private TableColumn<PoslovniKorisnik, String> nazivKorisnikColumn;
	@FXML
	private TableColumn<PoslovniKorisnik, String> webKorisnikColumn;
	@FXML
	private TableColumn<PoslovniKorisnik, String> emailKorisnikColumn;
	@FXML
	private TableColumn<PoslovniKorisnik, String> telefonKorisnikColumn;


	
	
	@FXML
	public void prikaziKorisnike() {
		List<PoslovniKorisnik> privLista = korisnici.stream()
				.filter(a -> a.getNaziv().toLowerCase().contains(nazivText.getText().toLowerCase()))
				.filter(a -> a.getWeb().toLowerCase().contains(webText.getText().toLowerCase()))
				.filter(a -> a.getEmail().toLowerCase().contains(emailText.getText().toLowerCase()))
				.filter(a -> a.getTelefon().toLowerCase().contains(telefonText.getText().toLowerCase()))
				.collect(Collectors.toList());

        
		ObservableList<PoslovniKorisnik> ObsLista = FXCollections.observableArrayList(privLista);
		tablica.setItems(ObsLista);	
	}
	
	
	@FXML
	public void initialize() {
		
		try {
			korisnici = BazaPodataka.dohvatiPoslovneKorisnikePremaKriterijima(null);
			nazivKorisnikColumn.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("naziv"));
			webKorisnikColumn.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("web"));
			emailKorisnikColumn.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("email"));
			telefonKorisnikColumn.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("telefon"));
			
			ObservableList<PoslovniKorisnik> listaKorisnika = FXCollections.observableArrayList(korisnici);
			
			
			tablica.setItems(listaKorisnika);
		} catch (BazaPodatakaException e) {
			e.printStackTrace();
		}
		
		
	}
}
