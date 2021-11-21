package botecofx;

import botecofx.db.dal.CategoriaDAL;
import botecofx.db.dal.ProdutoDAL;
import botecofx.db.dal.UnidadeDAL;
import botecofx.db.entidades.Produto;
import botecofx.db.entidades.Unidade;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class TelaCadUnidadeController implements Initializable {

    @FXML
    private JFXTextField txid;
    @FXML
    private JFXTextField txnome;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()->{
            txnome.requestFocus();
        });
        if(TelaRelUnidadeController.uni!=null)
        {
            Unidade u=TelaRelUnidadeController.uni;
            txid.setText(""+u.getId());
            txnome.setText(u.getNome());
           
        }
    }    

    @FXML
    private void evtConfirmar(ActionEvent event) {
        if (!txnome.getText().isEmpty())
        {
            
            Unidade u;
            u=new Unidade(txnome.getText());
            if(txid.getText().isEmpty())
               new UnidadeDAL().salvar(u);
            else
            {  u.setId(Integer.parseInt(txid.getText()));
               new UnidadeDAL().alterar(u);
            }
            txid.getScene().getWindow().hide();
        }
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txid.getScene().getWindow().hide();
    }
    
}
