package botecofx;

import botecofx.db.dal.CategoriaDAL;
import botecofx.db.dal.ProdutoDAL;
import botecofx.db.dal.UnidadeDAL;
import botecofx.db.entidades.Categoria;
import botecofx.db.entidades.Produto;
import botecofx.db.entidades.Unidade;
import botecofx.util.MaskFieldUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class TelaCadProdutoController implements Initializable {

    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txpreco;
    @FXML
    private JFXComboBox<Categoria> txcategoria;
    @FXML
    private JFXComboBox<Unidade> txunidade;
    @FXML
    private JFXTextField txdescricao;
    @FXML
    private JFXTextField txid;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()->{
            txnome.requestFocus();
        });
        txcategoria.setItems(FXCollections.observableArrayList(new CategoriaDAL().get("")));
        txunidade.setItems(FXCollections.observableArrayList(new UnidadeDAL().get("")));
        MaskFieldUtil.monetaryField(txpreco);
        if(TelaRelProdutoController.prod!=null)
        {
            Produto p=TelaRelProdutoController.prod;
            txid.setText(""+p.getId());
            txnome.setText(p.getNome());
            txpreco.setText(""+p.getPreco());
            txcategoria.setValue(p.getCategoria());
            txunidade.setValue(p.getUnidade());
            //txcategoria.setText(""+p.getCategoria().getId());
            //txunidade.setText(""+p.getUnidade().getId());
            txdescricao.setText(p.getDescricao());
            System.out.println(""+txcategoria.getValue().getNome());
        }
    }    

    @FXML
    private void evtConfirmar(ActionEvent event) {
        if (!txnome.getText().isEmpty() && !txpreco.getText().isEmpty()
                && txcategoria.getItems()!=null && txunidade.getItems()!=null)
        {
            Produto p;
            String v=txpreco.getText();
            v=v.replace(',', '.');
            p=new Produto(txnome.getText(),Double.parseDouble(v),txdescricao.getText(),
                    new CategoriaDAL().get(txcategoria.getValue().getId()), 
                    new UnidadeDAL().get(txunidade.getValue().getId()));
            if(txid.getText().isEmpty()){
               new ProdutoDAL().salvar(p);
            }
            else
            {  p.setId(Integer.parseInt(txid.getText()));
               new ProdutoDAL().alterar(p);
            }
            txid.getScene().getWindow().hide();
        }
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txid.getScene().getWindow().hide();
    }
    
}
