/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import gestiondecommande.model.dao.VenteDao;
import gestiondecommande.model.dao.itemeDeVenteDao;
import gestiondecommande.model.domain.Vente;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class ListeProduitDialogController implements Initializable {

     private List<Vente> listeVente;
     private final VenteDao venteDao;
     private ObservableList<Vente>ObservableListVente;
     private final itemeDeVenteDao itemeDeVenteDao;
    
    @FXML
    private Label labelCodeVente;
    @FXML
    private Label dateVente;
    @FXML
    private Label etatpayer;
    @FXML
    private Label codeClient;
    @FXML
    private Label nomClient;
    @FXML
    private Label numTel;
    @FXML
    private Label CA;
    @FXML
    private Label totalNet;
    @FXML
    private TableView<Vente> tableViewListePorduit;
    @FXML
    private TableColumn columnCodePro;
    @FXML
    private TableColumn columnDesigantion;
    @FXML
    private TableColumn columnPU;
    @FXML
    private TableColumn columnQte;
    @FXML
    private TableColumn columnSTotal;

    private Vente vente;

    public ListeProduitDialogController() throws SQLException {
        this.itemeDeVenteDao = new itemeDeVenteDao();
        this.venteDao = new VenteDao();
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
        
        labelCodeVente.setText(Integer.toString(vente.getCodeVente()));
        dateVente.setText(String.valueOf(vente.getDateVente().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        etatpayer.setText( vente.isPayer()? "Payé":"non");
        
        codeClient.setText(Integer.toString(vente.getClient().getCodeClient()));
        nomClient.setText(vente.getClient().getNom());
        numTel.setText(vente.getClient().getNumTel());
        CA.setText(Double.toString(vente.getClient().getChiffreDaffaire()));
        
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
    }    
    
}
