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
public class Vente implements Serializable{
    private int codeVente;
    private Client client;
    private LocalDate dateVente;
    private List<ItemeDeVente> itemeDeVente;
    private List<ItemeDeVente> tempeItemeDeVente;
    private Double TValeur;
    private boolean payer;

    public List<ItemeDeVente> getTempeItemeDeVente() {
        return tempeItemeDeVente;
    }

    public void setTempeItemeDeVente(List<ItemeDeVente> tempeItemeDeVente) {
        this.tempeItemeDeVente = tempeItemeDeVente;
    }

    
    public int getCodeVente() {
        return codeVente;
    }

    public void setCodeVente(int codeVente) {
        this.codeVente = codeVente;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente = dateVente;
    }

    public List<ItemeDeVente> getItemeDeVente() {
        return itemeDeVente;
    }

    public void setItemeDeVente(List<ItemeDeVente> itemeDeVente) {
        this.itemeDeVente = itemeDeVente;
    }

    public Double getTValeur() {
        return TValeur;
    }

    public void setTValeur(Double TValeur) {
        this.TValeur = TValeur;
    }

    public boolean isPayer() {
        return payer;
    }

    public void setPayer(boolean payer) {
        this.payer = payer;
    }
    
    
    
    
}
