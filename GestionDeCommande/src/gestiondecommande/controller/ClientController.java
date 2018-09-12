/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import gestiondecommande.model.dao.ClientDao;
import gestiondecommande.model.domain.CAparAnnee;
import gestiondecommande.model.domain.Client;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class ClientController implements Initializable {

    private Stage stage;
    private List<Client> Listclient;
    private ObservableList<Client> observableListClient;
    private final ClientDao clientDao;
    
    @FXML
    private AnchorPane anchorPaneCategorie;
    @FXML
    private Button buttonAjouter;
    @FXML
    private Button buttonModifier;
    @FXML
    private Button buttonSuprimer;
    @FXML
    private TableView<Client> tabelViewClient;
    @FXML
    private TableColumn<Client, String> tableColumnCodeClient;
    @FXML
    private TableColumn<Client, String> tableColumnNom;
    @FXML
    private TableColumn<Client, String> tableColumnNumTel;
    @FXML
    private TableColumn<Client, Double> tableColumnCA;
    @FXML
    private Label labelCodeClient;
    @FXML
    private Label labelNom;
    @FXML
    private Label labelNumTel;
    @FXML
    private Label labelCA;
    @FXML
    private Label labelNaissance;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private ComboBox<String> comboBoxSearch;
    String  lesChamps[] = {"nom", "numTel", "dateNais"};
    @FXML
    private Pagination pagination;
    
    @FXML
    private TableView<CAparAnnee> tableViewCA;
    @FXML
    private TableColumn tableColumnAnnee;
    @FXML
    private TableColumn tableColumnValeurCA;
   

    public ClientController() throws SQLException {
        this.clientDao = new ClientDao();
    }
    

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pagination.setPageFactory(this::afficherTableViewClient);
        afficherListeSearch();
         // Listen for selection changes and show the person details when changed.
             tabelViewClient.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
             
             tabelViewClient.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> afficherTableViewCA(newValue));
    }    
      public void afficherListeSearch(){
        comboBoxSearch.setItems(FXCollections.observableArrayList(lesChamps));
      }
    
      public Node afficherTableViewClient(int pageIndex){
        tableColumnCA.setCellValueFactory(new PropertyValueFactory<>("ChiffreDaffaire"));
        tableColumnCodeClient.setCellValueFactory(new PropertyValueFactory<>("codeClient"));
        tableColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tableColumnNumTel.setCellValueFactory(new PropertyValueFactory<>("numTel"));
        
        Listclient = clientDao.listeClient();
         int rowParPage = 4;
         int addPagSuplem;
         //verifier si le nombre de ligne dans la table est impaire 
         if(Listclient.size() % 4 == 0 ) addPagSuplem = 0;
         else addPagSuplem = 1;
         
         pagination.setPageCount((Listclient.size() / rowParPage + addPagSuplem));
         int fromIndex = pageIndex * rowParPage;
         int toIndex = Math.min(fromIndex + rowParPage, Listclient.size());

        observableListClient = FXCollections.observableArrayList(Listclient.subList(fromIndex, toIndex));
        tabelViewClient.setItems(observableListClient);
        
        return tabelViewClient;
    }
       public Node afficherTableViewCA(Client client){
        tableColumnAnnee.setCellValueFactory(new PropertyValueFactory<>("annee"));
        tableColumnValeurCA.setCellValueFactory(new PropertyValueFactory<>("CA"));
       
        tableViewCA.setItems(FXCollections.observableArrayList(client.getCaParAnnee()));
        
        return tabelViewClient;
    }
       /**
     * afficher les listes de personnes.
     * si la personne est vide toutes la valeur du label sera vide
     *
     * @param person the person or null
     */
        private void showPersonDetails(Client client) {
        if (client != null) {
            // Fill the labels with info from the person object.
            labelCodeClient.setText(Integer.toString(client.getCodeClient()));
            labelNom.setText(client.getNom());
            labelCA.setText(Double.toString(client.getChiffreDaffaire()));
            labelNumTel.setText(client.getNumTel());
            labelNaissance.setText(String.valueOf(client.getDateNais().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
     
        } else {
            // Person is null, remove all the text.
            labelCodeClient.setText("");
            labelNom.setText("");
            labelCA.setText("");
            labelNumTel.setText("");
            labelNaissance.setText("");
        }
    }

    //boutton ajouter client
    @FXML
    private void handleButtonAjouter(ActionEvent event) {
         Client client = new Client(); 
        boolean buttonValiderClicked = showClientDialog(client);
        if(buttonValiderClicked){
            clientDao.create(client);
           pagination.setPageFactory(this::afficherTableViewClient);
        }
    }
    //boutton modifier client
    @FXML
    private void handleButtonUpdate(ActionEvent event) {
         Client client = tabelViewClient.getSelectionModel().getSelectedItem();
        if(client != null){
            boolean buttonValiderClicked = showClientDialog(client);
            if(buttonValiderClicked){
                clientDao.Update(client);
                 pagination.setPageFactory(this::afficherTableViewClient);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun client selectionne!!");
            alert.show();
        }
    }
    //boutton suprimer client
    @FXML
    private void handleButtonDelete(ActionEvent event) {
         Client client = tabelViewClient.getSelectionModel().getSelectedItem();
        if(client != null){
             // Show the error message.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suprimer un client");
            alert.setContentText("Voulez vous vraiment Suprimer '"+ client.getNom()+ "'");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                 clientDao.delete(client);
                 pagination.setPageFactory(this::afficherTableViewClient);
            }
               
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun client selectionne!!");
            alert.show();
        }
    }
    
    
     
    //afficher le boite de dialogue
    public boolean showClientDialog(Client client) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientDialogController.class.getResource("/gestiondecommande/view/clientDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Formulaire user");
            dialogStage.initModality(Modality.APPLICATION_MODAL.WINDOW_MODAL);
            dialogStage.initOwner(this.stage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the user into the controller.
            ClientDialogController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setClient(client);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isButtonValiderClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //fonction recherche
    @FXML
    public void handleSearch() throws IOException{
         
           if(textFieldSearch.getText() != null && comboBoxSearch.getSelectionModel().getSelectedItem() != null){
                tableColumnCA.setCellValueFactory(new PropertyValueFactory<>("ChiffreDaffaire"));
                tableColumnCodeClient.setCellValueFactory(new PropertyValueFactory<>("codeClient"));
                tableColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                tableColumnNumTel.setCellValueFactory(new PropertyValueFactory<>("numTel"));

                Listclient = clientDao.searchClient(comboBoxSearch.getSelectionModel().getSelectedItem(), textFieldSearch.getText());
                observableListClient = FXCollections.observableArrayList(Listclient);
                tabelViewClient.setItems(observableListClient);
                
           }else {
               pagination.setPageFactory(this::afficherTableViewClient);
           }
          
    }
    
}
