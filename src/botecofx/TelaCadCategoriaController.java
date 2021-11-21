
package botecofx;

import botecofx.db.dal.CategoriaDAL;
import botecofx.db.entidades.Categoria;
import botecofx.db.entidades.Tipopgto;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class TelaCadCategoriaController implements Initializable {

    @FXML
    private JFXTextField txid;
    @FXML
    private JFXTextField txnome;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()->{
            txnome.requestFocus();
        });
        if(TelaRelCategoriaController.cat!=null)
        {
            Categoria c=TelaRelCategoriaController.cat;
            txid.setText(""+c.getId());
            txnome.setText(c.getNome());
           
        }
    }    

    @FXML
    private void evtConfirmar(ActionEvent event) {
        Categoria c;
            c=new Categoria(txnome.getText());
            if(txid.getText().isEmpty())
               new CategoriaDAL().salvar(c);
            else
            {  c.setId(Integer.parseInt(txid.getText()));
               new CategoriaDAL().alterar(c);
            }
            txid.getScene().getWindow().hide();
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txid.getScene().getWindow().hide();
    }
    
}
