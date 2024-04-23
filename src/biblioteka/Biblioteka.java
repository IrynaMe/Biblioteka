package biblioteka;

import java.io.*;
import java.util.*;

public class Biblioteka {

    private HashMap<Integer, Book> listaLibri = new HashMap<>();
    private String nomefile = "E:\\Java JOB\\Biblio\\src\\biblioteka\\libriDisponibili.txt";
    //  private String nomefile = "D:\\Java JOB\\Biblio\\src\\biblioteka\\libriDisponibili.txt";

    public void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Cosa vuoi fare: ");
        boolean flag = true;
        while (flag) {
            System.out.println("------------------------------------");
            System.out.println("S -> Stampa libri disponibili ");
            System.out.println("A -> Aggiungere un nuovo libro ");
            System.out.println("C -> Cancellare un libro ");
            System.out.println("N -> Cerca libro per nome/autore/parola chiave");
            System.out.println("Altro -> Uscire");
            String scelta = sc.nextLine().toUpperCase();
            switch (scelta) {
                case "S":
                    stampaValori();
                    break;
                case "A":
                    aggiungereLibro();
                    break;
                case "C":
                    System.out.println(" Inserisci ID del libro da cancellare: ");
                    int id = Integer.parseInt(sc.nextLine());
                    cancellaLibro(id);
                    break;
                case "N":
                    System.out.println(" Inserisci il nome del libro opperue autore da cercare: ");
                    String nome = sc.nextLine();
                    trovaLibroPerNome(nome);
                    break;
                default:
                    System.out.println("Arrivederci!");
                    flag = false;
                    break;
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
                    //  AudioBook audioBook = new AudioBook(datiLibro[1].trim(), datiLibro[2].trim(), Integer.valueOf(datiLibro[3]));
                    listaLibri.put(Integer.valueOf(datiLibro[0]), new AudioBook(datiLibro[1].trim(), datiLibro[2].trim(), Integer.parseInt(datiLibro[3])));
                } else {
                    System.out.println("inserimento in HashMap non è possibile");
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
            System.out.println("Cosa voui aggiungere?");
            System.out.println("L -> libro");
            System.out.println("A -> audiolibro");
            System.out.println("Altro -> tornare a menu");
            String schelta = sc.nextLine().toUpperCase();
            switch (schelta) {
                case "L":
                    System.out.println("Inserisci il titolo");
                    String titolo = sc.nextLine();
                    System.out.println("Inserisci il autore");
                    String autore = sc.nextLine();
                    //aggiungo libro in HashMap
                    Book book = new Book(titolo, autore);
                    listaLibri.put(++maxId, book);
                    //rescrivo file
                    aggiornaFile();
                    System.out.println("Libro aggiunto con successo");
                    break;
                case "A":
                    System.out.println("Inserisci il titolo");
                    String titolo1 = sc.nextLine();
                    System.out.println("Inserisci il autore");
                    String autore1 = sc.nextLine();
                    //aggiungo audiolibro in HashMap
                    System.out.println("Inserisci la durata");
                    int durata = Integer.parseInt(sc.nextLine());
                    listaLibri.put(++maxId, new AudioBook(titolo1, autore1, durata));
                    //rescrivo file
                    aggiornaFile();
                    System.out.println("audiolibro aggiunto con successo");
                    break;
                default:
                    flag = false;
                    break;
            }
        }

    }


    public void cancellaLibro(Integer key) {
        Integer id = trovaLibroPerId(key);
        if (id == null) {
            System.out.println("Non hai cancellato il libro: la chiave non trovata");
        } else {
            listaLibri.remove(id);
            System.out.println("hai cancellato il libro con id: " + id);
            //rescrivo il file
            aggiornaFile();
        }
    }

    public Integer trovaLibroPerId(int id) {
        listaLibri = readFromFile();
        Integer key = null;
        for (Map.Entry<Integer, Book> entry : listaLibri.entrySet()) {
            if (entry.getKey() == id) {
                key = id;
                System.out.println("libro trovato -> ID: " + entry.getKey() + ", Libro: " + entry.getValue().toString());
            }
        }
        if (key == null) {
            System.out.println("Libro non è trovato");
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
            System.out.println("Libro non è trovato");
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
            System.out.println("il file è aggiornato");
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

}//



