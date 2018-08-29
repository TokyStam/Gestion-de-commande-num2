/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.model.dao;

import gestiondecommande.model.database.ConnectionsDB;
import gestiondecommande.model.domain.Categorie;
import gestiondecommande.model.domain.ItemeDeVente;
import gestiondecommande.model.domain.Produit;
import java.sql.Connection;
import java.sql.Date;
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
public class ProduitDao {
    static Connection cnx;
    
    public ProduitDao() throws SQLException{
        this.cnx = new ConnectionsDB().getConnect();
    }
    //creer un nouvereau utilisateut
    public boolean create(Produit produit){
        String sql = "INSERT INTO produits( codeCategorie, designation, prix, qteEnStk, commentaire, dateDebutStk) VALUES(?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, produit.getCategorie().getCodeCateg());
            st.setString(2, produit.getDesignation());
            st.setDouble(3, produit.getPrixU());
            st.setDouble(4, produit.getQteEnStk());
            st.setString(5, produit.getCommentaire());
            st.setDate(6,Date.valueOf(produit.getDateEnStk()));
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //suprimer un utilisateur
     public boolean delete(Produit produit){
        String sql = "DELETE FROM produits WHERE numPro = ?";
        
        try {
            itemeDeVenteDao i = new itemeDeVenteDao();
            i.deleteClientProduit(produit.getCodePro());
            
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, produit.getCodePro());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
      
     
    // modifier un produit
    public boolean Update(Produit produit){
        String sql = "UPDATE produits SET codeCategorie=?, designation=?, prix=?, qteEnStk=?, commentaire=?, dateDebutStk=? WHERE numPro = ?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, produit.getCategorie().getCodeCateg());
            st.setString(2, produit.getDesignation());
            st.setDouble(3, produit.getPrixU());
            st.setDouble(4, produit.getQteEnStk());
            st.setString(5, produit.getCommentaire());
            st.setDate(6,Date.valueOf(produit.getDateEnStk())); 
            st.setInt(7, produit.getCodePro());
            
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
      // modifier un produit
    public boolean UpdateQte(Produit produit){
        String sql = "UPDATE produits SET  qteEnStk=? WHERE numPro =?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setDouble(1, produit.getQteEnStk());
            st.setInt(2, produit.getCodePro());
            
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    //one Produit
    public Produit showProduit(Produit produit){
            String sql = "SELECT *FROM produits WHERE numPro=" + produit.getCodePro();
            PreparedStatement st;
            Produit us = new Produit(); 
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            if(res.next()){
                //creation d un DAO pour ajouter un categorie a un produit
                Categorie categ = new Categorie();
                categ.setCodeCateg(res.getInt("codeCategorie"));
                
                CategorieDao categDao = new CategorieDao();
                categ = categDao.showCategorie(categ);
                
                us.setCategorie(categ);
                us.setCodePro(res.getInt("numPro"));
                us.setDesignation(res.getString("designation"));
                us.setCommentaire(res.getString("commentaire"));
                us.setPrixU(res.getDouble("prix"));
                us.setQteEnStk(res.getDouble("qteEnStk"));
                us.setDateEnStk(res.getDate("dateDebutStk").toLocalDate()); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return us;
    }
    // liste des produit
    public List<Produit> listeProduits(){
        String sql = "SELECT *FROM produits";
        List<Produit> resultat = new ArrayList<>();
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
                Produit us = new Produit(); 
               //creation d un DAO pour ajouter un categorie a un produit
                Categorie categ = new Categorie();
                categ.setCodeCateg(res.getInt("codeCategorie"));
                
                CategorieDao categDao = new CategorieDao();
                categ = categDao.showCategorie(categ);
                
                us.setCategorie(categ);
                us.setCodePro(res.getInt("numPro"));
                us.setDesignation(res.getString("designation"));
                us.setPrixU(res.getDouble("prix"));
                us.setCommentaire(res.getString("commentaire"));
                us.setQteEnStk(res.getDouble("qteEnStk"));
                us.setDateEnStk(res.getDate("dateDebutStk").toLocalDate());
                
                resultat.add(us);
            }
         
        }catch(SQLException ex){
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
}
