package hr.java.vjezbe;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProdajeController {
	@FXML
	private Button pretrazi;
	@FXML
	private ComboBox<Artikl> artiklCombo;
	@FXML
	private ComboBox<Korisnik> korisnikCombo;
	@FXML
	private DatePicker datumPicker;

	
			
	@FXML
	private TableView<Prodaja> tablica;
	
	static List<Prodaja> prodaje;
	
	@FXML
	private TableColumn<Prodaja, Artikl> artiklProdajaColumn;
	@FXML
	private TableColumn<Prodaja, Korisnik> korisnikProdajaColumn;
	@FXML
	private TableColumn<Prodaja, LocalDate> datumProdajaColumn;

	
	@FXML
	public void prikaziProdaje() {

		List<Prodaja> privLista = prodaje.stream()
				.filter(a -> {if(artiklCombo.getValue().equals(null)){
						return true;
					}
				return a.getArtikl().toString().contentEquals(artiklCombo.getValue().toString());
					
				})
				.filter(a -> {if(korisnikCombo.getValue().equals(null)){

						return true;
					}
					return a.getKorisnik().toString().contentEquals(korisnikCombo.getValue().toString());
					
				})
//				.filter(a -> artiklCombo.getValue().equals(null) ? true : a.getArtikl().toString().contentEquals(artiklCombo.getValue().toString()))
//				.filter(a -> korisnikCombo.getValue().equals(null) ? true : a.getKorisnik().toString().contentEquals(korisnikCombo.getValue().toString()))
//				.filter(a -> datumPicker.equals(null) ? true : a.getDatumObjave().isEqual(datumPicker.getValue()))
				.filter(a -> {if(datumPicker.getValue().equals(null)){
						return true;
					}
					return a.getDatumObjave().toString().contentEquals(datumPicker.getValue().toString());
				})
				.collect(Collectors.toList());

		ObservableList<Prodaja> ObsLista = FXCollections.observableArrayList(privLista);
		tablica.setItems(ObsLista);	
	}
	
	
	@FXML
	public void initialize() {
		
		try {
			prodaje = BazaPodataka.dohvatiProdajuPremaKriterijima(null);
			artiklProdajaColumn.setCellValueFactory(new PropertyValueFactory<Prodaja, Artikl>("artikl"));
			korisnikProdajaColumn.setCellValueFactory(new PropertyValueFactory<Prodaja, Korisnik>("korisnik"));
			datumProdajaColumn.setCellValueFactory(new PropertyValueFactory<Prodaja, LocalDate>("datumObjave"));

			ObservableList<Prodaja> listaProdaja = FXCollections.observableArrayList(prodaje);
			
			artiklCombo.getItems().setAll(BazaPodataka.dohvatiArtikle());			
			korisnikCombo.getItems().setAll(BazaPodataka.dohvatiKorisnike());

			
			tablica.setItems(listaProdaja);
			
		} catch (BazaPodatakaException e) {
			e.printStackTrace();
		}
		
		
	}
}
