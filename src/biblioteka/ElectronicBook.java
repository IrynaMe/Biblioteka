package biblioteka;

public class ElectronicBook extends Book{
    private String format;

    public ElectronicBook(String titolo, String autore, String format) {
        super(titolo, autore);
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    /*@Override
    public String getFormattedString(){
        return super.getFormattedString()+","+format;
    }*/
    @Override
    public String toString() {
        return super.getTitolo() + "," + super.getAutore() + "," + format;
        //return super.toString()+","+durataMinuti;
    }


}
