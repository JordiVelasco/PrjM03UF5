package invasion_extraterrestre;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Classe Controladora FXML
 * @author sergi grau
 * @version 1.0 8/02/2014
 */
public class LoginController implements Initializable {

    /**
     * Inicialitza el controlador
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML private Text textMissatge;

    @FXML protected void gestorBotoAction(ActionEvent event) {
        textMissatge.setText("En procés...");
        new Juego().controlar_juego();

    }
}