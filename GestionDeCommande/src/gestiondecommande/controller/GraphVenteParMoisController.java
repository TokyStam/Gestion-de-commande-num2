/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.controller;

import gestiondecommande.model.dao.VenteDao;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class GraphVenteParMoisController implements Initializable {

    @FXML
    private BarChart barChartParMois;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private NumberAxis numberAxis;
    
    private ObservableList<String> observableListeMois  = FXCollections.observableArrayList();
    private final VenteDao venteDao;

    public GraphVenteParMoisController() throws SQLException {
        this.venteDao = new VenteDao();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        String[] arrayMois = {"janv", "fev", "mars", "avri", "mai", "jun", "juil", "aout", "sept", "oct", "nov", "dec"};
        observableListeMois.addAll(arrayMois);
        
        categoryAxis.setCategories(observableListeMois);
        Map<Integer, ArrayList> dates = venteDao.ListeQteVenteParMoi();
        
        for( Map.Entry<Integer, ArrayList> dateItem: dates.entrySet()){
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(dateItem.getKey().toString());
            
            for(int i = 0; i < dateItem.getValue().size(); i = i + 2){
                String mois;
                Integer quantite;
                mois = retourneNomMois((int) dateItem.getValue().get(i));
                quantite = (Integer) dateItem.getValue().get(i + 1);
                series.getData().add(new XYChart.Data<>(mois, quantite));
            }
            barChartParMois.getData().add(series);
        }
                
    }    
    
    
    public String retourneNomMois(int mois){
        switch(mois){
            case 1: 
                return "janv";
            case 2: 
                return "fev";
             case 3: 
                return "mars";
            case 4: 
                return "avril";
            case 5: 
                return "mai";
            case 6: 
                return "jun";
             case 7: 
                return "jul";
            case 8: 
                return "aout";
            case 9: 
                return "sept";
            case 10: 
                return "oct";
             case 11: 
                return "nov";
            case 12: 
                return "dec";
        }
        return null;
       
    }
    
}
