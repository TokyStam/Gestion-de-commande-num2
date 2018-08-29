/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import gestiondecommande.model.dao.ClientDao;
import gestiondecommande.model.domain.Client;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class ClientDialogController implements Initializable {

    private Stage stage;
    private Client client;
    private boolean buttonValiderClicked = false;   
    @FXML
    private TextField textFieldCodeClient;
    @FXML
    private TextField textFieldNom;
    @FXML
    private TextField textFieldNumTel;
    @FXML
    private DatePicker textFieldNaissance;
    @FXML
    private Button buttonValider;
    @FXML
    private Button buttonAnnuler;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
         if(this.client != null ||  client.getCodeClient() == 0){
            this.textFieldCodeClient.setText(Integer.toString(client.getCodeClient()));
            this.textFieldNom.setText(client.getNom());
           this.textFieldNumTel.setText(client.getNumTel());
            this.textFieldNaissance.setValue(client.getDateNais());
        }
    }

    public boolean isButtonValiderClicked() {
        return buttonValiderClicked;
    }

    public void setButtonValiderClicked(boolean buttonValiderClicked) {
        this.buttonValiderClicked = buttonValiderClicked;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonValider(ActionEvent event) throws SQLException {
        if(isInputValid()){
            client.setNom(textFieldNom.getText());
            client.setNumTel(textFieldNumTel.getText());
            client.setDateNais(textFieldNaissance.getValue());
            
            ClientDao clientDao = new ClientDao();
            client.setChiffreDaffaire(clientDao.chiffreDaffaire(client));

            buttonValiderClicked = true;
            stage.close();
       }
        
    }

    @FXML
    private void handleButtonFermer(ActionEvent event) {
        stage.close();
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        if (textFieldNom.getText() == null || textFieldNom.getText().length() == 0) {
            errorMessage += "Le nom saisi est invalide!\n";
        }
        if (textFieldNumTel.getText() == null || textFieldNumTel.getText().length() < 10 ) {
            errorMessage += "Contacte invalide!\n";
        }
         if (textFieldNaissance.getValue() == null) {
            errorMessage += "Date de naissance invalide!\n";
        } 
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Informations incorrectes");
            alert.setHeaderText("SVP, corriger les champs invalides");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
