
package gestiondecommande.model.domain;

/**
 *
 * @author johnyftr
 */
public class ListeProduit {
    private int codePro;
    private String designation;
    private Double PU;
    private int qteCommande;
    private Double sTotal;
    private Double valeur;

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

    public Double getPU() {
        return PU;
    }

    public void setPU(Double PU) {
        this.PU = PU;
    }

    public int getQteCommande() {
        return qteCommande;
    }

    public void setQteCommande(int qteCommande) {
        this.qteCommande = qteCommande;
    }

    public Double getsTotal() {
        return sTotal;
    }

    public void setsTotal(Double sTotal) {
        this.sTotal = sTotal;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }
    

    
}
