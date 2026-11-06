package main.com.manga.store.data;

import main.com.manga.store.manga.Manga;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileManager {

    static {
        System.out.println("FileManager initialized.");
    }

    public static ArrayList<Manga> caricaManga() {

        ArrayList<Manga> lista = new ArrayList<>();

        try {
            InputStream stream = FileManager.class.getResourceAsStream("/main/com/manga/store/data/manga.json");

            if (stream == null) {
                System.out.println("manga.json not found!");
                return lista;
            }

            InputStreamReader reader = new InputStreamReader(stream, java.nio.charset.StandardCharsets.UTF_8);
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray array = root.getAsJsonArray("catalogo");

            Gson gson = new Gson();
            for (int i = 0; i < array.size(); i++) {
                Manga manga = gson.fromJson(array.get(i), Manga.class);
                lista.add(manga);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading manga.json: " + e.getMessage());
        }

        return lista;
    }
}
