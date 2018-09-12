/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import gestiondecommande.model.dao.ProduitDao;
import gestiondecommande.model.dao.VenteDao;
import gestiondecommande.model.dao.itemeDeVenteDao;
import gestiondecommande.model.domain.ItemeDeVente;
import gestiondecommande.model.domain.Produit;
import gestiondecommande.model.domain.Vente;
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
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
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
public class VenteController implements Initializable {

     private Stage stage;
     private List<Vente> listeVente;
     private final VenteDao venteDao;
     private ObservableList<Vente>ObservableListVente;
     private final itemeDeVenteDao itemeDeVenteDao = new itemeDeVenteDao();
     
     
    @FXML
    private Pagination pagination;
    @FXML
    private Button buttonAjouter;
    @FXML
    private Button buttonModifier;
    @FXML
    private Button buttonSuprimer;
    @FXML
    private Button buttonProduitCommande;

    @FXML
    private Label labelCodeVente;
    @FXML
    private Label labelValeur;
    @FXML
    private Label labelClient;
    @FXML
    private Label labelPayer;
    @FXML
    private Label labelCategorie;
    @FXML
    private Label labelDateVente;
    @FXML
    private TableView<Vente> tableViewVente;
    @FXML
    private TableColumn<Vente, String> tabelColumnCodeVente;
    @FXML
    private TableColumn<Vente, String> tabelColumnValeur;
    @FXML
    private TableColumn<Vente, String> tabelColumnDateVente;
    @FXML
    private TableColumn<Vente, String> tabelColumnClient;
    

    public VenteController() throws SQLException {
        this.venteDao = new VenteDao();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
     //menu clic droite
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pagination.setPageFactory(this::afficherTableViewVente);
        
        // Listen for selection changes and show the person details when changed.
             tableViewVente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showVenteDetails(newValue));
        //menu clic droite
          tableViewVente.autosize();
        //afficher la pagination
       
    }    
    //fonction pagination
    private Node createPage(int pageIdex){
       
         return tableViewVente;
    }
    //-----------------------action CRUD-----------------------------//
    @FXML
    private void handleButtonAjouter(ActionEvent event) throws SQLException {
          //raliser un nouveau vente
        Vente vente = new Vente();
        List<ItemeDeVente> itemeDeVente = new ArrayList();
        vente.setItemeDeVente(itemeDeVente);
        boolean buttonValiderClicked = showVenteDialog(vente);
        if(buttonValiderClicked){
            venteDao.create(vente);
            for( ItemeDeVente listeItemDeVente:vente.getItemeDeVente()){
                Produit produit = listeItemDeVente.getProduit();
                ProduitDao produitDao = new ProduitDao();
                listeItemDeVente.setVente(venteDao.recupererDerniereVente());
                
                itemeDeVenteDao.create(listeItemDeVente);
                
                produit.setQteEnStk(produit.getQteEnStk() - listeItemDeVente.getQteCommande());
                produit.setCategorie(listeItemDeVente.getProduit().getCategorie());
                produitDao.UpdateQte(produit);
            }
         pagination.setPageFactory(this::afficherTableViewVente);
        }
    }

    @FXML
    private void handleButtonUpdate(ActionEvent event) throws SQLException {
         Vente vente = (Vente) tableViewVente.getSelectionModel().getSelectedItem();
         vente.setTempeItemeDeVente(vente.getItemeDeVente());
        if(vente != null){
//            vente.setItemeDeVente(itemeDeVenteDao.listeProduitDeVente(vente));
            boolean buttonValiderClicked = showVenteDialog(vente);
            if(buttonValiderClicked){

                List<ItemeDeVente> ancienIteme = itemeDeVenteDao.listeProduitDeVente(vente);
                redonnerQteProduit(ancienIteme);
                //enregistrer la vente tout d'abord
                venteDao.update(vente);
                 //effacer les itemes de vente 
                itemeDeVenteDao.deleteAllItemeDeVente(vente);
               
                //renregistrer les itemes de vente
                for( ItemeDeVente listeItemDeVente:vente.getItemeDeVente()){
                    ProduitDao produitDao = new ProduitDao();
                    Produit p1 = listeItemDeVente.getProduit();
                    Produit produit = produitDao.showProduit(p1);
                    
                    System.out.println("quantite de produit: "+ produit.getQteEnStk());
                   
//                    
                    listeItemDeVente.setVente(vente);
                   
                    itemeDeVenteDao.create(listeItemDeVente);
                    System.out.println("final quantite commande: "+ listeItemDeVente.getQteCommande());
                     System.out.println("quantite de produit: "+ produit.getQteEnStk());
                    produit.setQteEnStk(produit.getQteEnStk() - listeItemDeVente.getQteCommande());
                    produit.setCategorie(listeItemDeVente.getProduit().getCategorie());
                    produitDao.UpdateQte(produit);
                }
               
              pagination.setPageFactory(this::afficherTableViewVente);
                
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun Produit selectionne!!");
            alert.show();
        }
    }
    /*fonction utiliser par update
    * Qui permet de redonner la quantite initiale de chaque produit concernet par la vente
    */
    public void redonnerQteProduit(List<ItemeDeVente> listeItemeDeVente) throws SQLException{
        ProduitDao produitDao = new ProduitDao();
        for(ItemeDeVente itemeDeVente:listeItemeDeVente){
            Produit produit = itemeDeVente.getProduit();
            produit.setQteEnStk(itemeDeVente.getQteCommande() + produit.getQteEnStk());
            produitDao.UpdateQte(produit);
            System.out.println("Qte produit redonnee : "+   produit.getQteEnStk());
        }
    }

    @FXML
    private void handleButtonDelete(ActionEvent event) throws SQLException {
        Vente vente = tableViewVente.getSelectionModel().getSelectedItem();
        
        if(vente != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suprimer une commande");
            alert.setContentText("Voulez vous vraiment Suprimer la commande numero: '"+ vente.getCodeVente()+ "'");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                //resinitialiser la quantite
                  for( ItemeDeVente listeItemDeVente:vente.getItemeDeVente()){
                      Produit produit = listeItemDeVente.getProduit();
                      ProduitDao produitDao = new ProduitDao();
                      
                      produit.setQteEnStk(produit.getQteEnStk() + listeItemDeVente.getQteCommande());
                       produit.setCategorie(listeItemDeVente.getProduit().getCategorie());
                      produitDao.Update(produit);
                      
                     itemeDeVenteDao.deleteOneItem(listeItemDeVente);
                  }
                venteDao.delete(vente);
                pagination.setPageFactory(this::afficherTableViewVente);
            }
              
                 
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun ligne de vente selectionné!!");
            alert.show();
        }
    }
    
    //afficher le boite de dialogue
    public boolean showVenteDialog(Vente vente) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(VenteDialogController.class.getResource("/gestiondecommande/view/venteDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
//        page.set
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Formulaire vente");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL.WINDOW_MODAL);
        dialogStage.initOwner(this.stage);
        dialogStage.setResizable(false);
        
        // Set the user into the controller.
        VenteDialogController controller = loader.getController();
        controller.setStage(dialogStage);
        controller.setVente(vente);
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isButtonValiderClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    //---------------------fin action crud -------------------------//
    
    //afficher les les contenues de vente sur la table vente
     public Node afficherTableViewVente(int pageIndex){
        tabelColumnCodeVente.setCellValueFactory(new PropertyValueFactory<>("codeVente"));
        tabelColumnDateVente.setCellValueFactory(new PropertyValueFactory<>("dateVente"));
        tabelColumnValeur.setCellValueFactory(new PropertyValueFactory<>("TValeur"));
        tabelColumnClient.setCellValueFactory(new PropertyValueFactory<>("client"));
      
        listeVente = venteDao.listeVente();
       
         int rowParPage = 4;
         int addPagSuplem;
         //verifier si le nombre de ligne dans la table est impaire 
         if(listeVente.size() % 4 == 0 ) addPagSuplem = 0;
         else addPagSuplem = 1;
         
         pagination.setPageCount((listeVente.size() / rowParPage) + addPagSuplem);
         int fromIndex = pageIndex * rowParPage;
         int toIndex = Math.min(fromIndex + rowParPage, listeVente.size());
         
        ObservableListVente = FXCollections.observableArrayList(listeVente.subList(fromIndex, toIndex));
        tableViewVente.setItems(ObservableListVente);
        
        return tableViewVente;
    }
     
     //afficher les information en detaille d une ligne de vente selectionnEe
      private void showVenteDetails(Vente vente) {
        if (vente != null) {
            // Fill the labels with info from the person object.
            labelCodeVente.setText(Integer.toString(vente.getCodeVente()));
            labelValeur.setText(Double.toString(vente.getTValeur()));
            labelPayer.setText(String.valueOf(vente.isPayer()));
            labelDateVente.setText(String.valueOf(vente.getDateVente().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            labelClient.setText(vente.getClient().toString());

        } else {
            labelCodeVente.setText("");
            labelValeur.setText("");
            labelPayer.setText("");
            labelDateVente.setText("");
            labelClient.setText("");
        }
    }
      
      
    //fomction qui permet d afficher la liste des produits commandes par un client
    @FXML
    private void handleButtonListeProduit(ActionEvent event) throws IOException{
         // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        
        
         Vente vente = tableViewVente.getSelectionModel().getSelectedItem();
        
        if(vente != null){
            loader.setLocation(ListeProduitDialogController.class.getResource("/gestiondecommande/view/listeProduitDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Liste des produits");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL.WINDOW_MODAL);
            dialogStage.initOwner(this.stage);
            
             // Set the user into the controller.
            ListeProduitDialogController controller = loader.getController();
            controller.setVente(vente);
            controller.setListeProduit(venteDao.listeProduit(vente));
            
            dialogStage.showAndWait();
                 
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun ligne de vente selectionné!!");
            alert.show();
        }

    }
        
}


    