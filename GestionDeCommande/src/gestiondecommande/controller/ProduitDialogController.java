
package gestiondecommande.controller;

import gestiondecommande.model.dao.CategorieDao;
import gestiondecommande.model.dao.ProduitDao;
import gestiondecommande.model.domain.Categorie;
import gestiondecommande.model.domain.Produit;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class ProduitDialogController implements Initializable {
    
    @FXML
    private TextField textFieldCodeProduit;
    @FXML
    private TextField textFielDesigantion;
    @FXML
    private TextField textFielPU;
    @FXML
    private TextField textFielQteEnStk;
    @FXML
    private TextArea textAreaCommentaire;
    @FXML
    private DatePicker datePickerDebeutStk;
    @FXML
    private ComboBox<Categorie> comboBoxCategorie;
    //bouton decision
    @FXML
    private Button buttonValider;
    @FXML
    private Button buttonFermer;
    
    private List<Categorie> listeCategorie;
    private ObservableList<Categorie> observableListCategorie; 
    private final CategorieDao categorieDao;
    
    private Stage stage;
    private Produit produit;
    private boolean buttonValiderClicked = false;   

    public ProduitDialogController() throws SQLException {
        this.categorieDao = new CategorieDao();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
         this.produit = produit;
        if(produit != null && produit.getPrixU()!= null && produit.getDesignation()!= null){
            this.textFieldCodeProduit.setText(Integer.toString(produit.getCodePro()));
            this.textFielDesigantion.setText(produit.getDesignation());
            this.textFielQteEnStk.setText(Double.toString(produit.getQteEnStk()));
            this.textFielPU.setText(Double.toString(produit.getPrixU()));
            this.datePickerDebeutStk.setValue(produit.getDateEnStk());
            this.textAreaCommentaire.setText(produit.getCommentaire());
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
        afficherComboBoxCategogrie();
    }    
    
    //bouton confirmer est clique
    public void handleButtonValider(){
      if(isInputValid()){
            produit.setDesignation(textFielDesigantion.getText());
            produit.setCommentaire(textAreaCommentaire.getText());
            produit.setPrixU(Double.parseDouble(textFielPU.getText()));
            produit.setQteEnStk(Double.parseDouble(textFielQteEnStk.getText()));
            produit.setDateEnStk(datePickerDebeutStk.getValue());
            produit.setCategorie((Categorie) comboBoxCategorie.getSelectionModel().getSelectedItem());

            buttonValiderClicked = true;
            stage.close();
      }
    }
    //boutton fermer cliquer
    public void handleButtonFermer(){
         stage.close();
    }
    
          //afficher la liste des produit disponible
    public void afficherComboBoxCategogrie(){
        listeCategorie = categorieDao.listeCategorie();    
        observableListCategorie = FXCollections.observableArrayList(listeCategorie);
        comboBoxCategorie.setItems(observableListCategorie);
    }
    
     /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if (comboBoxCategorie.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Choisir la categorie correspondente!\n";
        }
        if (textFielDesigantion.getText() == null || textFielDesigantion.getText().length() == 0) {
            errorMessage += "Veuillez remplir le champ designation!\n";
        }
        
        if (datePickerDebeutStk.getValue()== null) {
            errorMessage += "Veuillez remplir le champ debut du stoque!\n";
        }
            
        if (textFielPU.getText() == null || textFielPU.getText().length() == 0) {
            errorMessage += "Veuillerz mentioner le prix du produit!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Double.parseDouble(textFielPU.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Prix invalide !\n";
            }
        }
        
         if (textFielQteEnStk.getText() == null || textFielQteEnStk.getText().length() == 0) {
            errorMessage += "Veuillerz mentioner la quantitE du produit stokE!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Double.parseDouble(textFielQteEnStk.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Quantite invalide !\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
         }
    }
}
