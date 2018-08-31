/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class ConteneurController implements Initializable {

    @FXML
    private Button buttonCategories;
    @FXML
    private Button buttonPrdouits;
    @FXML
    private Button buttonClients;
    @FXML
    private Button buttonVentes;
    @FXML
    private Button buttonDiagrammes;
    @FXML
    private AnchorPane anchorPaneHover;
  
    
    //l anchorpan conteneur
    @FXML
    private AnchorPane anchorPaneConeteneur;
     @FXML
    private AnchorPane anchorPaneMenuBG;
   
    private Stage stage;

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
           
        try {
            FXMLLoader l = new FXMLLoader(getClass().getResource("/gestiondecommande/view/pagedaccueil.fxml"));
            AnchorPane b = (AnchorPane) l.load();
            anchorPaneConeteneur.getChildren().setAll(b); 
        } catch (IOException ex) {
            Logger.getLogger(ConteneurController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }  
    
    @FXML
    public void handleCategories() throws IOException{
       
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestiondecommande/view/categorie.fxml"));;
        AnchorPane a = (AnchorPane) loader.load();
        CategorieController categorieController = loader.getController();
        categorieController.setStage(stage);
        anchorPaneConeteneur.getChildren().setAll(a); 
        
        anchorPaneHover.setTranslateY(0);
        anchorPaneHover.setTranslateY(45);
        
        anchorPaneMenuBG.setTranslateY(0);
        anchorPaneMenuBG.setTranslateY(45);
    }
    
    @FXML
    public void handleProduits() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestiondecommande/view/produit.fxml"));;
        AnchorPane a = (AnchorPane) loader.load();
        ProduitController produitController = loader.getController();
        produitController.setStage(stage);
        anchorPaneConeteneur.getChildren().setAll(a); 
        
        anchorPaneHover.setTranslateY(0);
        anchorPaneHover.setTranslateY(110);
        
        anchorPaneMenuBG.setTranslateY(0);
        anchorPaneMenuBG.setTranslateY(110);
    }
    
    @FXML
    public void handleClients() throws IOException{
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestiondecommande/view/client.fxml"));;
        AnchorPane a = (AnchorPane) loader.load();
        ClientController clientController = loader.getController();
        clientController.setStage(stage);
        anchorPaneConeteneur.getChildren().setAll(a);
        
        anchorPaneHover.setTranslateY(0);
        anchorPaneHover.setTranslateY(175);
         
        anchorPaneMenuBG.setTranslateY(0);
        anchorPaneMenuBG.setTranslateY(175);
    }
    
    @FXML
    public void handleVentes() throws IOException{
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestiondecommande/view/vente.fxml"));;
        AnchorPane a = (AnchorPane) loader.load();
        VenteController VenteController = loader.getController();
        VenteController.setStage(stage);
        anchorPaneConeteneur.getChildren().setAll(a); 
        anchorPaneHover.setTranslateY(0);
        anchorPaneHover.setTranslateY(240);
        
        anchorPaneMenuBG.setTranslateY(0);
        anchorPaneMenuBG.setTranslateY(240);
    }
    
    @FXML
    public void handleDiagramme() throws IOException{
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestiondecommande/view/graphVenteParMois.fxml"));;
        AnchorPane a = (AnchorPane) loader.load();
        anchorPaneConeteneur.getChildren().setAll(a);
        
        anchorPaneHover.setTranslateY(0);
        anchorPaneHover.setTranslateY(310);
        
        anchorPaneMenuBG.setTranslateY(0);
        anchorPaneMenuBG.setTranslateY(310);
    }
    
}
