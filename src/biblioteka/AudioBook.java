package biblioteka;

public class AudioBook extends Book{
    private int durataMinuti;

    public AudioBook(String titolo, String autore, int durataMinuti) {
        super(titolo, autore);
        this.durataMinuti = durataMinuti;
    }
    @Override
    public String getFormattedString(){
        return super.getFormattedString()+","+durataMinuti;
    }


   /* @Override
    public String toString() {
       return super.getTitolo()+","+super.getAutore()+","+durataMinuti;
        //return super.toString()+","+durataMinuti;
    }*/
}
