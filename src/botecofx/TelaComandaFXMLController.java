/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botecofx;

import botecofx.db.dal.ComandaDAL;
import botecofx.db.dal.GarconDAL;
import botecofx.db.entidades.Comanda;
import botecofx.db.entidades.Garcon;
import botecofx.db.entidades.Pagamento;
import botecofx.db.util.Banco;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
 
public class TelaComandaFXMLController implements Initializable {

    @FXML
    private Label lbid;
    @FXML
    private Label lbvalor;
    private int id;
    @FXML
    private Label lbvalorpago;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    private void evtClickComanda(MouseEvent event) throws IOException {
        ComandaSingleton.Com= new ComandaDAL().get(id);
        Parent root=FXMLLoader.load(getClass().getResource("TelaCadComandaFXML.fxml"));
        
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setTitle("Cadastro");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        ControllerSingleton.controllerpainelcomandas.atualizar();
    
    }

    public void setID(Comanda c)
    {
        //busca os dados da comanda pelo id usando a comandaDAL
       
       double soma=0,soma2=0;
       for(Comanda.Item i:c.getItens())
           soma+=i.getPreco();
       for(Pagamento p:c.getPgtos())
           soma2+=p.getValor();
       id=c.getId();
       lbid.setText(""+c.getId());
       lbvalor.setText(String.format("R$ %.2f", soma));
       lbvalorpago.setText(String.format("R$ %.2f", soma2));
    }

    @FXML
    private void evtImprimir(ActionEvent event) {
        gerarRelatorio("",
                       "MyReports/info_comanda.jasper","Relatorio de comanda");
    }
    private void gerarRelatorio(String sql,String relat,String titulo)
    {
        try
        { //sql para obter os dados para o relatorio
            Map parametros = new HashMap();
            parametros.put("codComanda",id);
            JasperPrint jasperPrint = JasperFillManager.fillReport(relat,
                                            parametros,Banco.getCon().getConexao());
            JasperViewer.viewReport(jasperPrint, false);
        } 
 
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}

