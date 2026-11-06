package main.com.manga.store.manga;

import main.com.manga.store.cart.Carrello;
import main.com.manga.store.data.CatalogLoader;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MangaController {

    @FXML
    private VBox mangaContainer;
    @FXML
    private Button logoutButton;
    @FXML
    private Button cartButton;
    @FXML
    private Button outOfStockButton;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> genreFilter;
    @FXML
    private ComboBox<String> sortFilter;

    private boolean showingOutOfStock = false;
    private ArrayList<Manga> catalogo = new ArrayList<>();
    private CatalogLoader loader;

    @FXML
    public void initialize() {

        genreFilter.getItems().addAll(
                "All Genres",
                "Action / Adventure",
                "Action / Fantasy",
                "Action / Comedy",
                "Action / Horror",
                "Action / Story",
                "Action / Superhero",
                "Thriller / Mystery",
                "Slice of Life / Drama"
        );
        genreFilter.setValue("All Genres");

        sortFilter.getItems().addAll(
                "Default",
                "Name A → Z",
                "Name Z → A",
                "Price Low → High",
                "Price High → Low"
        );
        sortFilter.setValue("Default");

        searchField.textProperty().addListener((obs, oldVal, newVal) -> aggiornaLista());
        genreFilter.setOnAction(e -> aggiornaLista());
        sortFilter.setOnAction(e -> aggiornaLista());

        aggiornaCartButton();

        loader = new CatalogLoader(() -> {
            catalogo = loader.getResult();
            aggiornaLista();
        });
        loader.start();
    }

    private void aggiornaLista() {
        mangaContainer.getChildren().clear();

        String search = searchField.getText().toLowerCase().trim();
        String genre = genreFilter.getValue();
        String sort = sortFilter.getValue();

        List<Manga> lista = new ArrayList<>();
        for (Manga manga : catalogo) {
            boolean matchSearch = manga.getTitolo().toLowerCase().contains(search);
            boolean matchGenre = "All Genres".equals(genre) || manga.getGenere().equals(genre);
            boolean matchStock = showingOutOfStock ? !manga.isInStock() : manga.isInStock();

            if (matchSearch && matchGenre && matchStock) {
                lista.add(manga);
            }
        }

        if ("Name A → Z".equals(sort)) {
            lista.sort((a, b) -> a.getTitolo().compareTo(b.getTitolo()));
        } else if ("Name Z → A".equals(sort)) {
            lista.sort((a, b) -> b.getTitolo().compareTo(a.getTitolo()));
        } else if ("Price Low → High".equals(sort)) {
            lista.sort((a, b) -> Double.compare(Double.parseDouble(a.getPrezzo()), Double.parseDouble(b.getPrezzo())));
        } else if ("Price High → Low".equals(sort)) {
            lista.sort((a, b) -> Double.compare(Double.parseDouble(b.getPrezzo()), Double.parseDouble(a.getPrezzo())));
        }

        if (lista.isEmpty()) {
            Label nessuno = new Label("No manga found.");
            nessuno.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px; -fx-padding: 20;");
            mangaContainer.getChildren().add(nessuno);
        } else {
            for (Manga manga : lista) {
                HBox carta = creaCarta(manga);
                mangaContainer.getChildren().add(carta);
            }
        }
    }

    private HBox creaCarta(Manga manga) {

        ImageView immagine = new ImageView();
        immagine.setFitWidth(60);
        immagine.setFitHeight(85);
        immagine.setPreserveRatio(true);

        try {
            java.io.InputStream stream = getClass().getResourceAsStream("/main/com/manga/store/manga/photos/" + manga.getImmagine());
            if (stream != null) {
                immagine.setImage(new Image(stream));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Image not found: " + manga.getImmagine());
        }

        Label labelTitolo = new Label(manga.getTitolo());
        labelTitolo.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label labelGenere = new Label(manga.getGenere());
        labelGenere.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 11px;");

        Label labelInfo = new Label(manga.getDisplayInfo());
        labelInfo.setStyle("-fx-text-fill: #555577; -fx-font-size: 10px;");

        VBox infoBox = new VBox(3, labelTitolo, labelGenere, labelInfo);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        String prezzoFormattato = "€ " + manga.getPrezzo().replace(".", ",");
        Label labelPrezzo = new Label(prezzoFormattato);
        labelPrezzo.setStyle("-fx-text-fill: #e94560; -fx-font-size: 15px; -fx-font-weight: bold;");

        VBox rightBox;

        if (!manga.isInStock()) {
            Label outLabel = new Label("Out of Stock");
            outLabel.setStyle("-fx-text-fill: #555555; -fx-font-size: 11px; -fx-font-weight: bold;");
            rightBox = new VBox(6, labelPrezzo, outLabel);
            rightBox.setAlignment(Pos.CENTER_RIGHT);
        } else {
            Label qtyLabel = new Label("1");
            qtyLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 13px; -fx-min-width: 25; -fx-alignment: center;");

            Button minusBtn = new Button("−");
            minusBtn.setStyle("-fx-background-color: #0f3460; -fx-text-fill: white; -fx-font-size: 13px; -fx-cursor: hand; -fx-background-radius: 4; -fx-min-width: 26; -fx-min-height: 26;");

            Button plusBtn = new Button("+");
            plusBtn.setStyle("-fx-background-color: #0f3460; -fx-text-fill: white; -fx-font-size: 13px; -fx-cursor: hand; -fx-background-radius: 4; -fx-min-width: 26; -fx-min-height: 26;");

            minusBtn.setOnAction(e -> {
                int qty = Integer.parseInt(qtyLabel.getText());
                if (qty > 1) qtyLabel.setText(String.valueOf(qty - 1));
            });

            plusBtn.setOnAction(e -> {
                int qty = Integer.parseInt(qtyLabel.getText());
                if (qty < Carrello.MAX_QUANTITY) qtyLabel.setText(String.valueOf(qty + 1));
            });

            HBox qtyBox = new HBox(6, minusBtn, qtyLabel, plusBtn);
            qtyBox.setAlignment(Pos.CENTER);

            Button addBtn = new Button("Add to Cart");
            addBtn.setStyle("-fx-background-color: #e94560; -fx-text-fill: white; -fx-font-size: 11px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 5; -fx-padding: 5 10;");
            addBtn.setOnAction(e -> {
                int qty = Integer.parseInt(qtyLabel.getText());
                Carrello.aggiungi(manga.getTitolo(), manga.getPrezzo(), qty);
                aggiornaCartButton();
                animateNode(addBtn);
            });

            rightBox = new VBox(6, labelPrezzo, qtyBox, addBtn);
            rightBox.setAlignment(Pos.CENTER_RIGHT);
        }

        HBox carta = new HBox(12, immagine, infoBox, rightBox);
        carta.setAlignment(Pos.CENTER_LEFT);
        carta.setStyle(
                "-fx-background-color: #16213e;" +
                        "-fx-padding: 10 18;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: #0f3460;" +
                        "-fx-border-radius: 8;"
        );
        return carta;
    }

    @FXML
    private void handleOutOfStock() {
        showingOutOfStock = !showingOutOfStock;
        if (showingOutOfStock) {
            outOfStockButton.setText("Show In Stock");
            outOfStockButton.setStyle("-fx-background-color: #e94560; -fx-text-fill: white; -fx-font-size: 11px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 5; -fx-padding: 7 12;");
        } else {
            outOfStockButton.setText("Out of Stock");
            outOfStockButton.setStyle("-fx-background-color: #0f3460; -fx-text-fill: #aaaaaa; -fx-font-size: 11px; -fx-cursor: hand; -fx-background-radius: 5; -fx-padding: 7 12;");
        }
        aggiornaLista();
    }

    @FXML
    private void handleCart() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/com/manga/store/cart/cart.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 520);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setTitle("Cart - Manga Store");
            stage.setResizable(false);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Cannot open cart.");
        }
    }

    private void aggiornaCartButton() {
        cartButton.setText("Cart (" + Carrello.quantitaTotale() + ")");
    }

    @FXML
    private void handleLogout() {
        animateNode(logoutButton);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/com/manga/store/auth/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 450);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setTitle("Login - Manga Store");
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Can't return to login page.");
        }
    }

    private void animateNode(Button btn) {
        ScaleTransition st = new ScaleTransition(Duration.millis(120), btn);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(0.95);
        st.setToY(0.95);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
