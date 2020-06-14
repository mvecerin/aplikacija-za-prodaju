package hr.java.vjezbe;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AutomobiliController {
	
	@FXML
	private Button pretrazi;
	@FXML
	private TextField naslovText;
	@FXML
	private TextField opisText;
	@FXML
	private TextField snagaKsText;
	@FXML
	private TextField cijenaText;
	
			
	@FXML
	private TableView<Automobil> tablica;
	
	static List<Automobil> automobili;
	
	@FXML
	private TableColumn<Automobil, String> naslovAutaColumn;
	@FXML
	private TableColumn<Automobil, String> opisAutaColumn;
	@FXML
	private TableColumn<Automobil, BigDecimal> snagaAutaColumn;
	@FXML
	private TableColumn<Automobil, BigDecimal> cijenaAutaColumn;
	@FXML
	private TableColumn<Automobil, Stanje> stanjeAutaColumn;

	
	
	@FXML
	public void prikaziAutomobile() {

		List<Automobil> privLista = automobili.stream()
				.filter(a -> a.getNaslov().toLowerCase().contains(naslovText.getText().toLowerCase()))
				.filter(a -> a.getOpis().toLowerCase().contains(opisText.getText().toLowerCase()))
				.filter(a -> snagaKsText.getText().length()!= 0 ? a.getSnagaKs().compareTo(new BigDecimal(snagaKsText.getText()))==0 :  true)
				.filter(a -> cijenaText.getText().length()!= 0 ? a.getCijena().compareTo(new BigDecimal(cijenaText.getText()))==0 :  true)
				.collect(Collectors.toList());

        
		ObservableList<Automobil> ObsLista = FXCollections.observableArrayList(privLista);
		tablica.setItems(ObsLista);	
	}
	
	
	@FXML
	public void initialize() {
		
		try {
			automobili = BazaPodataka.dohvatiAutomobilePremaKriterijima(null);
			naslovAutaColumn.setCellValueFactory(new PropertyValueFactory<Automobil, String>("naslov"));
			opisAutaColumn.setCellValueFactory(new PropertyValueFactory<Automobil, String>("opis"));
			cijenaAutaColumn.setCellValueFactory(new PropertyValueFactory<Automobil, BigDecimal>("cijena"));
			snagaAutaColumn.setCellValueFactory(new PropertyValueFactory<Automobil, BigDecimal>("snagaKs"));
			stanjeAutaColumn.setCellValueFactory(new PropertyValueFactory<Automobil, Stanje>("stanje"));
			
			ObservableList<Automobil> listaAuta = FXCollections.observableArrayList(automobili);
		
			tablica.setItems(listaAuta);
		} 
		catch (BazaPodatakaException e) {
			e.printStackTrace();
		}
		
		
	}

}
