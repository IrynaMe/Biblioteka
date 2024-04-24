package biblioteka;

abstract class Item {
    private String titolo;
    private String autore;

    public Item(String titolo, String autore) {
        this.titolo = titolo;
        this.autore = autore;
    }

   // public abstract String getFormattedString ();

    @Override
    public String  toString(){
        return titolo+","+autore;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }
}
