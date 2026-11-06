package main.com.manga.store.cart;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CartController {

    @FXML
    private VBox cartContainer;
    @FXML
    private Label totalLabel;
    @FXML
    private Button orderButton;
    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        aggiornaCarrello();
    }

    private void aggiornaCarrello() {
        cartContainer.getChildren().clear();

        if (Carrello.items.isEmpty()) {
            Label vuoto = new Label("Your cart is empty.");
            vuoto.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px; -fx-padding: 20;");
            cartContainer.getChildren().add(vuoto);
            totalLabel.setText("Total: € 0,00");
            return;
        }

        for (String[] item : Carrello.items) {
            HBox riga = creaRiga(item[0], item[1], item[2]);
            cartContainer.getChildren().add(riga);
        }

        String totaleStr = String.format("%.2f", Carrello.totale()).replace(".", ",");
        totalLabel.setText("Total: € " + totaleStr);
    }

    private HBox creaRiga(String titolo, String prezzo, String qty) {

        Label labelTitolo = new Label(titolo);
        labelTitolo.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 13px; -fx-font-weight: bold;");

        Label labelQty = new Label("Qty: " + qty);
        labelQty.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 11px;");

        VBox infoBox = new VBox(3, labelTitolo, labelQty);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        double lineTotal = Double.parseDouble(prezzo) * Integer.parseInt(qty);
        String lineTotalStr = "€ " + String.format("%.2f", lineTotal).replace(".", ",");
        Label labelPrezzo = new Label(lineTotalStr);
        labelPrezzo.setStyle("-fx-text-fill: #e94560; -fx-font-size: 14px; -fx-font-weight: bold;");

        Button removeBtn = new Button("Remove");
        removeBtn.setStyle(
                "-fx-background-color: #0f3460; -fx-text-fill: #aaaaaa;" +
                        "-fx-font-size: 11px; -fx-cursor: hand; -fx-background-radius: 5; -fx-padding: 5 10;"
        );
        removeBtn.setOnAction(e -> {
            Carrello.rimuovi(titolo);
            aggiornaCarrello();
        });

        HBox riga = new HBox(12, infoBox, labelPrezzo, removeBtn);
        riga.setAlignment(Pos.CENTER_LEFT);
        riga.setStyle(
                "-fx-background-color: #16213e;" +
                        "-fx-padding: 12 18;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: #0f3460;" +
                        "-fx-border-radius: 8;"
        );
        return riga;
    }

    @FXML
    private void handleOrder() {
        if (Carrello.items.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cart Is Empty", "Add some manga to your cart first.");
            return;
        }
        animateNode(orderButton);
        Carrello.items.clear();
        aggiornaCarrello();
        showAlert(Alert.AlertType.INFORMATION, "Order Placed!", "Your order has been placed successfully. Thank you!");
    }

    @FXML
    private void handleBack() {
        animateNode(backButton);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/com/manga/store/manga/manga.fxml"));
            Scene scene = new Scene(loader.load(), 600, 520);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setTitle("Manga Store");
            stage.setResizable(false);
            stage.setScene(scene);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Cannot go back to the store.");
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
