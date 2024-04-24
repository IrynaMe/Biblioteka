package biblioteka;

import java.io.*;
import java.util.*;

public class Biblioteka {

    private HashMap<Integer, Book> listaLibri = new HashMap<>();
    private final String nomefile = "src\\biblioteka\\libriDisponibili.txt";

    public void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Cosa vuoi fare: ");
        boolean flag = true;
        while (flag) {
            System.out.println("------------------------------------");
            System.out.println("1 -> Stampa libri disponibili ");
            System.out.println("2 -> Aggiungere un nuovo libro ");
            System.out.println("3 -> Cancellare un libro ");
            System.out.println("4 -> Cerca libro per nome/autore/parola chiave");
            System.out.println("5 -> Uscire");
            try {
                int scelta = sc.nextInt();
                switch (scelta) {
                    case 1:
                        stampaValori();
                        break;
                    case 2:
                        aggiungereLibro();
                        break;
                    case 3:
                        System.out.println("Inserisci ID del libro da cancellare: ");
                        int id = sc.nextInt();
                        cancellaLibro(id);
                        break;
                    case 4:
                        System.out.println("Inserisci il nome del libro oppure autore da cercare: ");
                        String nome = sc.next();
                        trovaLibroPerNome(nome);
                        break;
                    case 5:
                        System.out.println("Arrivederci!");
                        flag = false;
                        break;
                    default:
                        System.out.println("Scelta errata");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Errore: inserisci un numero");
                sc.next();
            }
        }
        sc.close();
    }

    //legge da file e popola HashMap
    public HashMap<Integer, Book> readFromFile() {
        String linea;
        try {
            BufferedReader breader = new BufferedReader(new FileReader(nomefile));
            while ((linea = breader.readLine()) != null) {
                String[] datiLibro = linea.split(",");
                if (datiLibro.length == 3) {
                    Book book = new Book(datiLibro[1].trim(), datiLibro[2].trim());
                    listaLibri.put(Integer.valueOf(datiLibro[0]), book);
                } else if (datiLibro.length == 4) {
                    //System.out.println("TEST DURATA "+datiLibro[3]);
                    //controllo se Audiobook o ElectronicBook
                    try {
                        listaLibri.put(Integer.valueOf(datiLibro[0]), new AudioBook(datiLibro[1].trim(), datiLibro[2].trim(), Integer.valueOf(datiLibro[3])));
                    } catch (NumberFormatException e) {
                        listaLibri.put(Integer.valueOf(datiLibro[0]), new ElectronicBook(datiLibro[1].trim(), datiLibro[2].trim(), datiLibro[3]));
                    }

                } else {
                    System.out.println("Inserimento in HashMap non è possibile");
                }
            }
            breader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return listaLibri;
    }

    //aggiungere in hashMap + in file
    public void aggiungereLibro() {
        listaLibri = readFromFile();
        //creo id autoincrement
        int maxId = 0;
        for (Map.Entry<Integer, Book> entry : listaLibri.entrySet()) {
            if (entry != null && entry.getKey() > maxId) {
                maxId = entry.getKey();
            }
        }
        boolean flag = true;

        while (flag) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Cosa vuoi aggiungere?");
            System.out.println("1 -> libro");
            System.out.println("2 -> audiolibro");
            System.out.println("3 -> libro elettronico");
            System.out.println("4 -> Torna al menu");
            try {
                int scelta = sc.nextInt();
                switch (scelta) {
                    case 1:
                        System.out.println("Inserisci il titolo");
                        String titolo = sc.next();
                        System.out.println("Inserisci il autore");
                        String autore = sc.next();
                        Book book = new Book(titolo, autore);
                        listaLibri.put(++maxId, book);
                        aggiornaFile();
                        System.out.println("Libro aggiunto con successo");
                        break;
                    case 2:
                        System.out.println("Inserisci il titolo");
                        String titolo1 = sc.next();
                        System.out.println("Inserisci il autore");
                        String autore1 = sc.next();
                        System.out.println("Inserisci la durata");
                        int durata = sc.nextInt();
                        listaLibri.put(++maxId, new AudioBook(titolo1, autore1, durata));
                        aggiornaFile();
                        System.out.println("Audiolibro aggiunto con successo");
                        break;
                    case 3:
                        System.out.println("Inserisci il titolo");
                        String titolo2= sc.next();
                        System.out.println("Inserisci il autore");
                        String autore2 = sc.next();
                        System.out.println("Inserisci il format");
                        String format=sc.next();
                        listaLibri.put(++maxId, new ElectronicBook(titolo2, autore2, format));
                        aggiornaFile();
                        System.out.println("Audiolibro aggiunto con successo");
                        break;
                    case 4:
                        flag = false;
                        break;
                    default:
                        System.out.println("Scelta errata");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Errore: inserisci un numero");
                sc.next();
            }
        }

    }

    public void cancellaLibro(Integer key) {
        Integer id = trovaLibroPerId(key);
        if (id == null) {
            System.out.println("Non hai cancellato il libro: la chiave non trovata");
        } else {
            listaLibri.remove(id);
            System.out.println("Hai cancellato il libro con id: " + id);
            aggiornaFile();
        }
    }

    public Integer trovaLibroPerId(int id) {
        listaLibri = readFromFile();
        Integer key = null;
        for (Map.Entry<Integer, Book> entry : listaLibri.entrySet()) {
            if (entry.getKey() == id) {
                key = id;
                System.out.println("Libro trovato -> ID: " + entry.getKey() + ", Libro: " + entry.getValue().toString());
            }
        }
        if (key == null) {
            System.out.println("Libro non trovato");
        }
        return key;
    }

    public void trovaLibroPerNome(String nome) {
        listaLibri = readFromFile();
        List<String> lista = null;

        for (Map.Entry<Integer, Book> entry : listaLibri.entrySet()) {
            String result = entry.getValue().toString().toUpperCase();
            if (result.contains(nome.toUpperCase())) {
                lista = new ArrayList<>();
                lista.add("ID: " + entry.getKey() + ", Libro: " + entry.getValue().toString());
            }
        }
        if (lista != null) {
            System.out.println("Trovato:");
            for (String libro : lista) System.out.println(libro);
        } else {
            System.out.println("Libro non trovato");
        }
    }

    public void aggiornaFile() {
        String linea;
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(nomefile));
            for (Map.Entry<Integer, Book> entry : listaLibri.entrySet()) {
                linea = entry.getKey() + "," + entry.getValue() + "\n";
                br.write(linea);
            }
            br.close();
            System.out.println("Il file è stato aggiornato");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void stampaValori() {
        readFromFile();
        for (Map.Entry<Integer, Book> entry : listaLibri.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
