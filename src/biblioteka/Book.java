package biblioteka;


public class Book extends Item{
    private String titolo;
    private String autore;

    public Book(String titolo, String autore) {
        super(titolo, autore);
    }

    @Override
    public String getFormattedString() {
        return "Book: " +super.toString();
    }

}
