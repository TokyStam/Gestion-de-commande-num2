/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande;
import gestiondecommande.model.domain.Convert;
import gestiondecommande.controller.ConteneurController;
import gestiondecommande.model.dao.CategorieDao;
import gestiondecommande.model.dao.ProduitDao;
import gestiondecommande.model.domain.Categorie;
import gestiondecommande.model.domain.Produit;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author johnyftr
 */
public class GestionDeCommande extends Application {
    public Stage dialogStage = null;
    @Override
    public void start(Stage stage) throws Exception {    
         // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/conteneur.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        // Create the dialog Stage.
//        Stage dialogStage = new Stage();
        dialogStage = stage;
        dialogStage.setResizable(false);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        // Set the user into the controller.
        ConteneurController controller = loader.getController();
        controller.setStage(dialogStage);
        // Show the dialog and wait until the user closes it
        dialogStage.show();

        //----------------------------------
//        Parent root = FXMLLoader.load(getClass().getResource("view/conteneur.fxml"));
//        
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.show();


//        CategorieDao d = new CategorieDao();
//         List<Categorie> f = d.listeCategorie();
//         
//         for(Categorie c: f){
//            System.out.println(c.getDesignation());
//        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
