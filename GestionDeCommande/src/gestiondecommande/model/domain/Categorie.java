/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.model.domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author johnyftr
 */
public class Categorie implements Serializable{
    private int codeCateg;
    private String designation;
    private List<Produit> listeProduit;
    private String commenataire;
    private String codeString;

    public String getCodeString() {
        return codeString;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString;
    }

    public int getCodeCateg() {
        return codeCateg;
    }

    public void setCodeCateg(int codeCateg) {
        this.codeCateg = codeCateg;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCommenataire() {
        return commenataire;
    }

    public void setCommenataire(String commenataire) {
        this.commenataire = commenataire;
    }

    public List<Produit> getListeProduit() {
        return listeProduit;
    }

    public void setListeProduit(List<Produit> listeProduit) {
        this.listeProduit = listeProduit;
    }

    @Override
    public String toString() {
        return  designation;
    }
    
    
}
