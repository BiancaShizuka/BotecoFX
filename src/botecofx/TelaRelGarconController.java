package botecofx;

import botecofx.db.dal.GarconDAL;
import botecofx.db.entidades.Garcon;
import java.io.IOException;
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

public class TelaRelGarconController implements Initializable {

    @FXML
    private TableView<Garcon> tabela;
    @FXML
    private TableColumn<Garcon, Integer> colid;
    @FXML
    private TableColumn<Garcon, String> colnome;
    @FXML
    private TableColumn<Garcon, String> colcpf;
    @FXML
    private TableColumn<Garcon, String> colcidade;
    @FXML
    private TableColumn<Garcon, String> colfone;
    @FXML
    private TextField txbusca;
    public static Garcon garcon=null;
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
       colcpf.setCellValueFactory(new PropertyValueFactory<>("Cpf"));
       colcidade.setCellValueFactory(new PropertyValueFactory<>("Cidade"));
       colfone.setCellValueFactory(new PropertyValueFactory<>("Fone"));
       carregarTabela("");
    }    
    void carregarTabela(String filtro)
    {
        List <Garcon> res = new GarconDAL().get(filtro);
        tabela.setItems(FXCollections.observableArrayList(res));
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        garcon=tabela.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("TelaCadGarcon.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Garcon");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            carregarTabela("");
        } catch (Exception ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
        garcon=null;
    }

    @FXML
    private void evtBuscarDigitacao(KeyEvent event) {
        String chave=txbusca.getText();
        carregarTabela("upper(gar_nome) like '%"+chave.toUpperCase());
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
        String chave=txbusca.getText();
        carregarTabela("upper(gar_nome) like '%"+chave.toUpperCase());
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        int id=tabela.getSelectionModel().getSelectedItem().getId();
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja apagar o Garçon?");
        if(alert.showAndWait().get()==ButtonType.OK)
        {    new GarconDAL().apagar(id);
             carregarTabela(""); 
        }
    }
}
