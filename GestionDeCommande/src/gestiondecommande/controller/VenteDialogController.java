/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import gestiondecommande.model.dao.ClientDao;
import gestiondecommande.model.dao.ProduitDao;
import gestiondecommande.model.domain.Client;
import gestiondecommande.model.domain.ItemeDeVente;
import gestiondecommande.model.domain.Produit;
import gestiondecommande.model.domain.Vente;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class VenteDialogController implements Initializable {

    private Stage stage;
    private Vente vente;
    private boolean buttonValiderClicked = false;  
    
    private ObservableList<Client> observableListClient;
    private ObservableList<Produit> observableListProduit;
    private ObservableList<ItemeDeVente> observableListItemeDeVentes;
    
    private List<Client> listeClients;
    private List<Produit> listeProduits;
    private final ClientDao clientDao;
    private final ProduitDao produitDao;
    
    @FXML
    private ComboBox<Client> comboBoxClient;
    @FXML
    private DatePicker datePickerDateVente;
    @FXML
    private CheckBox chexkBoxPayer;
    @FXML
    private TableView<ItemeDeVente> tableViewItemeVente;
    @FXML
    private TableColumn<Vente, String> columnNomProduit;
    @FXML
    private TableColumn<Vente, Double>  columnQteProduit;
    @FXML
    private TableColumn<Vente, Double>  columnValeurProduit;
    @FXML
    private TextField textFieldValeurVente;
    @FXML
    private ComboBox<Produit> comboBoxProduit;
    @FXML
    private TextField textFieldQteProduit;
    @FXML
    private Button buttonAddProduitIteme;
    @FXML
    private Button buttonConfir;
    @FXML
    private Button buttonEdit;
    @FXML
    private FontAwesomeIconView buttonDelete;

    @FXML
    private Button buttonAnnulerVente;

    public VenteDialogController() throws SQLException {
        this.produitDao = new ProduitDao();
        this.clientDao = new ClientDao();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
        
        if(vente.getClient()!= null && vente.getItemeDeVente() != null){
          comboBoxClient.setValue(vente.getClient());
          datePickerDateVente.setValue(vente.getDateVente());
          textFieldValeurVente.setText(String.format("%.2f", vente.getTValeur()));
          
         observableListItemeDeVentes= FXCollections.observableArrayList(vente.getItemeDeVente());
         tableViewItemeVente.setItems(observableListItemeDeVentes);
        
//          for(ItemeDeVente listeItemeDeVente:vente.getItemeDeVente()){
//               System.out.println("categorie 2: " + listeItemeDeVente.getProduit().getCategorie().getDesignation());
//           }
          
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
        afficherComboBoxClient();
        afficherComboBoxProduit();
        
        
        
        //Afficher les produit ajoutEs
        columnNomProduit.setCellValueFactory(new PropertyValueFactory<>("produit"));
        columnQteProduit.setCellValueFactory(new PropertyValueFactory<>("QteCommande"));
        columnValeurProduit.setCellValueFactory(new PropertyValueFactory<>("SValeur"));
    }    

    @FXML
    private void handleButtonAddProduit(ActionEvent event) {
        Produit produit;
        ItemeDeVente itemeDeVente = new ItemeDeVente();
        
        if(comboBoxProduit.getSelectionModel().getSelectedItem() != null &&
                (textFieldQteProduit.getText() != null && Double.parseDouble(textFieldQteProduit.getText()) != 0)){
            produit = (Produit) comboBoxProduit.getSelectionModel().getSelectedItem();
            if(!isProduitExist(vente.getItemeDeVente(), produit)){
                if(produit.getQteEnStk() >= Integer.parseInt(textFieldQteProduit.getText())){
                        itemeDeVente.setProduit((Produit) comboBoxProduit.getSelectionModel().getSelectedItem());
                        itemeDeVente.setQteCommande(Double.parseDouble(textFieldQteProduit.getText()));
                        itemeDeVente.setSValeur(itemeDeVente.getProduit().getPrixU()* itemeDeVente.getQteCommande());
                        
                        System.out.println("Designation categ : " + produit.getCategorie().getDesignation());

                        vente.getItemeDeVente().add(itemeDeVente);
                        vente.setTValeur((vente.getTValeur()!= null ? vente.getTValeur(): Double.parseDouble("0")) + itemeDeVente.getSValeur());
                        observableListItemeDeVentes= FXCollections.observableArrayList(vente.getItemeDeVente());
                        tableViewItemeVente.setItems(observableListItemeDeVentes);

                        textFieldValeurVente.setText(String.format("%.2f", vente.getTValeur()));
                }else{
                     // Show the error message.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(stage);
                    alert.setHeaderText("Ajout de produit invalide");
                    alert.setContentText("La quantite en stoque de ce produit est insuffisante");

                    alert.showAndWait();
                }
            }else{
                 // Show the error message.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(stage);
                    alert.setHeaderText("Ajout de produit invalide");
                    alert.setContentText("Vous avez déjà ajouté ce produit : '" + produit.getDesignation() + "'");

                    alert.showAndWait();
            }
        }else{
            // Show the error message.
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.initOwner(stage);
           alert.setHeaderText("Ajout de produit invalide");
           alert.setContentText("Veuiller selectioner le produit à ajouter ou verifier la quantité commandée par le client");

           alert.showAndWait();
        }
    }
    
      //verifier si la vente ne contient aucun produit identique
    public boolean isProduitExist(List<ItemeDeVente> itemeDeVente, Produit produit){
        boolean  exist= false;
         for(ItemeDeVente listeItemeDeVente:itemeDeVente){
               if(listeItemeDeVente.getProduit().getCodePro()  == produit.getCodePro()  )
               {exist = true;}
           }
       
        return exist;
    }

    @FXML
    private void handleConfirmerVente(ActionEvent event) {
        if(isInputValid()){
            vente.setClient((Client)comboBoxClient.getSelectionModel().getSelectedItem());
            vente.setDateVente(datePickerDateVente.getValue());
            vente.setPayer(chexkBoxPayer.isSelected());

            buttonValiderClicked = true;
            stage.close();
        }
    }

    @FXML
    private void handleAnnulerVente(ActionEvent event) {
        stage.close();
    }
    
    //afficher la liste des clients et la liste des produits
     //afficher la liste des clients disponible
    public void afficherComboBoxClient(){
        listeClients= clientDao.listeClient();
        observableListClient = FXCollections.observableArrayList(listeClients);
        comboBoxClient.setItems(observableListClient);
    }
    
      //afficher la liste des produit disponible
    public void afficherComboBoxProduit(){
        listeProduits = produitDao.listeProduits();    
        observableListProduit = FXCollections.observableArrayList(listeProduits);
        comboBoxProduit.setItems(observableListProduit);
    }
    
       /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        
        if (comboBoxClient.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Choisir un client!\n";
        }
        if (datePickerDateVente.getValue() == null) {
            errorMessage += "La date de la vente est invalide!\n";
        } 
        if (observableListItemeDeVentes == null ) {
            errorMessage += "Selectionner au moin un produit\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Element invalide");
            alert.setHeaderText("SVP, corriger l'erreur si vous voulez valider la vente");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
      @FXML
    private void handleEditPorduct(ActionEvent event) {
        ItemeDeVente i  = tableViewItemeVente.getSelectionModel().getSelectedItem();
        if( i != null){
         comboBoxProduit.setValue(i.getProduit());
         
          vente.setTValeur(vente.getTValeur() - i.getSValeur());
          textFieldValeurVente.setText(String.format("%.2f", vente.getTValeur()));
          int index = tableViewItemeVente.getSelectionModel().getSelectedIndex() ;
          vente.getItemeDeVente().remove(index);
          observableListItemeDeVentes= FXCollections.observableArrayList(vente.getItemeDeVente());
          tableViewItemeVente.setItems(observableListItemeDeVentes);
          
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun produit selectionné!!");
            alert.show();
        }
    }

    @FXML
    private void handelDeleteProduct(ActionEvent event) {
        ItemeDeVente i  = tableViewItemeVente.getSelectionModel().getSelectedItem();
        if(i != null){ 
         
          vente.setTValeur(vente.getTValeur() - i.getSValeur());
          textFieldValeurVente.setText(String.format("%.2f", vente.getTValeur()));
          int index = tableViewItemeVente.getSelectionModel().getSelectedIndex() ;
          vente.getItemeDeVente().remove(index);
          observableListItemeDeVentes= FXCollections.observableArrayList(vente.getItemeDeVente());
          tableViewItemeVente.setItems(observableListItemeDeVentes);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun produit selectionné!!");
            alert.show();
        }
    }
    
    
}
