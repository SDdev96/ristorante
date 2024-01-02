package ristorante;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("all")

public class Comande implements Serializable {
    private String filename;
    private Queue<Ordinazione> comande;
    private static final String path = "src/files/"; //To be changed

    /**
     * Una generica {@code comanda} a cui viene associata un file di nome
     * {@code filename}
     * e un valore booleano {@code leggiBackup} utilizzato per leggere da
     * {@code filename}. Se {@code filename} è {@code true} legge da
     * {@code filename}, altrimenti crea una lista vuota.
     * 
     * @param filename
     * @param leggiBackup
     * 
     * @see {@link ObjectInputStream#readObject()}
     * @see {@link BufferedInputStream}
     * @see {@link FileInputStream}
     * @see {@link LinkedList}
     * @see {@link Ordinazione}
     * @see {@link ClassNotFoundException}
     * 
     * @throws IOException
     */
    public Comande(String filename, Boolean leggiBackup) throws IOException {
        this.filename = filename;
        if (leggiBackup) {
            try (final ObjectInputStream ois = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(path + filename)))) {
                try {
                    comande = (LinkedList<Ordinazione>) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else
            comande = new LinkedList<>();
    }

    /**
     * Aggiunge un'ordinazione alla coda.
     * Una volta aggiunto risveglia il rispettivo thread e rilascia il mutex
     * 
     * @param ordinazione {@link Ordinazione}
     * 
     * @see {@link Queue#add(Object)}
     * @see {@link java.util.Collection#size()}
     * @see {@link Thread#notifyAll()}
     * 
     */
    public synchronized void aggiungiOrdinazione(Ordinazione ordinazione) {
        comande.add(ordinazione);
        System.out.println("Aggiungo ordinazione.. Piatti in coda: " + comande.size());
        notifyAll();
    }

    /**
     * Recupera ed elimina l'ordinazione in testa alla coda e rilascia il mutex.
     * Se la coda è vuota mette in attesa il thread.
     * 
     * @return la testa della coda di tipo {@code Ordinazione}
     * 
     * @see {@link java.util.Collection#isEmpty()}
     * @see {@link Thread#wait()}
     * @see {@link Queue#poll()}
     * @see {@link InterruptedException#printStackTrace()}
     */
    public synchronized Ordinazione consegnaOrdinazione() {
        while (comande.isEmpty())
            try {
                wait();
            } catch (InterruptedException e) {
                // e.printStackTrace();
                System.err.println("InterrutpedException attivata");
            }
        Ordinazione ordinazione = comande.poll();
        System.out.println("Consegna ordinazione.. Piatti in coda: " + comande.size());
        return ordinazione;
    }

    /**
     * Scrive l'ordinazione sotto forma di oggetto su un file di backup.
     * Se la coda è vuota mette in attesa il thread.
     * 
     * @throws IOException
     * 
     * @see {@link java.util.Collection#isEmpty()}
     * @see {@link Thread#wait()}
     * @see {@link InterruptedException#printStackTrace()}
     * @see {@link ObjectOutputStream#writeObject(Object)}
     * @see {@link BufferedOutputStream}
     * @see {@link FileOutputStream}
     */
    public synchronized void salvaOrdinazione() throws IOException {
        while (comande.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                // e.printStackTrace();
                System.err.println("InterrutpedException attivata");
            }
        }
        try (final ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(path + filename)))) {
            oos.writeObject(comande);
        }
    }
}
