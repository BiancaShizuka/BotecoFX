
package botecofx;


import botecofx.db.dal.TipopgtoDAL;
import botecofx.db.dal.UnidadeDAL;
import botecofx.db.entidades.Tipopgto;
import botecofx.db.entidades.Unidade;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class TelaCadTpgoController implements Initializable {

    @FXML
    private JFXTextField txid;
    @FXML
    private JFXTextField txnome;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()->{
            txnome.requestFocus();
        });
        if(TelaRelTpgoController.tpg!=null)
        {
            Tipopgto t=TelaRelTpgoController.tpg;
            txid.setText(""+t.getId());
            txnome.setText(t.getNome());
           
        }
    }

    @FXML
    private void evtConfirmar(ActionEvent event) {
        if (!txnome.getText().isEmpty())
        {
            
            Tipopgto t;
            t=new Tipopgto(txnome.getText());
            if(txid.getText().isEmpty())
               new TipopgtoDAL().salvar(t);
            else
            {  t.setId(Integer.parseInt(txid.getText()));
               new TipopgtoDAL().alterar(t);
            }
            txid.getScene().getWindow().hide();
        }
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txid.getScene().getWindow().hide();
    }
    
}
