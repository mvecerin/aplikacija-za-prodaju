package hr.java.vjezbe;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UslugeController {
	@FXML
	private Button pretrazi;
	@FXML
	private TextField naslovText;
	@FXML
	private TextField opisText;
	@FXML
	private TextField cijenaText;

	
			
	@FXML
	private TableView<Usluga> tablica;
	
	static List<Usluga> usluge;
	
	@FXML
	private TableColumn<Usluga, String> naslovUslugaColumn;
	@FXML
	private TableColumn<Usluga, String> opisUslugaColumn;
	@FXML
	private TableColumn<Usluga, BigDecimal> cijenaUslugaColumn;
	@FXML
	private TableColumn<Usluga, Stanje> stanjeUslugaColumn;

	
	@FXML
	public void prikaziUsluge() {

		List<Usluga> privLista = usluge.stream()
				.filter(a -> a.getNaslov().toLowerCase().contains(naslovText.getText().toLowerCase()))
				.filter(a -> a.getOpis().toLowerCase().contains(opisText.getText().toLowerCase()))
				.filter(a -> cijenaText.getText().length()!= 0 ? a.getCijena().compareTo(new BigDecimal(cijenaText.getText()))==0 : true)
				.map(a -> a)
				.collect(Collectors.toList());

        
		ObservableList<Usluga> ObsLista = FXCollections.observableArrayList(privLista);
		tablica.setItems(ObsLista);	
	}
	
	
	@FXML
	public void initialize() {
		
		try {
			usluge = BazaPodataka.dohvatiUslugePremaKriterijima(null);
			naslovUslugaColumn.setCellValueFactory(new PropertyValueFactory<Usluga, String>("naslov"));
			opisUslugaColumn.setCellValueFactory(new PropertyValueFactory<Usluga, String>("opis"));
			cijenaUslugaColumn.setCellValueFactory(new PropertyValueFactory<Usluga, BigDecimal>("cijena"));
			stanjeUslugaColumn.setCellValueFactory(new PropertyValueFactory<Usluga, Stanje>("stanje"));


			ObservableList<Usluga> listaUsluga = FXCollections.observableArrayList(usluge);

			tablica.setItems(listaUsluga);
			
		} catch (BazaPodatakaException e) {
			e.printStackTrace();
		}
		
		
	}
}
