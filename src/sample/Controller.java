package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField txtCodBarra;
    @FXML
    private Label lblValido;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtCodBarra.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (txtCodBarra.getText().isEmpty()) {
                    lblValido.setText("");
                } else {
                    verificaTxtCodBarras();
                }
            }
        });
    }

    public static boolean validaCodBarras(String codigoBarras) {
        String ValidChars = "0123456789";
        char digito;
        for (int i = 0; i < codigoBarras.length(); i++) {
            digito = codigoBarras.charAt(i);
            if (ValidChars.indexOf(digito) == -1) {
                return false;
            }
        }
        // Add five 0 if the code has only 8 digits
        if (codigoBarras.length() == 8) {
            codigoBarras = "00000" + codigoBarras;
        }

        if (codigoBarras.length() == 12) {
            codigoBarras = "0" + codigoBarras;
        }
        // Check for 13 digits otherwise
        else if (codigoBarras.length() != 13) {
            return false;
        }
        // Get the check number
        int originalCheck = Integer.parseInt(codigoBarras
                .substring(codigoBarras.length() - 1));
        codigoBarras = codigoBarras.substring(0, codigoBarras.length() - 1);
        // Add even numbers together
        int even = Integer.parseInt(codigoBarras.charAt(1) + "");
        even += Integer.parseInt(codigoBarras.charAt(3) + "");
        even += Integer.parseInt(codigoBarras.charAt(5) + "");
        even += Integer.parseInt(codigoBarras.charAt(7) + "");
        even += Integer.parseInt(codigoBarras.charAt(9) + "");
        even += Integer.parseInt(codigoBarras.charAt(11) + "");

        // Multiply this result by 3
        even *= 3;

        // Add odd numbers together
        int odd = Integer.parseInt(codigoBarras.charAt(0) + "");
        odd += Integer.parseInt(codigoBarras.charAt(2) + "");
        odd += Integer.parseInt(codigoBarras.charAt(4) + "");
        odd += Integer.parseInt(codigoBarras.charAt(6) + "");
        odd += Integer.parseInt(codigoBarras.charAt(8) + "");
        odd += Integer.parseInt(codigoBarras.charAt(10) + "");

        // Add two totals together
        int total = even + odd;

        // Calculate the checksum
        // Divide total by 10 and store the remainder

        int checksum = total % 10;

        // If result is not 0 then take away 10

        if (checksum != 0) {
            checksum = 10 - checksum;
        }

        // Return the result
        if (checksum != originalCheck) {
            return false;
        }
        return true;
    }

    private boolean verificaTxtCodBarras() {
        if (!validaCodBarras(txtCodBarra.getText())) {
            lblValido.setText("INVÁLIDO");
            lblValido.getStyleClass().set(0, "label-error");
            return false;
        } else {
            lblValido.setText("VÁLIDO");
            lblValido.getStyleClass().set(0, "label-success");
            return true;
        }
    }


}
