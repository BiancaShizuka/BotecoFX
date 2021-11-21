package botecofx;

import botecofx.db.dal.ComandaDAL;
import botecofx.db.dal.ProdutoDAL;
import botecofx.db.dal.TipopgtoDAL;
import botecofx.db.entidades.Comanda;
import botecofx.db.entidades.Comanda.Item;
import botecofx.db.entidades.Pagamento;
import botecofx.db.entidades.Produto;
import botecofx.db.entidades.Tipopgto;
import botecofx.util.MaskFieldUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaGerComandaFXMLController implements Initializable {

    @FXML
    private TableColumn<Item, Integer> colQtde;
    @FXML
    private TableColumn<Item, Produto> colProd;
    @FXML
    private TableColumn<Item, Double> colPreco;
    @FXML
    private TableView<Item> tabela;
    @FXML
    private TableView<Pagamento> tabela2;
    @FXML
    private TableColumn<Pagamento, Float> colvalor;
    @FXML
    private TableColumn<Pagamento, Tipopgto> coltpgo;
    @FXML
    private Spinner<Integer> txtQtde;
    @FXML
    private JFXComboBox<Produto> txtProduto;
    @FXML
    private JFXComboBox<Tipopgto> txtTpgo;
    @FXML
    private JFXTextField txtValor;
    
    private Comanda comanda;
    
    @FXML
    private Button btnCadProd;
    @FXML
    private Button btnCadPag;
    @FXML
    private Button btnVoltar;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       comanda=ComandaSingleton.Com;
        
       colQtde.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
       colProd.setCellValueFactory(new PropertyValueFactory<>("produto"));
       colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
       
       colvalor.setCellValueFactory(new PropertyValueFactory<>("valor"));
       coltpgo.setCellValueFactory(new PropertyValueFactory<>("tipopag"));
       
       txtProduto.setItems(FXCollections.observableArrayList(new ProdutoDAL().get("")));
       txtTpgo.setItems(FXCollections.observableArrayList(new TipopgtoDAL().get("")));
       
       txtQtde.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10,1));
       MaskFieldUtil.monetaryField(txtValor);
       
       if(comanda.getValorComanda()==comanda.getValorPagamentos())
            btnCadPag.setDisable(true);
       else
           btnCadPag.setDisable(false);
       
       if(comanda.getStatus()=='F')
       {
           btnCadPag.setDisable(true);
           btnCadProd.setDisable(true);
           txtProduto.setDisable(true);
           txtQtde.setDisable(true);
           txtTpgo.setDisable(true);
           txtValor.setDisable(true);
       }
       carregarTabela();
       carregarTabela2();
    }    
    public void carregarTabela()
    {
        tabela.setItems(FXCollections.observableArrayList(comanda.getItens()));
    }
    public void carregarTabela2()
    {
        tabela2.setItems(FXCollections.observableArrayList(comanda.getPgtos()));
    }
    @FXML
    private void evtFechar(ActionEvent event) {
        btnVoltar.getScene().getWindow().hide();
    }

    @FXML
    private void evtCadastraItem(ActionEvent event) {
        if(txtProduto.getItems()!=null)
        {
            comanda.addItem(new ProdutoDAL().get(txtProduto.getValue().getId()),txtQtde.getValue());
            btnCadPag.setDisable(false);
        }
        carregarTabela();
    }

    @FXML
    private void evtCadastrarPag(ActionEvent event) {
        
        if(txtTpgo.getItems()!=null)
        {
            String v=txtValor.getText();
            v=v.replace(',', '.');
            double pagar;
            System.out.println(v);
            System.out.println("Comanda"+comanda.getValorComanda());
            System.out.println("Pagamentos:"+comanda.getValorPagamentos());
            if(comanda.getValorComanda()>comanda.getValorPagamentos())
            {
                if(comanda.getValorComanda()<(comanda.getValorPagamentos()+Double.parseDouble(v)))
                {
                    pagar=comanda.getValorComanda()-comanda.getValorPagamentos();
                    System.out.println("Entri no if"+(comanda.getValorComanda()-comanda.getValorPagamentos()));
                    comanda.addPagamento(new TipopgtoDAL().get(txtTpgo.getValue().getId()),
                            pagar);
                }
                else
                    comanda.addPagamento(new TipopgtoDAL().get(txtTpgo.getValue().getId()),Double.parseDouble(v));
            }
            if(comanda.getValorComanda()==comanda.getValorPagamentos())
                   btnCadPag.setDisable(true);
               
        }
        carregarTabela2();
    }

}
