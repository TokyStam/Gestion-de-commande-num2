/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.model.dao;

import static gestiondecommande.model.dao.ProduitDao.cnx;
import gestiondecommande.model.database.ConnectionsDB;
import gestiondecommande.model.domain.Categorie;
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
public class CategorieDao {
    static Connection cnx;
    
    public CategorieDao() throws SQLException{
        this.cnx = new ConnectionsDB().getConnect();
    }
    //creer un nouvereau utilisateut
    public boolean create(Categorie categorie){
        String sql = "INSERT INTO categorie( designation, commentaire) VALUES(?, ?)";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setString(1, categorie.getDesignation());
            st.setString(2, categorie.getCommenataire());
          
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(CategorieDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //suprimer un utilisateur
     public boolean delete(Categorie categorie){
        String sql = "DELETE FROM categorie WHERE codeCateg = ?";      
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, categorie.getCodeCateg());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(CategorieDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    // modifier un utilisateur
    public boolean Update(Categorie categorie){
        String sql = "UPDATE categorie SET designation=?, commentaire=? WHERE codeCateg = ?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setString(1, categorie.getDesignation());
            st.setString(2,categorie.getCommenataire());
            
            st.setInt(3, categorie.getCodeCateg());
            
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //show user
    public Categorie showCategorie(Categorie categorie){
        String sql = "SELECT *FROM categorie WHERE codeCateg=" + categorie.getCodeCateg();
            PreparedStatement st;
            Categorie us = new Categorie(); 
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            if(res.next()){
                us.setDesignation(res.getString("designation"));
                us.setCommenataire(res.getString("commentaire"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
        }
         return us;
    }
    
    // liste les utlisateurs
    public List<Categorie> listeCategorie(){
        String sql = "SELECT *FROM categorie";
        List<Categorie> resultat = new ArrayList<>();
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
                Categorie us = new Categorie(); 
                us.setCodeCateg(res.getInt("codeCateg"));
                us.setDesignation(res.getString("designation"));
                us.setCommenataire(res.getString("commentaire"));
                resultat.add(us);
            }
         
        }catch(SQLException ex){
            Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
    
     // liste les utlisateurs
    public List<Produit> listeProduitD1Categorie(Categorie categorie){
        String sql = "SELECT*FROM produits WHERE codeCategorie = "+ categorie.getCodeCateg();
         List<Produit> resultat = new ArrayList<>();
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
                Produit us = new Produit(); 
                
                us.setCategorie(categorie);
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
