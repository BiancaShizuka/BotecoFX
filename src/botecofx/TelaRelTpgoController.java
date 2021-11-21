package botecofx;

import botecofx.db.dal.TipopgtoDAL;
import botecofx.db.entidades.Tipopgto;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaRelTpgoController implements Initializable {

    @FXML
    private TableView<Tipopgto> tabela;
    @FXML
    private TableColumn<Tipopgto, Integer> colid;
    @FXML
    private TableColumn<Tipopgto, String> colnome;
    @FXML
    private TextField txbusca;
    public static Tipopgto tpg;
    @FXML
    private Button btnPesq;
    @FXML
    private Button btnCad;
    @FXML
    private Button btnDel;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       colid.setCellValueFactory(new PropertyValueFactory<>("id"));
       colnome.setCellValueFactory(new PropertyValueFactory<>("nome"));

       carregarTabela("");
    } 
    
    void carregarTabela(String filtro)
    {
        List <Tipopgto> res = new TipopgtoDAL().get(filtro);
        tabela.setItems(FXCollections.observableArrayList(res));
    }
    
    @FXML
    private void evtCadastrar(ActionEvent event) {
        Stage stage = new Stage();
        tpg=tabela.getSelectionModel().getSelectedItem();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("TelaCadTpgo.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
        carregarTabela("");
    }

   

    @FXML
    private void evtBuscar(ActionEvent event) {
        String chave=txbusca.getText();
        carregarTabela("upper(tpg_nome) like '"+chave.toUpperCase()+"%'");
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        int id=tabela.getSelectionModel().getSelectedItem().getId();
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja apagar o tipo de pagamento?");
        if(alert.showAndWait().get()==ButtonType.OK)
        {    new TipopgtoDAL().apagar(id);
             carregarTabela(""); 
        }
    }

    @FXML
    private void evtBuscarDigitacao(KeyEvent event) {
        String chave=txbusca.getText();
        carregarTabela("upper(tpg_nome) like '"+chave.toUpperCase()+"%'");
    }
    
}
