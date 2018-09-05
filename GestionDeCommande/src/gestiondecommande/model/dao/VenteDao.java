/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.model.dao;

import gestiondecommande.model.database.ConnectionsDB;
import gestiondecommande.model.domain.Client;
import gestiondecommande.model.domain.ItemeDeVente;
import gestiondecommande.model.domain.ListeProduit;
import gestiondecommande.model.domain.Produit;
import gestiondecommande.model.domain.ProduitDate;
import gestiondecommande.model.domain.Vente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johnyftr
 */
public class VenteDao {
  static Connection cnx;
    
    public VenteDao() throws SQLException{
        this.cnx = new ConnectionsDB().getConnect();
    }
    //creer un nouvereau utilisateut
    public boolean create(Vente vente){
        String sql = "INSERT INTO vente(valeur, payer, codeUser, dateVente) VALUES(?, ?, ?, ?)";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setDouble(1, vente.getTValeur());
            st.setBoolean(2, vente.isPayer());
            st.setInt(3, vente.getClient().getCodeClient());
            st.setDate(4,Date.valueOf(vente.getDateVente()));
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(VenteDao.class.getName()).log(Level.SEVERE, null, ex); 
            return false;
        }
    }
    
    //suprimer un utilisateur
     public boolean delete(Vente vente){
        String sql = "DELETE FROM vente WHERE cVente = ?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, vente.getCodeVente());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(VenteDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
       //suprimer un utilisateur
     public boolean deleteClientVente(Client client){
        String sql = "DELETE FROM vente WHERE codeUser= ?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, client.getCodeClient());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(VenteDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
   
    // liste les utlisateurs
    public List<Vente> listeVente(){ 
        String sql = "SELECT *FROM vente";
        List<Vente> resultat = new ArrayList<>();
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
                Vente vente = new Vente();
                Client client = new Client();
                List<ItemeDeVente> itemProduit = new ArrayList();
                 
                vente.setCodeVente(res.getInt("cVente"));
                vente.setTValeur(res.getDouble("valeur"));
                vente.setPayer(res.getBoolean("payer"));
                vente.setDateVente(res.getDate("dateVente").toLocalDate());
                client.setCodeClient(res.getInt("codeUser"));

                //recuper le client 
                ClientDao userDao = new ClientDao();
                client = userDao.showUser(client);
                
                //recuperer les iteme
                itemeDeVenteDao itemeDeVenteDao = new itemeDeVenteDao();
                itemProduit = itemeDeVenteDao.listeProduitDeVente(vente);
                
                vente.setClient(client);
                vente.setItemeDeVente(itemProduit);
                 
                resultat.add(vente);
            }
         
        }catch(SQLException ex){
            Logger.getLogger(VenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
    
    //show vnete
    public Vente showVente(Vente vente){
        String sql = "SELECT *FROM vente WHERE cVente=" + vente.getCodeVente();
            PreparedStatement st;
            Vente us = new Vente(); 
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            if(res.next()){
                us.setCodeVente(res.getInt("cVente"));
                us.setTValeur(res.getDouble("valeur"));
                us.setPayer(res.getBoolean("payer"));
                us.setDateVente(res.getDate("dateVente").toLocalDate());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return us;
    }
    
     //show vnete
    public List<ListeProduit> listeProduit(Vente vente){
        String sql = "SELECT produits.numPro AS codePro, produits.designation AS designation, itemeproduit.quantite AS quantite, produits.prix AS PU, itemeproduit.quantite * produits.prix AS Valeur FROM itemeproduit, produits, vente WHERE (vente.cVente=" + vente.getCodeVente() + ") ANd (itemeproduit.cVente=vente.cVente) AND (itemeproduit.numPro=produits.numPro)";
            PreparedStatement st;
            List<ListeProduit> listeProduit = new ArrayList();
             
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
           
            while(res.next()){
                ListeProduit us = new ListeProduit(); 
                
                us.setCodePro(res.getInt("codePro"));
                us.setDesignation(res.getString("designation"));
                us.setQteCommande(res.getInt("quantite"));
                us.setPU(res.getDouble("PU"));
                us.setValeur(res.getDouble("valeur"));
                us.setsTotal(res.getDouble("Valeur"));
             
                
               listeProduit.add(us);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return listeProduit;
    }
    
    public Vente recupererDerniereVente(){
        String sql = "SELECT*FROM vente ORDER BY cVente DESC LIMIT 1";
            PreparedStatement st;
            Vente resultat = new Vente();
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            if(res.next()){
                resultat.setCodeVente(res.getInt("cVente"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
     
    }
    
    public Map<Integer, ArrayList> ListeQteVenteParMoi(){
         String sql = "SELECT count(cVente) as count, EXTRACT(year from dateVente) as ans, EXTRACT(month from dateVente) as mois FROM vente GROUP BY ans,mois ORDER BY ans, mois";
         Map<Integer, ArrayList> retourner = new HashMap();
          try {
            PreparedStatement st;
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            while(res.next()){
                ArrayList lien = new ArrayList();
                if(!retourner.containsKey(res.getInt("ans"))){
                    
                    lien.add(res.getInt("mois"));
                    lien.add(res.getInt("count"));
                    retourner.put(res.getInt("ans"), lien);
                }else{
                    ArrayList LienNouveau = retourner.get(res.getInt("ans"));
                    LienNouveau.add(res.getInt("mois"));
                    LienNouveau.add(res.getInt("count"));
                }
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retourner;
    }
    
   //creer un nouvereau utilisateut
    public boolean update(Vente vente){
        String sql = "UPDATE vente SET valeur=?, payer=?, codeUser=?, dateVente=? WHERE cVente=?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setDouble(1, vente.getTValeur());
            st.setBoolean(2, vente.isPayer());
            st.setInt(3, vente.getClient().getCodeClient());
            st.setDate(4,Date.valueOf(vente.getDateVente()));
            st.setInt(5, vente.getCodeVente());
            
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(VenteDao.class.getName()).log(Level.SEVERE, null, ex); 
            return false;
        }
    }
    
     //show vnete
    public List<ProduitDate> venteEntre2Date(Client client, LocalDate date1, LocalDate date2){
        String sql = "Select *FROM vente, produits, itemeproduit, user WHERE\n" +
" (user.id = vente.codeUser) AND (vente.cVente = itemeproduit.cVente) AND \n" +
" (produits.numPro = itemeproduit.numPro) AND (user.id= "+client.getCodeClient()+") AND (vente.dateVente BETWEEN '"+date1+"' AND '"+date2+"')";
            PreparedStatement st;
             List<ProduitDate> resultat = new ArrayList();
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
           
            while(res.next()){
                ProduitDate us = new ProduitDate();
                
                us.setCodePorduit(res.getInt("produits.numPro"));
                us.setCodeVente(res.getInt("vente.cVente"));
                us.setDesignation(res.getString("produits.designation"));
                us.setDatevente(res.getDate("dateVente").toLocalDate());
                us.setQteCommande(res.getDouble("itemeproduit.quantite"));
                us.setSousValeur(res.getDouble("itemeproduit.valeur"));
                resultat.add(us);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
    
     //show vnete
    public List<ProduitDate> venteParCritere(Client client, String critere, int date){
        critere = critere == "Mois"? "month": "year";
        String sql ="Select * FROM itemeproduit, produits, vente, user WHERE\n" +
" (user.id = vente.codeUser) AND (vente.cVente = itemeproduit.cVente) AND \n" +
" (produits.numPro = itemeproduit.numPro) AND (user.id="+client.getCodeClient()+") AND EXTRACT("+ critere +" from vente.dateVente) = '"+ date +"'";
                PreparedStatement st;
             List<ProduitDate> resultat = new ArrayList();
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
           
            while(res.next()){
                ProduitDate us = new ProduitDate();
                
                us.setCodePorduit(res.getInt("produits.numPro"));
                us.setCodeVente(res.getInt("vente.cVente"));
                us.setDesignation(res.getString("produits.designation"));
                us.setDatevente(res.getDate("dateVente").toLocalDate());
                us.setQteCommande(res.getDouble("itemeproduit.quantite"));
                us.setSousValeur(res.getDouble("itemeproduit.valeur"));
                resultat.add(us);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
    
}


//vente dans between deux date 
//



//------------vente par moi ou par annnee
//
//Select * FROM itemeproduit, produits, vente, user WHERE
// (user.id = vente.codeUser) AND (vente.cVente = itemeproduit.cVente) AND 
// (produits.numPro = itemeproduit.numPro) AND (user.id=1) AND EXTRACT(month from vente.dateVente) = '09'
