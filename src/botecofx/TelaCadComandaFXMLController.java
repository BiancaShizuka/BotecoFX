
package botecofx;

import botecofx.db.dal.ComandaDAL;
import botecofx.db.dal.GarconDAL;
import botecofx.db.entidades.Comanda;
import botecofx.db.entidades.Garcon;
import botecofx.db.util.Banco;
import static botecofx.db.util.Banco.getCon;
import botecofx.util.MaskFieldUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TelaCadComandaFXMLController implements Initializable {

    @FXML
    private JFXComboBox<Garcon> txgarcon;
    @FXML
    private JFXTextField txnum;
    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txdesc;
    @FXML
    private JFXDatePicker txdt;
    static public Comanda c;
    
    @FXML
    private Button btnPedidoPag;
    @FXML
    private Button btnFinalizar;
    @FXML
    private Button btnConf;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        c=ComandaSingleton.Com;
        
        Platform.runLater(()->{
            txgarcon.requestFocus();
        });
        txdt.setDisable(true);
        txgarcon.setItems(FXCollections.observableArrayList(new GarconDAL().get("")));
        if(c!=null)
        {
         
            
            txnum.setText(""+c.getNumero());
            txnome.setText(c.getNome());
            txdesc.setText(c.getDesc());
            txdt.setValue(c.getData());
            txgarcon.setValue(c.getGarcon());
            btnFinalizar.setDisable(false);
            btnPedidoPag.setDisable(false);
            if(c.getStatus()=='F')
            {
                btnPedidoPag.setDisable(false);
                btnFinalizar.setDisable(true);
                btnConf.setDisable(true);
                txdt.setDisable(true);
                txnum.setEditable(false);
                txnome.setEditable(false);
                txdesc.setEditable(false);
                txgarcon.setDisable(true);
            }
        }
        else
        {
            btnFinalizar.setDisable(true);
            btnPedidoPag.setDisable(true);
        }
        
        
        MaskFieldUtil.numericField(txnum);
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {

        if (!txnome.getText().isEmpty() && !txdesc.getText().isEmpty() && !txnum.getText().isEmpty()
                && txgarcon.getItems()!=null)
        {
            
            
            if(c==null){
                LocalDate d=LocalDate.now();
                Comanda comanda=new Comanda(txgarcon.getValue(),Integer.parseInt(txnum.getText()),txnome.getText(),d,
                                            txdesc.getText(),0,'A');
                new ComandaDAL().salvar(comanda);
                btnFinalizar.setDisable(false);
                btnPedidoPag.setDisable(false);
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Comanda cadastrada");

                alert.showAndWait();
          
                int pk=Banco.getCon().getMaxPK("comanda", "com_id");
                c=new ComandaDAL().get(pk);
                ComandaSingleton.Com=c;
            }
            else{
                c.setData(txdt.getValue());
                c.setDesc(txdesc.getText());
                c.setNome(txnome.getText());
                c.setNumero(Integer.parseInt(txnum.getText()));
                c.setValor(c.getValorComanda());
                c.setGarcon(new GarconDAL().get(txgarcon.getValue().getId()));
                new ComandaDAL().alterar(c);
                ComandaSingleton.Com=c;
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Comanda Alterada");

                alert.showAndWait();
                txnome.getScene().getWindow().hide();
            }
       
        }
        
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        txnome.getScene().getWindow().hide();
    }

    @FXML
    private void evtPagPed(ActionEvent event) throws IOException {
        Stage stage=new Stage();
            Scene scene=new Scene(FXMLLoader.load(getClass().getResource("TelaGerComandaFXML.fxml")));
            stage.setScene(scene);
            stage.setTitle("Comandas");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setHeight(700);
            stage.setWidth(1200);
            stage.showAndWait();
    }

    @FXML
    private void evtFinalizar(ActionEvent event) {
        if(c.getValorComanda()==c.getValorPagamentos())
        {
            c.setStatus('F');
            c.setValor(c.getValorComanda());
            new ComandaDAL().alterar(c);
            ComandaSingleton.Com=c;
            txnome.getScene().getWindow().hide();
        }
        else
        {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Não é possível finalizar a comanda. Pagamento não realizado");

            alert.showAndWait();
        }
    }
  
    

    
}
