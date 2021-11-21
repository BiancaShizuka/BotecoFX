package botecofx;
import botecofx.util.Servicos;
import botecofx.db.dal.GarconDAL;
import botecofx.db.entidades.Garcon;
import botecofx.db.util.Banco;
import botecofx.util.MaskFieldUtil;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.json.JSONObject;

public class TelaCadGarconController implements Initializable {

    @FXML
    private JFXTextField txid;
    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txcpf;
    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXTextField txendereco;
    @FXML
    private JFXTextField txcidade;
    @FXML
    private JFXTextField txuf;
    @FXML
    private Button btnconfirmar;
    @FXML
    private Button btncancelar;
    @FXML
    private JFXTextField txfone;
    @FXML
    private ImageView imageview;
    private BufferedImage bimag;
    private FileChooser fchooser;
    private Image imagem;
    private File file=null;
    private String a;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()->{
            txnome.requestFocus();
        });
        if (TelaRelGarconController.garcon!=null)
        {
            Garcon aux=TelaRelGarconController.garcon;
            txid.setText(""+aux.getId());
            txnome.setText(aux.getNome());
            txcpf.setText(aux.getCpf());
            txcep.setText(""+aux.getCep());
            txendereco.setText(""+aux.getEndereco());
            txcidade.setText(""+aux.getCidade());
            txuf.setText(""+aux.getUf());
            txfone.setText(""+aux.getFone());
            file=new GarconDAL().recuperarImagem(aux.getId());
            carregarImagem();
        }
        MaskFieldUtil.cpfField(txcpf);
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.foneField(txfone);
    }    

    @FXML
    private void evtCadastrar(ActionEvent event) {
        if (!txnome.getText().isEmpty() && !txcpf.getText().isEmpty() 
            && !txcep.getText().isEmpty() && !txendereco.getText().isEmpty() 
            && !txcidade.getText().isEmpty() && !txuf.getText().isEmpty() 
            && !txfone.getText().isEmpty())
        {
            Garcon g;
            g=new Garcon(txnome.getText(),txcpf.getText(),txcep.getText(),
                        txendereco.getText(),txcidade.getText(),txuf.getText(),
                        txfone.getText());
            if(txid.getText().isEmpty()){
               new GarconDAL().salvar(g);
               int id=Banco.getCon().getMaxPK("garcon","gar_id");
                new GarconDAL().SalvarImagem(id, bimag);
            }
            else
            {  g.setId(Integer.parseInt(txid.getText()));
               new GarconDAL().alterar(g);
               new GarconDAL().SalvarImagem(TelaRelGarconController.garcon.getId(), bimag);
            }
            
            btnconfirmar.getScene().getWindow().hide();
            file=null;
        }
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        file=null;
        btncancelar.getScene().getWindow().hide();
    }

    @FXML
    private void evtAbrirImg(MouseEvent event) {
        fchooser=new FileChooser();
        fchooser.getExtensionFilters().addAll(
         new FileChooser.ExtensionFilter("jpg", "*.jpg"),
         new FileChooser.ExtensionFilter("png", "*.png"),
         new FileChooser.ExtensionFilter("gif","*.gif"),
         new FileChooser.ExtensionFilter("jpeg", "*.jpeg"));
        file =fchooser.showOpenDialog(null);
        carregarImagem();
    }
    private void carregarImagem(){ 
        if(file!=null)//um arquivo foi selecionado
        { 
            imagem=new Image(file.toURI().toString());
            imageview.setFitWidth(imageview.getFitWidth());
            imageview.setFitHeight(imageview.getFitHeight());
            imageview.setImage(imagem);
            bimag=SwingFXUtils.fromFXImage(imagem, null);
        }
    }
    @FXML
    private void evtPreencherCEP(KeyEvent event) {
        if(txcep.getText().length()==9){
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    a=Servicos.consultaCep(txcep.getText(),"json");
                    JSONObject jsonobject = new JSONObject(a);
                    String local = jsonobject.getString("localidade");
                    String end = jsonobject.getString("logradouro");
                    String uf = jsonobject.getString("uf");
                    txcidade.setText(local);
                    txendereco.setText(end);
                    txuf.setText(uf);
                    return null;
                }
            };
            new Thread(task).start();
        }
    }
    
}

