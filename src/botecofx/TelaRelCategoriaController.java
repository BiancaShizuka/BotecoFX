package botecofx;

import botecofx.db.dal.CategoriaDAL;
import botecofx.db.dal.UnidadeDAL;
import botecofx.db.entidades.Categoria;
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

public class TelaRelCategoriaController implements Initializable {

    @FXML
    private TableView<Categoria> tabela;
    @FXML
    private TableColumn<Categoria, Integer> colid;
    @FXML
    private TableColumn<Categoria, String> colnome;
    @FXML
    private TextField txbusca;
    public static Categoria cat;
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
        List <Categoria> res = new CategoriaDAL().get(filtro);
        tabela.setItems(FXCollections.observableArrayList(res));
    }

    @FXML
    private void evtCadastrar(ActionEvent event) {
        Stage stage = new Stage();
        cat=tabela.getSelectionModel().getSelectedItem();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("TelaCadCategoria.fxml"));
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
    private void evtBuscarDigitacao(KeyEvent event) {
        String chave=txbusca.getText();
        carregarTabela("upper(cat_nome) like '"+chave.toUpperCase()+"%'"); 
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        int id=tabela.getSelectionModel().getSelectedItem().getId();
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja apagar a categoria?");
        if(alert.showAndWait().get()==ButtonType.OK)
        {    new CategoriaDAL().apagar(id);
             carregarTabela(""); 
        }
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
        String chave=txbusca.getText();
        carregarTabela("upper(cat_nome) like '"+chave.toUpperCase()+"%'"); 
    }
    
}
