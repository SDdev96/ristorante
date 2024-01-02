package ristorante;

import java.io.Serializable;

public class Ordinazione implements Serializable {
    private String piatto;
    private int tavolo;
    private int quantita;

    /**
     * Costruttore per una generica {@code ordinazione}
     * 
     * @param piatto
     * @param tavolo
     * @param quantita
     * 
     */
    public Ordinazione(String piatto, int tavolo, int quantita) {
        this.piatto = piatto;
        this.tavolo = tavolo;
        this.quantita = quantita;
    }

    

    public final String getPiatto() {
        return piatto;
    }

    public final Integer getTavolo() {
        return tavolo;
    }

    public final Integer getQuantita() {
        return quantita;
    }

    public final void setPiatto(String piatto) {
        this.piatto = piatto;
    }

    public final void setTavolo(int tavolo) {
        this.tavolo = tavolo;
    }

    public final void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "Piatto: " + piatto + ", Tavolo: " + tavolo + ", Quantità: " + quantita;
    }
}
