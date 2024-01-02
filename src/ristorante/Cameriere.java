package ristorante;

import java.util.Random;

@SuppressWarnings("all")

public class Cameriere implements Runnable {
    private Comande comande;
    private String nome;
    private Menu menu;

    /**
     * Costruttore per un {@code cameriere} generico a cui viene associato un
     * {@code nome} e una {@code comanda}
     * 
     * @param nome
     * @param comande
     * 
     * @see {@link Comande#Comande(String, Boolean)}
     * @see {@link Menu#Menu()}
     */
    public Cameriere(String nome, Comande comande) {
        this.nome = nome;
        this.comande = comande;
        menu = new Menu();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            final int tavolo = new Random().nextInt(5) + 1;
            final int quantita = new Random().nextInt(4) + 1;
            final String piatto = menu.getPiatto();
            final int tempo = 5000 + new java.util.Random().nextInt(5000);
            try {
                Thread.sleep((long) tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Ordinazione ordinazione = new Ordinazione(piatto, tavolo, quantita);

            comande.aggiungiOrdinazione(ordinazione);
            System.out.println("Ordinazione presa da " + nome + ": " + ordinazione + "..Tempo atteso " + tempo + "ms");
        }

    }
}
