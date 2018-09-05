/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import gestiondecommande.model.dao.CategorieDao;
import gestiondecommande.model.domain.Categorie;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class CategorieController implements Initializable {

    //le conteneur
    @FXML
    private AnchorPane anchorPaneCategorie;
    @FXML
    private TextField textFieldSearch;
    
    //les bouton crud
    @FXML
    private Button buttonAjouter;
    @FXML
    private Button buttonModifier;
    @FXML
    private Button buttonSuprimer;
    
    //label detaille inforamtion
    @FXML
    private Label labelCodeCategorie;
    @FXML
    private Label labelDesignation;
    @FXML
    private Label labelCommentaire;
    
    //tableau d' affichage du categorie
    @FXML
    private TableView tabelViewCategorie;
    @FXML
    private TableColumn tableColumnCodeCategorie;
    @FXML
    private TableColumn tableColumnDesignation;
    
    //les class utiles
    private List<Categorie> listeCategorie;
    private ObservableList<Categorie> observableListCategorie;
    private CategorieDao categorieDao;
    
    private Stage stage;
    @FXML
    private Pagination pagination;

    public CategorieController() throws SQLException {
        this.categorieDao = new CategorieDao();
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
           //afficher le tableau
            pagination.setPageFactory(this::afficherTableViewCategorie);
            
            //afficher la categorie en detaille
             tabelViewCategorie.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCategorieDetails((Categorie) newValue));
       
    }    
   
    //afficher dans table view les categories
     public Node afficherTableViewCategorie(int pageIndex){
        tableColumnCodeCategorie.setCellValueFactory(new PropertyValueFactory<>("codeCateg"));
        tableColumnDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
       
        listeCategorie = categorieDao.listeCategorie();
        
         int rowParPage = 2;
         int addPagSuplem;
         //verifier si le nombre de ligne dans la table est impaire 
         if(listeCategorie.size() % 2 == 0 ) addPagSuplem = 0;
         else addPagSuplem = 1;
         
         pagination.setPageCount((listeCategorie.size() / rowParPage) +addPagSuplem);
         int fromIndex = pageIndex * rowParPage;
         int toIndex = Math.min(fromIndex + rowParPage, listeCategorie.size());
         
        observableListCategorie = FXCollections.observableArrayList(listeCategorie.subList(fromIndex, toIndex));
        tabelViewCategorie.setItems(observableListCategorie); 
         return tabelViewCategorie;
    }
    
     //fonction qui permet d'afficher en detaille les categories
     private void showCategorieDetails(Categorie categorie) {
        if (categorie != null) {
            // Fill the labels with info from the person object.
            labelCodeCategorie.setText(Integer.toString(categorie.getCodeCateg()));
            labelDesignation.setText(categorie.getDesignation());
            labelCommentaire.setText(categorie.getCommenataire());
        
        } else {
            // Person is null, remove all the text.
            labelCodeCategorie.setText("");
            labelDesignation.setText("");
            labelCommentaire.setText("");
        }
    }
     //-----------------------------------debut crud --------------------------------------//
     // suprimer un Produit
    @FXML
    public void handleButtonDelete() throws IOException{
        Categorie categorie = (Categorie) tabelViewCategorie.getSelectionModel().getSelectedItem();
        if(categorie != null){
                categorieDao.delete(categorie);
                pagination.setPageFactory(this::afficherTableViewCategorie);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun categorie selectionne!!");
            alert.show();
        }

    }
    
     //ajouter un nouveau Produit
    @FXML
    public void handleButtonAjouter() throws IOException{
        Categorie categorie = new Categorie();
        boolean buttonValiderClicked = showCategorieDialog(categorie);
        if(buttonValiderClicked){
            categorieDao.create(categorie);
            pagination.setPageFactory(this::afficherTableViewCategorie);
        }
    }
    
    //Modifier une categorie
    @FXML
    public void handleButtonUpdate() throws IOException{
        Categorie categorie = (Categorie) tabelViewCategorie.getSelectionModel().getSelectedItem();
        if(categorie != null){
            boolean buttonValiderClicked = showCategorieDialog(categorie);
            if(buttonValiderClicked){
                categorieDao.Update(categorie);
                 pagination.setPageFactory(this::afficherTableViewCategorie);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun categorie selectionné!!");
            alert.show();
        }

    }
    
     //fonction recherche
    @FXML
    public void handleSearch() throws IOException{
         
           if(textFieldSearch.getText() != null ){
                tableColumnCodeCategorie.setCellValueFactory(new PropertyValueFactory<>("codeCateg"));
                tableColumnDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));

                listeCategorie = null;
                listeCategorie = categorieDao.rechercheCategorie(textFieldSearch.getText());
                observableListCategorie = FXCollections.observableArrayList(listeCategorie);
                tabelViewCategorie.setItems(observableListCategorie); 
                
           }else {
              pagination.setPageFactory(this::afficherTableViewCategorie);
           }
    }
    
   // afficher le boite de dialogue
    public boolean showCategorieDialog(Categorie categorie) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CategorieDialogController.class.getResource("/gestiondecommande/view/categorieDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Formulaire categorie");
   
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        
        dialogStage.initModality(Modality.APPLICATION_MODAL.WINDOW_MODAL);
        dialogStage.initOwner(this.stage);
        
        //set the product into the controller
        CategorieDialogController controller= loader.getController();
        controller.setStage(dialogStage);
        controller.setCategorie(categorie);
        
        dialogStage.showAndWait();
        
        return controller.isButtonValiderClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    
}
