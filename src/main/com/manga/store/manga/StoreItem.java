package main.com.manga.store.manga;

public abstract class StoreItem {

    private String titolo;
    private String prezzo;
    private boolean inStock;

    public StoreItem() {
    }

    public String getTitolo() {
        return titolo;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public boolean isInStock() {
        return inStock;
    }

    public abstract String getDisplayInfo();

    @Override
    public String toString() {
        return titolo + " - €" + prezzo;
    }
}
