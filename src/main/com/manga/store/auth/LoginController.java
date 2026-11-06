package main.com.manga.store.auth;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;
    @FXML
    private Button loginButton;

    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "1";

    @FXML
    private void handleLogin() {
        animateButton();

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Enter username and password.");
            showAlert(Alert.AlertType.WARNING, "Fields Missing", "Insert username and password.");
            return;
        }

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            apriMangaStore();
        } else {
            messageLabel.setStyle("-fx-text-fill: #ffdddd; -fx-font-size: 12px; -fx-font-weight: bold;");
            messageLabel.setText("Credentials are incorrect.");
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private void apriMangaStore() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/main/com/manga/store/manga/manga.fxml"));
            Scene scene = new Scene(loader.load(), 600, 520);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setTitle("Manga Store");
            stage.setResizable(false);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Impossible to open Manga Store.");
        }
    }

    private void animateButton() {
        ScaleTransition st = new ScaleTransition(Duration.millis(120), loginButton);
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
