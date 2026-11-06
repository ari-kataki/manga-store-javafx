package com.manga.store;

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


public class MangaController {

    @FXML
    private VBox mangaContainer;

    @FXML
    private Button logoutButton;

    // { title, genere, price, image file name }
    private String[][] catalogo = {
            { "Naruto Vol. 1",              "Action / Adventure",      "€ 5,90", "naruto.png"         },
            { "Chainsaw Man Vol. 1",        "Action / Fantasy",        "€ 5,90", "chainsawman.png"    },
            { "Dragon Ball Vol. 1",         "Action / Comedy",         "€ 6,50", "dragonball.png"     },
            { "Attack on Titan Vol. 1",     "Action / Horror",         "€ 7,00", "attackontitan.png"  },
            { "Demon Slayer Vol. 1",        "Action / Story",          "€ 5,90", "demonslayer.png"    },
            { "My Hero Academia Vol. 1",    "Action / Superhero",      "€ 5,90", "myhero.png"         },
            { "Death Note Vol. 1",          "Thriller / Mystery",      "€ 6,50", "deathnote.png"      },
            { "Your Lie In April Vol. 1",   "Slice of Life / Drama",   "€ 6,00", "yourlieinapril.png" }
    };

    @FXML
    public void initialize() {
        for (String[] manga : catalogo) {
            HBox carta = creaCarta(manga[0], manga[1], manga[2], manga[3]);
            mangaContainer.getChildren().add(carta);
        }
    }

    private HBox creaCarta(String titolo, String genere, String prezzo, String imageName) {

        // Image
        ImageView immagine = new ImageView();
        immagine.setFitWidth(60);
        immagine.setFitHeight(85);
        immagine.setPreserveRatio(true);

        try {
            Image img = new Image(getClass().getResourceAsStream("/com/manga/store/" + imageName));
            immagine.setImage(img);
        } catch (Exception e) {
            System.out.println("Image not found: " + imageName);
        }

        // Left: title + genera
        Label labelTitolo = new Label(titolo);
        labelTitolo.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label labelGenere = new Label(genere);
        labelGenere.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 11px;");

        VBox infoBox = new VBox(3, labelTitolo, labelGenere);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        // right: price
        Label labelPrezzo = new Label(prezzo);
        labelPrezzo.setStyle("-fx-text-fill: #e94560; -fx-font-size: 15px; -fx-font-weight: bold;");

        // space seperate
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Card
        HBox carta = new HBox(12, immagine, infoBox, spacer, labelPrezzo);
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
    private void handleLogout() {
        animateButton();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/manga/store/login.fxml"));
            Scene scene = new Scene(loader.load(), 500, 450);

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setTitle("Login - Manga Store");
            stage.setScene(scene);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Can't return to login page.");
        }
    }

    private void animateButton() {
        ScaleTransition st = new ScaleTransition(Duration.millis(120), logoutButton);
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