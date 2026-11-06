package main.com.manga.store.data;

import main.com.manga.store.manga.Manga;
import javafx.application.Platform;

import java.util.ArrayList;

public class CatalogLoader extends Thread {

    private ArrayList<Manga> result;
    private Runnable onFinished;

    public CatalogLoader(Runnable onFinished) {
        this.result = new ArrayList<>();
        this.onFinished = onFinished;
    }

    @Override
    public void run() {
        result = FileManager.caricaManga();
        Platform.runLater(onFinished);
    }

    public ArrayList<Manga> getResult() {
        return result;
    }
}
