
package gestiondecommande.model.domain;

import java.time.LocalDate;

/**
 *
 * @author johnyftr
 */
public class ProduitDate {
   private int codeVente;
   private int codePorduit;
   private String designation;
   private LocalDate datevente;
   private Double qteCommande;
   private Double sousValeur;
   private Client client;

    public int getCodeVente() {
        return codeVente;
    }

    public void setCodeVente(int codeVente) {
        this.codeVente = codeVente;
    }

    public int getCodePorduit() {
        return codePorduit;
    }

    public void setCodePorduit(int codePorduit) {
        this.codePorduit = codePorduit;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDate getDatevente() {
        return datevente;
    }

    public void setDatevente(LocalDate datevente) {
        this.datevente = datevente;
    }

    public Double getQteCommande() {
        return qteCommande;
    }

    public void setQteCommande(Double qteCommande) {
        this.qteCommande = qteCommande;
    }

    public Double getSousValeur() {
        return sousValeur;
    }

    public void setSousValeur(Double sousValeur) {
        this.sousValeur = sousValeur;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

   
}
