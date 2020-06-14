package hr.java.vjezbe.baza;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

public class BazaPodataka {
	
	private static final String DATABASE_FILE = "dat/database.properties";
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);
	
	private static Connection spajanjeNaBazu() throws SQLException, IOException{
		
		Properties svojstva = new Properties();
		svojstva.load(new FileReader(DATABASE_FILE));
		String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
		String korisnickoIme = svojstva.getProperty("korisnickoIme");
		String lozinka = svojstva.getProperty("lozinka");
		Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme,lozinka);
		return veza;
	}
	
	//PRODAJA
	public static List<Prodaja> dohvatiProdajuPremaKriterijima(Prodaja prodaja) throws BazaPodatakaException {
		List<Prodaja> listaProdaje = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
			"select distinct korisnik.id as idKorisnika, tipKorisnika.naziv as tipKorisnika, \r\n"+
			"korisnik.naziv as nazivKorisnika, web, email, telefon, \r\n" +
			"korisnik.ime, korisnik.prezime, tipArtikla.naziv as tipArtikla,\r\n" +
			"artikl.naslov, artikl.opis, artikl.cijena, artikl.kvadratura,\r\n" +
			"artikl.snaga, stanje.naziv as stanje, prodaja.datumObjave, artikl.id as idArtikla\r\n"+
			"from korisnik inner join \r\n" +
			"tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika inner join\r\n" +
			"prodaja on prodaja.idKorisnik = korisnik.id inner join\r\n" +
			"artikl on artikl.id = prodaja.idArtikl inner join\r\n" +
			"tipArtikla on tipArtikla.id = artikl.idTipArtikla inner join\r\n" +
			"stanje on stanje.id = artikl.idStanje where 1=1");
			if (Optional.ofNullable(prodaja).isEmpty() == false) {
				if (Optional.ofNullable(prodaja.getArtikl()).isPresent())
					sqlUpit.append(" AND prodaja.idArtikl = " + prodaja.getArtikl().getId());
				if (Optional.ofNullable(prodaja.getKorisnik()).isPresent())
					sqlUpit.append(" AND prodaja.idArtikl = " + prodaja.getKorisnik().getId());
				if (Optional.ofNullable(prodaja.getDatumObjave()).isPresent()) {
					sqlUpit.append(" AND prodaja.datumObjave = '" + prodaja.getDatumObjave().format(DateTimeFormatter.ISO_DATE) + "'");
			}
		}
		Statement query = connection.createStatement();
		ResultSet resultSet = query.executeQuery(sqlUpit.toString());
		while (resultSet.next()) {
			Korisnik korisnik = null;
			if(resultSet.getString("tipKorisnika").equals("PrivatniKorisnik")) {
				korisnik = new PrivatniKorisnik(resultSet.getString("ime"),
				resultSet.getString("prezime"),
				resultSet.getString("email"),
				resultSet.getString("telefon"),
				resultSet.getLong("idKorisnika"));
			}
			else if(resultSet.getString("tipKorisnika").equals("PoslovniKorisnik")) {
				korisnik = new PoslovniKorisnik(
				resultSet.getString("nazivKorisnika"),
				resultSet.getString("web"),
				resultSet.getString("telefon"),
				resultSet.getString("email"),
				resultSet.getLong("idKorisnika"));
			}
			Artikl artikl = null;
			if(resultSet.getString("tipArtikla").equals("Automobil")) {
				artikl = new Automobil(resultSet.getString("naslov"),
				resultSet.getString("opis"),
				resultSet.getBigDecimal("cijena"),
				resultSet.getBigDecimal("snaga"),
				Stanje.valueOf(resultSet.getString("stanje")),
				resultSet.getLong("idArtikla"));
			}
			else if(resultSet.getString("tipArtikla").equals("Usluga")) {
				artikl = new Usluga(resultSet.getString("naslov"),
				resultSet.getString("opis"),
				resultSet.getBigDecimal("cijena"),
				Stanje.valueOf(resultSet.getString("stanje")),
				resultSet.getLong("idArtikla"));
			}
			else if(resultSet.getString("tipArtikla").equals("Stan")) {
				artikl = new Stan(resultSet.getString("naslov"),
				resultSet.getString("opis"),
				resultSet.getBigDecimal("cijena"),
				resultSet.getInt("kvadratura"),
				Stanje.valueOf(resultSet.getString("stanje")),
				resultSet.getLong("idArtikla"));
			}
			Prodaja novaProdaja = new Prodaja(artikl, korisnik,
			resultSet.getTimestamp("datumObjave").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			listaProdaje.add(novaProdaja);
			}
		} 
		catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return listaProdaje;
	}
	
	public static void pohraniNovuProdaju(Prodaja prodaja) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza.prepareStatement(
			"insert into prodaja(idArtikl, idKorisnik, datumObjave) " +
			"values (?, ?, ?);");
			preparedStatement.setLong(1, prodaja.getArtikl().getId());
			preparedStatement.setLong(2, prodaja.getKorisnik().getId());
			preparedStatement.setObject(3, prodaja.getDatumObjave());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException | IOException ex) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}
	
	public static List<Korisnik> dohvatiKorisnike(){
		List<Korisnik> listaKorisnika = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct korisnik.id as idKorisnika, korisnik.naziv, web, email, telefon, ime, prezime, tipKorisnika.naziv as tipKorisnika\n" + 
					"from korisnik inner join tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika");
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Korisnik korisnik = null;
				if(resultSet.getString("tipKorisnika").equals("PrivatniKorisnik")) {
					korisnik = new PrivatniKorisnik(resultSet.getString("ime"),
					resultSet.getString("prezime"),
					resultSet.getString("email"),
					resultSet.getString("telefon"),
					resultSet.getLong("idKorisnika"));
				}
				else if(resultSet.getString("tipKorisnika").equals("PoslovniKorisnik")) {
					korisnik = new PoslovniKorisnik(
					resultSet.getString("korisnik.naziv"),
					resultSet.getString("web"),
					resultSet.getString("telefon"),
					resultSet.getString("email"),
					resultSet.getLong("idKorisnika"));
				}
				listaKorisnika.add(korisnik);
			} 
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaKorisnika;
	}
	
	public static List<Artikl> dohvatiArtikle(){
		List<Artikl> listaArtikala = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct artikl.id as idArtikla, naslov, opis, cijena, snaga, kvadratura, stanje.naziv as stanje, tipArtikla.naziv as tipArtikla\n" + 
					"FROM artikl inner join stanje on stanje.id = artikl.idStanje inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla");
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Artikl artikl = null;
				if(resultSet.getString("tipArtikla").equals("Automobil")) {
					artikl = new Automobil(resultSet.getString("naslov"),
					resultSet.getString("opis"),
					resultSet.getBigDecimal("cijena"),
					resultSet.getBigDecimal("snaga"),
					Stanje.valueOf(resultSet.getString("stanje")),
					resultSet.getLong("idArtikla"));
				}
				else if(resultSet.getString("tipArtikla").equals("Usluga")) {
					artikl = new Usluga(resultSet.getString("naslov"),
					resultSet.getString("opis"),
					resultSet.getBigDecimal("cijena"),
					Stanje.valueOf(resultSet.getString("stanje")),
					resultSet.getLong("idArtikla"));
				}
				else if(resultSet.getString("tipArtikla").equals("Stan")) {
					artikl = new Stan(resultSet.getString("naslov"),
					resultSet.getString("opis"),
					resultSet.getBigDecimal("cijena"),
					resultSet.getInt("kvadratura"),
					Stanje.valueOf(resultSet.getString("stanje")),
					resultSet.getLong("idArtikla"));
				}
				
				listaArtikala.add(artikl);
			} 
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaArtikala;
	}
	
	
	//STAN
	public static List<Stan> dohvatiStanovePremaKriterijima(Stan stan) throws BazaPodatakaException {
		List<Stan> listaStanova = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
			"SELECT distinct artikl.id, naslov, opis, cijena, kvadratura, stanje.naziv "
			+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
			+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Stan'");
			if (Optional.ofNullable(stan).isEmpty() == false) {
				if (Optional.ofNullable(stan).map(Stan::getId).isPresent())
				sqlUpit.append(" AND artikl.id = " + stan.getId());
				if (Optional.ofNullable(stan.getNaslov()).map(String::isBlank).orElse(true) == false)
				sqlUpit.append(" AND artikl.naslov LIKE '%" + stan.getNaslov() + "%'");
				if (Optional.ofNullable(stan.getOpis()).map(String::isBlank).orElse(true) == false)
				sqlUpit.append(" AND artikl.opis LIKE '%" + stan.getOpis() + "%'");
				if (Optional.ofNullable(stan).map(Stan::getCijena).isPresent())
				sqlUpit.append(" AND artikl.cijena = " + stan.getCijena());
				if (Optional.ofNullable(stan).map(Stan::getKvadratura).isPresent())
				sqlUpit.append(" AND artikl.kvadratura = " + stan.getKvadratura());
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				Integer kvadratura = resultSet.getInt("kvadratura");
				String stanje = resultSet.getString("naziv");
				Stan newStan = new Stan(naslov, opis, cijena, kvadratura, Stanje.valueOf(stanje), id);
				listaStanova.add(newStan);
			}
		} 
		catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		} 
		return listaStanova;
	}
		public static void pohraniNoviStan(Stan stan) throws BazaPodatakaException {
			try (Connection veza = spajanjeNaBazu()) {
				PreparedStatement preparedStatement = veza.prepareStatement(
				"insert into artikl(Naslov, Opis, Cijena, Kvadratura, idStanje, idTipArtikla) " +
				"values (?, ?, ?, ?, ?, 3);");
				preparedStatement.setString(1, stan.getNaslov());
				preparedStatement.setString(2, stan.getOpis());
				preparedStatement.setBigDecimal(3, stan.getCijena());
				preparedStatement.setInt(4, stan.getKvadratura());
				preparedStatement.setLong(5, (stan.getStanje().ordinal() + 1));
				preparedStatement.executeUpdate();
			} 
			catch (SQLException | IOException ex) {
				String poruka = "Došlo je do pogreške u radu s bazom podataka";
				logger.error(poruka, ex);
				throw new BazaPodatakaException(poruka, ex);
			}
		}
		
		//AUTOMOBIL
		public static List<Automobil> dohvatiAutomobilePremaKriterijima(Automobil automobil) throws BazaPodatakaException {
			List<Automobil> listaAutomobila = new ArrayList<>();
			try (Connection connection = spajanjeNaBazu()) {
				StringBuilder sqlUpit = new StringBuilder(
				"SELECT distinct artikl.id, naslov, opis, cijena, snaga, stanje.naziv\n" + 
				"FROM artikl inner join stanje on stanje.id = artikl.idStanje\n" + 
				"inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv =\n" + "'Automobil'");
				if (Optional.ofNullable(automobil).isEmpty() == false) {
					if (Optional.ofNullable(automobil).map(Automobil::getId).isPresent())
					sqlUpit.append(" AND artikl.id = " + automobil.getId());
					if (Optional.ofNullable(automobil.getNaslov()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.naslov LIKE '%" + automobil.getNaslov() + "%'");
					if (Optional.ofNullable(automobil.getOpis()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.opis LIKE '%" + automobil.getOpis() + "%'");
					if (Optional.ofNullable(automobil).map(Automobil::getCijena).isPresent())
					sqlUpit.append(" AND artikl.cijena = " + automobil.getCijena());
					if (Optional.ofNullable(automobil).map(Automobil::getSnagaKs).isPresent())
					sqlUpit.append(" AND artikl.snaga = " + automobil.getSnagaKs());
				}
				Statement query = connection.createStatement();
				ResultSet resultSet = query.executeQuery(sqlUpit.toString());
				while (resultSet.next()) {
					Long id = resultSet.getLong("id");
					String naslov = resultSet.getString("naslov");
					String opis = resultSet.getString("opis");
					BigDecimal cijena = resultSet.getBigDecimal("cijena");
					BigDecimal snagaKs = resultSet.getBigDecimal("snaga");
					String stanje = resultSet.getString("naziv");
					Automobil newAutomobil = new Automobil(naslov, opis, cijena, snagaKs, Stanje.valueOf(stanje), id);
					listaAutomobila.add(newAutomobil);
				}
			} 
			catch (SQLException | IOException e) {
				String poruka = "Došlo je do pogreške u radu s bazom podataka";
				logger.error(poruka, e);
				throw new BazaPodatakaException(poruka, e);
			} 
			return listaAutomobila;
		}
		
		public static void pohraniNoviAutomobil(Automobil automobil) throws BazaPodatakaException {
			try (Connection veza = spajanjeNaBazu()) {
				PreparedStatement preparedStatement = veza.prepareStatement(
				"insert into artikl(Naslov, Opis, Cijena, Snaga, idStanje, idTipArtikla) " +
				"values (?, ?, ?, ?, ?, 1);");
				preparedStatement.setString(1, automobil.getNaslov());
				preparedStatement.setString(2, automobil.getOpis());
				preparedStatement.setBigDecimal(3, automobil.getCijena());
				preparedStatement.setBigDecimal(4, automobil.getSnagaKs());
				preparedStatement.setLong(5, (automobil.getStanje().ordinal() + 1));
				preparedStatement.executeUpdate();
			} 
			catch (SQLException | IOException ex) {
				String poruka = "Došlo je do pogreške u radu s bazom podataka";
				logger.error(poruka, ex);
				throw new BazaPodatakaException(poruka, ex);
			}
		}
		
		//USLUGA
		public static List<Usluga> dohvatiUslugePremaKriterijima(Usluga usluga) throws BazaPodatakaException {
			List<Usluga> listaUsluga = new ArrayList<>();
			try (Connection connection = spajanjeNaBazu()) {
				StringBuilder sqlUpit = new StringBuilder(
				"SELECT distinct artikl.id, naslov, opis, cijena, stanje.naziv\n" + 
				"FROM artikl inner join stanje on stanje.id = artikl.idStanje\n" + 
				"inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv =\n" + "'Usluga'");
				if (Optional.ofNullable(usluga).isEmpty() == false) {
					if (Optional.ofNullable(usluga).map(Usluga::getId).isPresent())
					sqlUpit.append(" AND artikl.id = " + usluga.getId());
					if (Optional.ofNullable(usluga.getNaslov()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.naslov LIKE '%" + usluga.getNaslov() + "%'");
					if (Optional.ofNullable(usluga.getOpis()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.opis LIKE '%" + usluga.getOpis() + "%'");
					if (Optional.ofNullable(usluga).map(Usluga::getCijena).isPresent())
					sqlUpit.append(" AND artikl.cijena = " + usluga.getCijena());
				}
				Statement query = connection.createStatement();
				ResultSet resultSet = query.executeQuery(sqlUpit.toString());
				while (resultSet.next()) {
					Long id = resultSet.getLong("id");
					String naslov = resultSet.getString("naslov");
					String opis = resultSet.getString("opis");
					BigDecimal cijena = resultSet.getBigDecimal("cijena");
					String stanje = resultSet.getString("naziv");
					Usluga newUsluga = new Usluga(naslov, opis, cijena, Stanje.valueOf(stanje), id);
					listaUsluga.add(newUsluga);
				}
			} 
			catch (SQLException | IOException e) {
				String poruka = "Došlo je do pogreške u radu s bazom podataka";
				logger.error(poruka, e);
				throw new BazaPodatakaException(poruka, e);
			} 
			return listaUsluga;
		}
		
		public static void pohraniNovuUslugu(Usluga usluga) throws BazaPodatakaException {
			try (Connection veza = spajanjeNaBazu()) {
				PreparedStatement preparedStatement = veza.prepareStatement(
				"insert into artikl(Naslov, Opis, Cijena, idStanje, idTipArtikla) " +
				"values (?, ?, ?, ?, 2);");
				preparedStatement.setString(1, usluga.getNaslov());
				preparedStatement.setString(2, usluga.getOpis());
				preparedStatement.setBigDecimal(3, usluga.getCijena());
				preparedStatement.setLong(4, (usluga.getStanje().ordinal() + 1));
				preparedStatement.executeUpdate();
			} 
			catch (SQLException | IOException ex) {
				String poruka = "Došlo je do pogreške u radu s bazom podataka";
				logger.error(poruka, ex);
				throw new BazaPodatakaException(poruka, ex);
			}
		}
		//PRIVATNI KORISNIK
				public static List<PrivatniKorisnik> dohvatiPrivatneKorisnikePremaKriterijima(PrivatniKorisnik korisnik) throws BazaPodatakaException {
					List<PrivatniKorisnik> listaKorisnika = new ArrayList<>();
					try (Connection connection = spajanjeNaBazu()) {
						StringBuilder sqlUpit = new StringBuilder(
						"select distinct korisnik.id, ime, prezime, email, telefon\n" + 
						"from korisnik inner join tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika\n" + 
						"where tipKorisnika.naziv = 'PrivatniKorisnik'");
						if (Optional.ofNullable(korisnik).isEmpty() == false) {
							if (Optional.ofNullable(korisnik).map(PrivatniKorisnik::getId).isPresent())
							sqlUpit.append(" AND korisnik.id = " + korisnik.getId());
							if (Optional.ofNullable(korisnik.getIme()).map(String::isBlank).orElse(true) == false)
							sqlUpit.append(" AND korisnik.ime LIKE '%" + korisnik.getIme() + "%'");
							if (Optional.ofNullable(korisnik.getPrezime()).map(String::isBlank).orElse(true) == false)
							sqlUpit.append(" AND korisnik.prezime LIKE '%" + korisnik.getPrezime() + "%'");
							if (Optional.ofNullable(korisnik.getEmail()).map(String::isBlank).orElse(true) == false)
							sqlUpit.append(" AND korisnik.email LIKE '%" + korisnik.getEmail() + "%'");
							if (Optional.ofNullable(korisnik.getTelefon()).map(String::isBlank).orElse(true) == false)
							sqlUpit.append(" AND korisnik.telefon LIKE '%" + korisnik.getTelefon() + "%'");
						}
						Statement query = connection.createStatement();
						ResultSet resultSet = query.executeQuery(sqlUpit.toString());
						while (resultSet.next()) {
							Long id = resultSet.getLong("id");
							String ime = resultSet.getString("ime");
							String prezime = resultSet.getString("prezime");
							String email = resultSet.getString("email");
							String telefon = resultSet.getString("telefon");
							PrivatniKorisnik newKorisnik = new PrivatniKorisnik(ime, prezime, email, telefon, id);
							listaKorisnika.add(newKorisnik);
						}
					} 
					catch (SQLException | IOException e) {
						String poruka = "Došlo je do pogreške u radu s bazom podataka";
						logger.error(poruka, e);
						throw new BazaPodatakaException(poruka, e);
					} 
					return listaKorisnika;
				}
				
				public static void pohraniNovogPrivatnogKorisnika(PrivatniKorisnik korisnik) throws BazaPodatakaException {
					try (Connection veza = spajanjeNaBazu()) {
						PreparedStatement preparedStatement = veza.prepareStatement(
						"insert into korisnik(Ime, Prezime, Email, Telefon, idTipKorisnika) " +
						"values (?, ?, ?, ?, 1);");
						preparedStatement.setString(1, korisnik.getIme());
						preparedStatement.setString(2, korisnik.getPrezime());
						preparedStatement.setString(3, korisnik.getEmail());
						preparedStatement.setString(4, korisnik.getTelefon());
						preparedStatement.executeUpdate();
					} 
					catch (SQLException | IOException ex) {
						String poruka = "Došlo je do pogreške u radu s bazom podataka";
						logger.error(poruka, ex);
						throw new BazaPodatakaException(poruka, ex);
					}
				}
				//POSLOVNI KORISNIK
				public static List<PoslovniKorisnik> dohvatiPoslovneKorisnikePremaKriterijima(PoslovniKorisnik korisnik) throws BazaPodatakaException {
					List<PoslovniKorisnik> listaKorisnika = new ArrayList<>();
					try (Connection connection = spajanjeNaBazu()) {
						StringBuilder sqlUpit = new StringBuilder(
						"select distinct korisnik.id, korisnik.naziv, web, email, telefon\n" + 
						"from korisnik inner join tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika\n" + 
						"where tipKorisnika.naziv = 'PoslovniKorisnik'");
						if (Optional.ofNullable(korisnik).isEmpty() == false) {
							if (Optional.ofNullable(korisnik).map(PoslovniKorisnik::getId).isPresent())
							sqlUpit.append(" AND korisnik.id = " + korisnik.getId());
							if (Optional.ofNullable(korisnik.getNaziv()).map(String::isBlank).orElse(true) == false)
							sqlUpit.append(" AND korisnik.naziv LIKE '%" + korisnik.getNaziv() + "%'");
							if (Optional.ofNullable(korisnik.getWeb()).map(String::isBlank).orElse(true) == false)
							sqlUpit.append(" AND korisnik.web LIKE '%" + korisnik.getWeb() + "%'");
							if (Optional.ofNullable(korisnik.getEmail()).map(String::isBlank).orElse(true) == false)
							sqlUpit.append(" AND korisnik.email LIKE '%" + korisnik.getEmail() + "%'");
							if (Optional.ofNullable(korisnik.getTelefon()).map(String::isBlank).orElse(true) == false)
							sqlUpit.append(" AND korisnik.telefon LIKE '%" + korisnik.getTelefon() + "%'");
						}
						Statement query = connection.createStatement();
						ResultSet resultSet = query.executeQuery(sqlUpit.toString());
						while (resultSet.next()) {
							Long id = resultSet.getLong("id");
							String naziv = resultSet.getString("naziv");
							String web = resultSet.getString("web");
							String email = resultSet.getString("email");
							String telefon = resultSet.getString("telefon");
							PoslovniKorisnik newKorisnik = new PoslovniKorisnik(naziv, web, email, telefon, id);
							listaKorisnika.add(newKorisnik);
						}
					} 
					catch (SQLException | IOException e) {
						String poruka = "Došlo je do pogreške u radu s bazom podataka";
						logger.error(poruka, e);
						throw new BazaPodatakaException(poruka, e);
					} 
					return listaKorisnika;
				}
				
				public static void pohraniNovogPoslovnogKorisnika(PoslovniKorisnik korisnik) throws BazaPodatakaException {
					try (Connection veza = spajanjeNaBazu()) {
						PreparedStatement preparedStatement = veza.prepareStatement(
						"insert into korisnik(Naziv, Web, Email, Telefon, idTipKorisnika) " +
						"values (?, ?, ?, ?, 2);");
						preparedStatement.setString(1, korisnik.getNaziv());
						preparedStatement.setString(2, korisnik.getWeb());
						preparedStatement.setString(3, korisnik.getEmail());
						preparedStatement.setString(4, korisnik.getTelefon());
						preparedStatement.executeUpdate();
					} 
					catch (SQLException | IOException ex) {
						String poruka = "Došlo je do pogreške u radu s bazom podataka";
						logger.error(poruka, ex);
						throw new BazaPodatakaException(poruka, ex);
					}
				}
}
