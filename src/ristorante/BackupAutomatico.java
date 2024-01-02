package ristorante;

import java.io.IOException;

public class BackupAutomatico implements Runnable {
    private Comande comande;

    /**
     * Costruttore utilizzato per effettuare il backup del file
     * 
     * @param comande
     * 
     * @see {@link Comande#Comande(String, Boolean)}
     */
    public BackupAutomatico(Comande comande) {
        this.comande = comande;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                comande.salvaOrdinazione();
                System.out.println("BACKUP EFFETTUATO");
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
