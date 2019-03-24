package invasion_extraterrestre;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe que implementa un login mitjançant FXML.
 * @author sergi grau
 * @version 1.0 8/02/2014
 */

public class LoginFXML extends Application {

    public static void main(String[] args) {
        Application.launch(LoginFXML.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent arrel = FXMLLoader.load(getClass().getResource("login.fxml"));

        stage.setTitle("Login");
        stage.setScene(new Scene(arrel, 300, 275));
        stage.show();
    }
}