/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.model.dao;

import gestiondecommande.model.database.ConnectionsDB;
import gestiondecommande.model.domain.ItemeDeVente;
import gestiondecommande.model.domain.Produit;
import gestiondecommande.model.domain.Vente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johnyftr
 */
public class itemeDeVenteDao {
  static Connection cnx;
    
    public itemeDeVenteDao() throws SQLException{
        this.cnx = new ConnectionsDB().getConnect();
    }
    //creer un nouvereau utilisateut
    public boolean create(ItemeDeVente itemeDeVente){
        String sql = "INSERT INTO itemeproduit( cVente, numPro, quantite, valeur) VALUES(?, ?, ?, ?)";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1,itemeDeVente.getVente().getCodeVente());
            st.setInt(2, itemeDeVente.getProduit().getCodePro());
            st.setDouble(3, itemeDeVente.getQteCommande());
            st.setDouble(4, itemeDeVente.getSValeur());
            
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ItemeDeVente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
      //suprimer un utilisateur
     public boolean deleteClientProduit(int code){
        String sql = "DELETE FROM itemeproduit WHERE numPro=?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, code);
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ItemeDeVente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
      //suprimer un utilisateurString cle, int code
     public boolean deleteAllItemeDeVente(Vente vente){
        String sql = "DELETE FROM itemeproduit WHERE cVente=?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, vente.getCodeVente());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ItemeDeVente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
          //suprimer un utilisateur
     public boolean deleteOneItem(ItemeDeVente itemProduit){
        String sql = "DELETE FROM itemeproduit WHERE cVente=? AND numPro=?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, itemProduit.getVente().getCodeVente());
            st.setInt(2, itemProduit.getProduit().getCodePro());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ItemeDeVente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    
    
    // liste des produit
    public List<ItemeDeVente> listeProduitDeVente(Vente vente){
        String sql = "SELECT *FROM itemeproduit WHERE cVente="+ vente.getCodeVente();
        List<ItemeDeVente> resultat = new ArrayList<>();
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
                //recuperer le produit
                Produit p = new Produit();
                ProduitDao pd=new ProduitDao();
                //recuperer le vente
                Vente v = new Vente();
                VenteDao vd = new VenteDao();
                
                //recuperer le numero de la vente et du produit
                p.setCodePro(res.getInt("numPro"));
                p = pd.showProduit(p);
                
                v.setCodeVente(res.getInt("cVente"));
                v = vd.showVente(vente);
                
                ItemeDeVente us = new ItemeDeVente();
                us.setProduit(p);
                us.setVente(v);
                us.setQteCommande(res.getDouble("quantite"));
                us.setSValeur(res.getDouble("valeur"));
                  
                resultat.add(us);
            }
         
        }catch(SQLException ex){
            Logger.getLogger(ItemeDeVente.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
}
 