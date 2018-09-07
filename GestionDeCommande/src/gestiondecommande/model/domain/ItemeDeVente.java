/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.model.domain;

import java.io.Serializable;

/**
 *
 * @author johnyftr
 */
public class ItemeDeVente implements Serializable{
    private Vente vente;
    private Produit produit;
    private Double QteCommande;
    private Double SValeur;
    

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Double getQteCommande() {
        return QteCommande;
    }

    public void setQteCommande(Double QteCommande) {
        this.QteCommande = QteCommande;
    }

    public Double getSValeur() {
        return SValeur;
    }

    public void setSValeur(Double SValeur) {
        this.SValeur = SValeur;
    }
    
    
}
