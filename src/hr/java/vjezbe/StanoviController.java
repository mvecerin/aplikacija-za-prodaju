package hr.java.vjezbe;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.entitet.Stan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class StanoviController {
		
	@FXML
	private Button pretrazi;
	@FXML
	private TextField naslovText;
	@FXML
	private TextField opisText;
	@FXML
	private TextField kvadraturaText;
	@FXML
	private TextField cijenaText;
	
			
	@FXML
	private TableView<Stan> tablica;
	
	static List<Stan> stanovi;
	
	@FXML
	private TableColumn<Stan, String> naslovStanColumn;
	@FXML
	private TableColumn<Stan, String> opisStanColumn;
	@FXML
	private TableColumn<Stan, BigDecimal> cijenaStanColumn;
	@FXML
	private TableColumn<Stan, Integer> kvadraturaStanColumn;
	@FXML
	private TableColumn<Stan, Stanje> stanjeStanColumn;

	
	
	@FXML
	public void prikaziStanove() {
		List<Stan> privLista = stanovi.stream()
				.filter(a -> a.getNaslov().toLowerCase().contains(naslovText.getText().toLowerCase()))
				.filter(a -> a.getOpis().toLowerCase().contains(opisText.getText().toLowerCase()))
				.filter(a -> cijenaText.getText().length()!= 0 ? a.getCijena().compareTo(new BigDecimal(cijenaText.getText()))==0 :  true)
				.filter(a -> kvadraturaText.getText().length()!= 0 ? a.getKvadratura() == Integer.parseInt(kvadraturaText.getText()) :  true)
				.collect(Collectors.toList());

        
		ObservableList<Stan> ObsLista = FXCollections.observableArrayList(privLista);
		tablica.setItems(ObsLista);	
	}

	@FXML
	public void initialize() {
		
		try {
			stanovi = BazaPodataka.dohvatiStanovePremaKriterijima(null);
		} catch (BazaPodatakaException e) {
			e.printStackTrace();
		}
		
		naslovStanColumn.setCellValueFactory(new PropertyValueFactory<Stan, String>("naslov"));
		opisStanColumn.setCellValueFactory(new PropertyValueFactory<Stan, String>("opis"));
		kvadraturaStanColumn.setCellValueFactory(new PropertyValueFactory<Stan, Integer>("kvadratura"));
		cijenaStanColumn.setCellValueFactory(new PropertyValueFactory<Stan, BigDecimal>("cijena"));
		stanjeStanColumn.setCellValueFactory(new PropertyValueFactory<Stan, Stanje>("stanje"));
		
		ObservableList<Stan> listaStan = FXCollections.observableArrayList(stanovi);
		
		
		tablica.setItems(listaStan);

	}
}
