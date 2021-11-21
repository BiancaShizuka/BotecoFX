package botecofx;

import botecofx.db.dal.ComandaDAL;
import botecofx.db.entidades.Comanda;
import botecofx.db.util.Banco;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

public class TelaPainelComandaFXMLController implements Initializable {

    @FXML
    private TilePane painel;
    private List<Comanda> coms=new ArrayList();
    public static boolean atualizar=false;
    @FXML
    private Button btnAdd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComanda();
    }    

    @FXML
    private void evtNovaComanda(ActionEvent event) {

        try
        {
            //Parent root=FXMLLoader.load(getClass().getResource("TelaComandaFXML.fxml"));
            FXMLLoader loader=new FXMLLoader(getClass().getResource("TelaComandaFXML.fxml"));
            Parent root =(Parent)loader.load();
            TelaComandaFXMLController ctr=loader.getController();
            ctr.setID(new Comanda());
            
            painel.getChildren().add(root);

        }  
        catch(Exception e){}
    }
    private void carregarComanda(){

        coms = new ComandaDAL().get("");
        painel.getChildren().clear();
        
        try{
            
            for(Comanda c:coms){
       
                FXMLLoader loader=new FXMLLoader(getClass().getResource("TelaComandaFXML.fxml"));
                Parent root=(Parent)loader.load();
                TelaComandaFXMLController ctr=loader.getController();
                ctr.setID(c);
                painel.getChildren().add(root);
            }

        }
        catch(Exception e){}
    }

    public void atualizar()
    {
        carregarComanda();
    }
}
