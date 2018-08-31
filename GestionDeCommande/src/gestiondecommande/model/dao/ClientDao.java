/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.model.dao;

import gestiondecommande.model.database.ConnectionsDB;
import gestiondecommande.model.domain.Client;
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
public class ClientDao {
    static Connection cnx;
    
    public ClientDao() throws SQLException{
        this.cnx = new ConnectionsDB().getConnect();
    }
    //creer un nouvereau utilisateut
    public boolean create(Client client){
        String sql = "INSERT INTO user( nom, chiffreDaffaire, dateNais, numTel) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setString(1, client.getNom());
            st.setDouble(2, client.getChiffreDaffaire());
            st.setDate(3,Date.valueOf(client.getDateNais()));
            st.setString(4, client.getNumTel());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //suprimer un utilisateur
     public boolean delete(Client client){
        String sql = "DELETE FROM user WHERE id = ?";
        
        try {
            VenteDao venteDao = new VenteDao();
            venteDao.deleteClientVente(client);
            
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, client.getCodeClient());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    // modifier un utilisateur
    public boolean Update(Client client){
        String sql = "UPDATE user SET nom=?, numTel=?, dateNais=?, chiffreDaffaire=? WHERE id = ?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setString(1, client.getNom());
            st.setString(2, client.getNumTel());
            st.setDate(3,Date.valueOf(client.getDateNais()));
            st.setDouble(4, client.getChiffreDaffaire());  
            st.setInt(5, client.getCodeClient());
            
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //show user
    public Client showUser(Client client){
        String sql = "SELECT *FROM user WHERE id=" + client.getCodeClient();
            PreparedStatement st;
            Client us = new Client(); 
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            if(res.next()){
                us.setCodeClient(res.getInt("id"));
                us.setChiffreDaffaire(res.getDouble("age"));
                us.setNom(res.getString("nom"));
                us.setNumTel(res.getString("numTel"));
                us.setDateNais(res.getDate("dateNais").toLocalDate());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return us;
    }
    
    // liste les utlisateurs
    public List<Client> listeClient(){
        String sql = "SELECT *FROM user";
        List<Client> resultat = new ArrayList<>();
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
                Client us = new Client();
                ClientDao clientdao = new ClientDao();
                
                us.setCodeClient(res.getInt("id"));
                us.setChiffreDaffaire(clientdao.chiffreDaffaire(us));
                us.setNom(res.getString("nom"));
                us.setNumTel(res.getString("numTel"));
                us.setDateNais(res.getDate("dateNais").toLocalDate());
                resultat.add(us);
            }
         
        }catch(SQLException ex){
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
    
    // chercher un client
    public List<Client> searchClient(String column, String mot){
        String sql = "SELECT *FROM user WHERE " + column + " LIKE '%" + mot + "%'";
        List<Client> resultat = new ArrayList<>();
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
                Client us = new Client();
                ClientDao clientdao = new ClientDao();
                
                us.setCodeClient(res.getInt("id"));
                us.setChiffreDaffaire(clientdao.chiffreDaffaire(us));
                us.setNom(res.getString("nom"));
                us.setNumTel(res.getString("numTel"));
                us.setDateNais(res.getDate("dateNais").toLocalDate());
                resultat.add(us);
            }
         
        }catch(SQLException ex){
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
    
    
     // liste les utlisateurs
    public Double chiffreDaffaire(Client client){
        String sql = "SELECT SUM(valeur) AS CA FROM vente WHERE codeUser=" + client.getCodeClient();
        Double resultat = null;
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
              resultat = res.getDouble("CA");
            }
         
        }catch(SQLException ex){
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
}

