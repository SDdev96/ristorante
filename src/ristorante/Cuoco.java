package ristorante;

public class Cuoco implements Runnable {
    private Comande comande;

    /**
     * Costruttore per un {@code cuoco} generico a cui viene associato
     * una {@code comanda}
     * 
     * @param comande
     * 
     * @see {@link Comande#Comande(String, Boolean)}
     */
    public Cuoco(Comande comande) {
        this.comande = comande;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            final Ordinazione ordinazione = comande.consegnaOrdinazione();
            final int tempo = 5000 + new java.util.Random().nextInt(5000);
            System.out.println("Piatto pronto: " + ordinazione + "..Tempo atteso " + (long) tempo + "ms");
            try {
                Thread.sleep(tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
