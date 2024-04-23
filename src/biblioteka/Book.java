package biblioteka;

public class Book {
    //private int ID;
    private String titolo;
    private String autore;

    public Book(String titolo, String autore) {
        this.titolo = titolo;
        this.autore = autore;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    @Override
    public String toString() {
        return titolo+","+autore;
    }
}
