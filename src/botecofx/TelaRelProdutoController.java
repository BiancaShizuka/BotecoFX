package botecofx;

import botecofx.db.dal.ProdutoDAL;
import botecofx.db.entidades.Categoria;
import botecofx.db.entidades.Produto;
import botecofx.db.entidades.Unidade;
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

public class TelaRelProdutoController implements Initializable {

    @FXML
    private TableView<Produto> tabela;
    @FXML
    private TableColumn<Produto, Integer> colid;
    @FXML
    private TableColumn<Produto, String> colproduto;
    @FXML
    private TableColumn<Produto, Double> colpreco;
    @FXML
    private TableColumn<Produto, Categoria> colcategoria;
    @FXML
    private TableColumn<Produto, Unidade> colunidade;
    @FXML
    private TextField txbusca;
    public static Produto prod;
    @FXML
    private Button btnPesq;
    @FXML
    private Button btnCad;
    @FXML
    private Button btnDel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       colid.setCellValueFactory(new PropertyValueFactory<>("id"));
       colproduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
       colpreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
       colcategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
       colunidade.setCellValueFactory(new PropertyValueFactory<>("unidade"));
       carregarTabela("");
    }    
     void carregarTabela(String filtro)
    {
        List <Produto> res = new ProdutoDAL().get(filtro);
        tabela.setItems(FXCollections.observableArrayList(res));
    }

    @FXML
    private void evtCadastrar(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        prod = tabela.getSelectionModel().getSelectedItem();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("TelaCadProduto.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Cadastrar Produto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        carregarTabela("");
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
        String chave=txbusca.getText();
        carregarTabela("upper(prod_nome) like '"+chave.toUpperCase()+"%'"); 
    }

    @FXML
    private void evtBuscarDigitacao(KeyEvent event) {
        String chave=txbusca.getText();
        carregarTabela("upper(prod_nome) like '"+chave.toUpperCase()+"%'"); 
        
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        int id=tabela.getSelectionModel().getSelectedItem().getId();
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja apagar o produto?");
        if(alert.showAndWait().get()==ButtonType.OK)
        {    new ProdutoDAL().apagar(id);
             carregarTabela(""); 
        }
    }
    
}


