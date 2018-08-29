/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import gestiondecommande.model.domain.Categorie;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class CategorieDialogController implements Initializable {
    
    
    //les champs de saisi
    @FXML
    private TextField textFieldCodeCateg;
    @FXML
    private TextField textFieldDesignation;
    @FXML
    private TextArea textAreaCommentaire;
    
    private Stage stage;
    private Categorie categorie;
    private boolean buttonValiderClicked = false;
    
    //les bouton de decision
    @FXML
    private Button buttonValider;
    @FXML
    private Button buttonFermer;

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
        textFieldCodeCateg.setEditable(false);
        if(this.categorie != null ||  categorie.getDesignation()!= null){
            this.textFieldDesignation.setText(categorie.getDesignation());
            this.textAreaCommentaire.setText(categorie.getCommenataire());
            this.textFieldCodeCateg.setText(Integer.toString(categorie.getCodeCateg()));
        }else{
            this.textFieldCodeCateg.setText("Code automatique");
        }
        
    }
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
    
     //bouton confirmer est cliquE
    @FXML
    public void handleButtonValider(){
       if(isInputValid()){
            this.categorie.setDesignation(textFieldDesignation.getText());
            this.categorie.setCommenataire(textAreaCommentaire.getText());
            System.out.println(textAreaCommentaire.getText());
            buttonValiderClicked = true;
            stage.close();
       }    
    }
    @FXML
    public void handleButtonFermer(){
         stage.close();
    }
    
     private boolean isInputValid() {
        String errorMessage = "";
        if (textFieldDesignation.getText() == null || textFieldDesignation.getText().length() == 0) {
            errorMessage += "Vous devez remplir le champs \'Designation\'!\n";
        }
        if (textAreaCommentaire.getText() == null || textAreaCommentaire.getText().length() == 0) {
            errorMessage += "Donner un peu de description de ce categorie '" +  textFieldDesignation.getText() + "' \n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Champs invalide");
            alert.setHeaderText("Svp, veuillez corriger les erreus suivantes!!!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
