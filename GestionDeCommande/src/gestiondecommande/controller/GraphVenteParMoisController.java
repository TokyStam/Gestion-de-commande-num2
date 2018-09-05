/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import gestiondecommande.model.dao.ClientDao;
import gestiondecommande.model.dao.VenteDao;
import gestiondecommande.model.domain.Client;
import gestiondecommande.model.domain.ProduitDate;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class GraphVenteParMoisController implements Initializable {
    
    private ObservableList<Client> observableListClient;
    private List<Client> listeClients;
    private final ClientDao clientDao;
    
    private ObservableList<ProduitDate> observableListProduit;

    @FXML
    private BarChart barChartParMois;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private NumberAxis numberAxis;
    
    private ObservableList<String> observableListeMois  = FXCollections.observableArrayList();
    private final VenteDao venteDao;
    
    @FXML
    private ComboBox<Client> comboBoxClient;
    @FXML
    private DatePicker datePickerDebut;
    @FXML
    private DatePicker datePickerFin;
    @FXML
    private TableView tableViewListeProduit;
    @FXML
    private TableColumn tableColumnCodeVente;
    @FXML
    private TableColumn tableColumnCodePro;
    @FXML
    private TableColumn tableColumnDesignation;
    @FXML
    private TableColumn tableColumnQteCommande;
    @FXML
    private TableColumn tableColumnSousTotale;
    @FXML
    private TableColumn tableColumnDateVente;
    
    @FXML
    private ComboBox<Client> comboBoxClient2;
    @FXML
    private ComboBox<String> comboBoxPar;
    @FXML
    private DatePicker datePickerDatePar;
    String[] citereParMoisOuAnnee = {"Mois", "Année"};
   
    public GraphVenteParMoisController() throws SQLException {
        this.venteDao = new VenteDao();
        this.clientDao = new ClientDao();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        String[] arrayMois = {"janv", "fev", "mars", "avri", "mai", "jun", "juil", "aout", "sept", "oct", "nov", "dec"};
        observableListeMois.addAll(arrayMois);
        
        categoryAxis.setCategories(observableListeMois);
        Map<Integer, ArrayList> dates = venteDao.ListeQteVenteParMoi();
        
        for( Map.Entry<Integer, ArrayList> dateItem: dates.entrySet()){
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(dateItem.getKey().toString());
            
            for(int i = 0; i < dateItem.getValue().size(); i = i + 2){
                String mois;
                Integer quantite;
                mois = retourneNomMois((int) dateItem.getValue().get(i));
                quantite = (Integer) dateItem.getValue().get(i + 1);
                series.getData().add(new XYChart.Data<>(mois, quantite));
            }
            barChartParMois.getData().add(series);
        }
           
    }    
    
    
    public String retourneNomMois(int mois){
        switch(mois){
            case 1: 
                return "janv";
            case 2: 
                return "fev";
             case 3: 
                return "mars";
            case 4: 
                return "avril";
            case 5: 
                return "mai";
            case 6: 
                return "jun";
             case 7: 
                return "jul";
            case 8: 
                return "aout";
            case 9: 
                return "sept";
            case 10: 
                return "oct";
             case 11: 
                return "nov";
            case 12: 
                return "dec";
        }
        return null;
       
    }

    //le client veut afficher les resultats obtenus
    @FXML
    private void handleButtonVoirListePorduit(ActionEvent event) {
        
        if(comboBoxClient.getSelectionModel().getSelectedItem() != null &&
           datePickerDebut.getValue() != null &&
           datePickerFin.getValue() != null ){
            tableColumnCodeVente.setCellValueFactory(new PropertyValueFactory<>("codeVente"));
            tableColumnCodePro.setCellValueFactory(new PropertyValueFactory<>("codePorduit"));
            tableColumnDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
            tableColumnQteCommande.setCellValueFactory(new PropertyValueFactory<>("qteCommande"));
            tableColumnSousTotale.setCellValueFactory(new PropertyValueFactory<>("sousValeur"));
            tableColumnDateVente.setCellValueFactory(new PropertyValueFactory<>("datevente"));
           
            Client client;
            client = (Client) comboBoxClient.getSelectionModel().getSelectedItem();
            observableListProduit = FXCollections.observableArrayList(venteDao.venteEntre2Date(client, datePickerDebut.getValue(), datePickerFin.getValue()));
            tableViewListeProduit.setItems(observableListProduit);
        }
    }
    
        @FXML
    private void handleVoirListeParCritere(ActionEvent event) {
       if(comboBoxClient2.getSelectionModel().getSelectedItem() != null &&
           comboBoxPar.getValue() != null &&
           datePickerDatePar.getValue() != null ){
            tableColumnCodeVente.setCellValueFactory(new PropertyValueFactory<>("codeVente"));
            tableColumnCodePro.setCellValueFactory(new PropertyValueFactory<>("codePorduit"));
            tableColumnDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
            tableColumnQteCommande.setCellValueFactory(new PropertyValueFactory<>("qteCommande"));
            tableColumnSousTotale.setCellValueFactory(new PropertyValueFactory<>("sousValeur"));
            tableColumnDateVente.setCellValueFactory(new PropertyValueFactory<>("datevente"));
           
            Client client;
            client = (Client) comboBoxClient2.getSelectionModel().getSelectedItem();
            String critere = (String) comboBoxPar.getSelectionModel().getSelectedItem();
            
            if(critere == "Mois")
                observableListProduit = FXCollections.observableArrayList(venteDao.venteParCritere(client, critere, datePickerDatePar.getValue().getMonthValue()));
            else
                 observableListProduit = FXCollections.observableArrayList(venteDao.venteParCritere(client, critere, datePickerDatePar.getValue().getYear()));
                
            tableViewListeProduit.setItems(observableListProduit);
       }
    }
    
      //afficher la liste des clients disponible
    public void afficherComboBoxClient(){
        listeClients= clientDao.listeClient();
        observableListClient = FXCollections.observableArrayList(listeClients);
        comboBoxClient.setItems(observableListClient);
        comboBoxClient2.setItems(observableListClient);
    }
    //onglet rapport selectionner
    @FXML
    private void handleOngletRapport(Event event) {
         afficherComboBoxClient();
         comboBoxPar.setItems(FXCollections.observableArrayList(citereParMoisOuAnnee));
    }


    

    
}
