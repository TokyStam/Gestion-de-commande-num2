/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author johnyftr
 */
public class Client implements Serializable{
    private int codeClient;
    private String nom;
    private Double chiffreDaffaire;
    private LocalDate dateNais;
    private String numTel;
    private List<CAparAnnee> caParAnnee;

    public List<CAparAnnee> getCaParAnnee() {
        return caParAnnee;
    }

    public void setCaParAnnee(List<CAparAnnee> caParAnnee) {
        this.caParAnnee = caParAnnee;
    }

    public int getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(int codeClient) {
        this.codeClient = codeClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getChiffreDaffaire() {
        return chiffreDaffaire;
    }

    public void setChiffreDaffaire(Double chiffreDaffaire) {
        this.chiffreDaffaire = chiffreDaffaire;
    }

    public LocalDate getDateNais() {
        return dateNais;
    }

    public void setDateNais(LocalDate dateNais) {
        this.dateNais = dateNais;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    @Override
    public String toString() {
        return  nom ;
    }
    
    
}
