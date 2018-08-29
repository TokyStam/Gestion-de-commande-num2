/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author johnyftr
 */
public class Produit implements Serializable{
    private Categorie categorie;
    private int codePro;
    private String designation;
    private Double prixU;
    private Double qteEnStk;
    private LocalDate dateEnStk;
    private String commentaire;

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public int getCodePro() {
        return codePro;
    }

    public void setCodePro(int codePro) {
        this.codePro = codePro;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getPrixU() {
        return prixU;
    }

    public void setPrixU(Double prixU) {
        this.prixU = prixU;
    }

    public Double getQteEnStk() {
        return qteEnStk;
    }

    public void setQteEnStk(Double qteEnStk) {
        this.qteEnStk = qteEnStk;
    }

    public LocalDate getDateEnStk() {
        return dateEnStk;
    }

    public void setDateEnStk(LocalDate dateEnStk) {
        this.dateEnStk = dateEnStk;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return  designation;
    }
    
}
