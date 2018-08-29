
package gestiondecommande.controller;

import gestiondecommande.model.dao.ProduitDao;
import gestiondecommande.model.domain.Produit;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class ProduitController implements Initializable {
    
    //les bouton crud
    @FXML
    private Button buttonAjouter;
    @FXML
    private Button buttonModifier;
    @FXML
    private Button buttonSuprimer;
    
    //label detaille inforamtion
    
    @FXML
    private TextField textFieldSearch;
    @FXML
    private Label labelCodeProduit;
    @FXML
    private Label labelDesignation;
    @FXML
    private Label labelPU;
    @FXML
    private Label labelQteEnStk;
    @FXML
    private Label labelDateStk;
    @FXML
    private Label labelCommenataire;
     @FXML
    private Label labelCategorie;
    
    //tableau d' affichage du produit
    @FXML
    private TableView tableViewProduit;
    @FXML
    private TableColumn<Produit, String> tabelColumnCodePro;
    @FXML
    private TableColumn<Produit, String> tabelColumnDesignaion;
    @FXML
    private TableColumn<Produit, Integer> tabelColumnCodePU;
    @FXML
    private TableColumn<Produit, Integer> tabelColumnQteEnStk;
    @FXML
    private TableColumn<Produit, String> tabelColumnCategorie;
    
      //les class utiles
    private List<Produit> listeProduit;
    private ObservableList<Produit> observableListProduit;
    private ProduitDao produitDao;
    
    private Stage stage;

    public ProduitController() throws SQLException {
        this.produitDao = new ProduitDao();
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
    
        afficherTableViewProduit();
        
          tableViewProduit.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProduitDetails((Produit) newValue));
    }    
    
     public void afficherTableViewProduit(){
        tabelColumnCodePro.setCellValueFactory(new PropertyValueFactory<>("codePro"));
        tabelColumnDesignaion.setCellValueFactory(new PropertyValueFactory<>("designation"));
        tabelColumnCodePU.setCellValueFactory(new PropertyValueFactory<>("prixU"));
        tabelColumnQteEnStk.setCellValueFactory(new PropertyValueFactory<>("qteEnStk"));
        tabelColumnCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        
        listeProduit = produitDao.listeProduits();
        observableListProduit = FXCollections.observableArrayList(listeProduit);
        tableViewProduit.setItems(observableListProduit);
        
    }
     
      private void showProduitDetails(Produit produit) {
        if (produit != null) {
            // Fill the labels with info from the person object.
            labelCodeProduit.setText(Integer.toString(produit.getCodePro()));
            labelDesignation.setText(produit.getDesignation());
            labelPU.setText(Double.toString(produit.getPrixU()));
            labelQteEnStk.setText(Double.toString(produit.getQteEnStk()));
            labelCommenataire.setText(produit.getCommentaire());
            labelDateStk.setText(String.valueOf(produit.getDateEnStk().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            labelCategorie.setText(produit.getCategorie().getDesignation());
     
        } else {
            labelCodeProduit.setText("");
            labelDesignation.setText("");
            labelPU.setText("");
            labelQteEnStk.setText("");
            labelCommenataire.setText("");
            labelDateStk.setText("");
            labelCategorie.setText("");
        }
   }
      //------------------- les CRUD ----------------------------//
      // suprimer un Produit
    @FXML
    public void handleButtonDelete() throws IOException{
        Produit produit = (Produit) tableViewProduit.getSelectionModel().getSelectedItem();
        if(produit != null){
                produitDao.delete(produit);
                afficherTableViewProduit();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun Produit selectionné!!");
            alert.show();
        }

    }
     //ajouter un nouveau Produit
    @FXML
    public void handleButtonAjouter() throws IOException{
        Produit produit = new Produit();
        boolean buttonValiderClicked = showProduitDialog(produit);
        if(buttonValiderClicked){
            produitDao.create(produit);
            afficherTableViewProduit();
        }
    }
    
     //Modifier un produit
    @FXML
    public void handleButtonUpdate() throws IOException{
        Produit produit = (Produit) tableViewProduit.getSelectionModel().getSelectedItem();
        if(produit != null){
            boolean buttonValiderClicked = showProduitDialog(produit);
            if(buttonValiderClicked){
                produitDao.Update(produit);
                afficherTableViewProduit();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun Produit selectionne!!");
            alert.show();
        }

    }
    
     // afficher le boite de dialogue
    public boolean showProduitDialog(Produit produit) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ProduitDialogController.class.getResource("/gestiondecommande/view/produitDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Formulaire Produit");
   
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL.WINDOW_MODAL);
        dialogStage.initOwner(this.stage);
        dialogStage.setResizable(false);
        
        //set the product into the controller
        ProduitDialogController controller= loader.getController();
        controller.setStage(dialogStage);
        controller.setProduit(produit);
        
        dialogStage.showAndWait();
        
        return controller.isButtonValiderClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
}
