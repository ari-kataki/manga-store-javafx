package main.com.manga.store.cart;

import java.util.ArrayList;

public class Carrello {

    public static final int MAX_QUANTITY = 10;

    public static ArrayList<String[]> items = new ArrayList<>();

    public static synchronized void aggiungi(String titolo, String prezzo, int qty) {
        for (String[] item : items) {
            if (item[0].equals(titolo)) {
                int current = Integer.parseInt(item[2]);
                item[2] = String.valueOf(current + qty);
                return;
            }
        }
        items.add(new String[]{titolo, prezzo, String.valueOf(qty)});
    }

    public static synchronized void rimuovi(String titolo) {
        items.removeIf(item -> item[0].equals(titolo));
    }

    public static double totale() {
        double tot = 0;
        for (String[] item : items) {
            double price = Double.parseDouble(item[1]);
            int qty = Integer.parseInt(item[2]);
            tot += price * qty;
        }
        return tot;
    }

    public static int quantitaTotale() {
        int tot = 0;
        for (String[] item : items) {
            tot += Integer.parseInt(item[2]);
        }
        return tot;
    }
}
