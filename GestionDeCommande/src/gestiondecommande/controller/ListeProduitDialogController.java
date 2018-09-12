/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import gestiondecommande.model.domain.Convert;
import gestiondecommande.model.domain.ListeProduit;
import gestiondecommande.model.domain.Produit;
import gestiondecommande.model.domain.Vente;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class ListeProduitDialogController implements Initializable {

     private ObservableList<ListeProduit> observableListeProduit;
    
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
    private TableView<ListeProduit> tableViewListePorduit;
    @FXML
    private TableColumn columnCodePro;
    @FXML
    private TableColumn columnDesigantion;
    @FXML
    private TableColumn columnPU;
    @FXML
    private TableColumn<ListeProduit, String> columnQte;
    @FXML
    private TableColumn<ListeProduit, Double> columnSTotal;

    private Vente vente;
    private List<ListeProduit> listeProduit;

    public List<ListeProduit> getListeProduit() {
        return listeProduit;
    }

    public void setListeProduit(List<ListeProduit> listeProduit) {
        this.listeProduit = listeProduit;
        
        columnCodePro.setCellValueFactory(new PropertyValueFactory<>("codePro"));
        columnDesigantion.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnPU.setCellValueFactory(new PropertyValueFactory<>("PU"));
        columnQte.setCellValueFactory(new PropertyValueFactory<>("qteCommande"));
        columnSTotal.setCellValueFactory(new PropertyValueFactory<>("valeur"));
    
        observableListeProduit = FXCollections.observableArrayList(this.listeProduit);
        tableViewListePorduit.setItems(observableListeProduit);
    }
    

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
        
        labelCodeVente.setText(Integer.toString(vente.getCodeVente()));
        dateVente.setText(String.valueOf(vente.getDateVente().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        etatpayer.setText( vente.isPayer()? "Payé":"non");
        totalNet.setText(String.format("%.2f", vente.getTValeur()));
        
        codeClient.setText(Integer.toString(vente.getClient().getCodeClient()));
        nomClient.setText(vente.getClient().getNom());
        numTel.setText(vente.getClient().getNumTel());
        CA.setText(Double.toString(vente.getClient().getChiffreDaffaire()));
        
    }
    
     public void genererFacture(){
        Document document = new Document();
        try 
        {
          PdfWriter.getInstance(document, new FileOutputStream("Facture"+vente.getDateVente() +"_"+ vente.getCodeVente()+"_" + vente.getClient() +".pdf"));
          document.open();
         
          //titre du document
          document.addTitle("Facture");
          document.addSubject("Generation du facture d un client");
          
          
          
          Paragraph fistPage = new Paragraph();
          

          fistPage.add(new Paragraph(new Chunk("Facture", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD))));
          fistPage.add(new Paragraph(" "));
          fistPage.add(new Paragraph(" "));
          
          Paragraph p1 = new Paragraph();
          p1.add(new Chunk("Code Commande : " + Integer.toString(vente.getCodeVente())));
          fistPage.add(p1);
    
          
          Paragraph p2 = new Paragraph();
          p2.add(new Chunk("Date Commande : " + vente.getDateVente().toString()));
          fistPage.add(p2);
          
          fistPage.add(new Paragraph(" "));
          
          Paragraph p3 = new Paragraph();
          p3.add(new Chunk("Code client : " + Integer.toString(vente.getClient().getCodeClient())));
          fistPage.add(p3);
          
          Paragraph p4 = new Paragraph();
          p4.add(new Chunk("Nom : " + vente.getClient().getNom()));
          fistPage.add(p4);

          Paragraph p6 = new Paragraph();
          p6.add(new Chunk("Tel : " + vente.getClient().getNumTel()));
          fistPage.add(p6);
          
          fistPage.add(new Paragraph(" "));
          fistPage.add(new Paragraph(" "));
          
          document.add(fistPage);
             
          document.add(premierTableau());
          document.add(new Paragraph(""));

          document.add(new Paragraph("Net a payer : " + Convert.FR(Double.toString(vente.getTValeur()))));

        }catch (DocumentException d){
        
        } catch (IOException de) {
            de.printStackTrace();
        } 

        document.close();
        
        // Show the error message.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.initOwner(stage);
            alert.setTitle("Generation pdf");
            alert.setContentText("La facture est imprimer avec succes");

            alert.showAndWait();
    }
     
     //Classe permmettant de déssiner un tableau.

  public PdfPTable premierTableau()
  {
      //On créer un objet table dans lequel on intialise ça taille.
      PdfPTable table = new PdfPTable(5);
      
       PdfPCell cell;
      cell = new PdfPCell(new Phrase("Code Pro"));

      table.addCell(cell);
      
      table.addCell("Designation");
      table.addCell("P.U");
      table.addCell("Quantite");
      table.addCell("Totale");
      
      for(ListeProduit produit: this.listeProduit){
            table.addCell(Integer.toString(produit.getCodePro()));
            table.addCell(produit.getDesignation());
            table.addCell(Double.toString(produit.getPU()));
            table.addCell(Double.toString(produit.getQteCommande()));
//            table.addCell(String.valueOf(produit.getDateEnStk().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            table.addCell(Double.toString(produit.getsTotal()));
           
      }
      
      table.addCell("");
      table.addCell("");
      table.addCell("");
      table.addCell("Totale");
      table.addCell(Double.toString(vente.getTValeur()) + " Ar");
      
      return table;  
  }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
