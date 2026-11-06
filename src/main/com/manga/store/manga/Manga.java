package main.com.manga.store.manga;

public class Manga extends StoreItem {

    private String genere;
    private String immagine;

    public Manga() {
        super();
    }

    public String getGenere() {
        return genere;
    }

    public String getImmagine() {
        return immagine;
    }

    @Override
    public String getDisplayInfo() {
        return getTitolo() + " [" + genere + "] - €" + getPrezzo();
    }
}
