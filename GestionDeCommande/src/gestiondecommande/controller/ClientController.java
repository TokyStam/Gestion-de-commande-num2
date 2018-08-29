/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import gestiondecommande.model.dao.ClientDao;
import gestiondecommande.model.domain.Client;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        afficherTableViewClient();
         // Listen for selection changes and show the person details when changed.
             tabelViewClient.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }    
    
      public void afficherTableViewClient(){
        tableColumnCA.setCellValueFactory(new PropertyValueFactory<>("ChiffreDaffaire"));
        tableColumnCodeClient.setCellValueFactory(new PropertyValueFactory<>("codeClient"));
        tableColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tableColumnNumTel.setCellValueFactory(new PropertyValueFactory<>("numTel"));
        
        Listclient = clientDao.listeClient();
        observableListClient = FXCollections.observableArrayList(Listclient);
        tabelViewClient.setItems(observableListClient);
        
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
            afficherTableViewClient();
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
                afficherTableViewClient();
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
                clientDao.delete(client);
                afficherTableViewClient();
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
    
}
