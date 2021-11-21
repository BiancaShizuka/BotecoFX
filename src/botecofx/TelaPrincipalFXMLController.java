
package botecofx;

import botecofx.db.dal.GarconDAL;
import botecofx.db.entidades.Garcon;
import botecofx.db.util.Banco;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.io.IOException;
import java.net.URL;


import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javax.swing.JComboBox;

import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaPrincipalFXMLController implements Initializable {

    @FXML
    private BorderPane painel;
    @FXML
    private HBox hboxsuperior;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void evtModuloAdm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaCadastro.fxml"));
        Parent root = (Parent) loader.load(); 
        painel.setCenter(root);
        
    }

    @FXML
    private void evtModuloCom(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaPainelComandaFXML.fxml"));
        Parent root = (Parent) loader.load(); 
        ControllerSingleton.controllerpainelcomandas = loader.getController();
        painel.setCenter(root);
    }

    @FXML
    private void evtRelProdutos(ActionEvent event) {
        gerarRelatorio("select * from produto as p,categoria as c, unidade as u\n" +
                        "where p.cat_id=c.cat_id and p.uni_id=u.uni_id\n" +
                        "order by p.prod_nome",
                        "MyReports/rel_produtos.jasper","Relatorio de produtos");
    }
    @FXML
    private void evtRelGarcon(ActionEvent event) {
        
        gerarRelatorioCidade(" ",
                        "MyReports/garcon_cidade.jasper","Relatorio do garçom");
    }
    
    private void gerarRelatorio(String sql,String relat,String titulo)
    {
        try
        { //sql para obter os dados para o relatorio
            
          ResultSet rs = Banco.getCon().consultar(sql);
          //implementação da interface JRDataSource para DataSource ResultSet
          JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
          //chamando o relatório
          String jasperPrint =          
              JasperFillManager.fillReportToFile(relat,null, jrRS);
          JasperViewer viewer = new JasperViewer(jasperPrint, false, false);
          viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
          viewer.setTitle(titulo);//titulo do relatório
          viewer.setVisible(true);
        } catch (JRException erro)
        {  erro.printStackTrace(); }

    }
    @FXML
    private void evtComandaData(ActionEvent event) {
  
        gerarRelatorioData(" ",
                        "MyReports/comanda_data.jasper",
                        "Relatorio da comanda");
    }
    private void gerarRelatorioCidade(String sql,String relat,String titulo)
    {
        Garcon g;
        try
        { 
            JComboBox jcb = new JComboBox();
            List <Garcon> gars=new ArrayList();
            gars=new GarconDAL().getCidade();
            for (int i = 0; i < gars.size(); i++) {
                jcb.addItem(gars.get(i).getCidade());
            }

            JOptionPane.showMessageDialog(null, jcb, "Selecione a cidade", JOptionPane.QUESTION_MESSAGE);

            
            int index=jcb.getSelectedIndex();
            Map parametros = new HashMap();
            parametros.put("cidade",gars.get(index).getCidade());
            JasperPrint jasperPrint = JasperFillManager.fillReport(relat,
                                            parametros,Banco.getCon().getConexao());
            JasperViewer.viewReport(jasperPrint, false);
        } 
 
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
    private void gerarRelatorioData(String sql,String relat,String titulo)
    {
        try
        { 
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            JDateChooser jc=new JDateChooser();
            JDateChooser jc2=new JDateChooser();
            JOptionPane.showMessageDialog(null, jc,"Escolha a data inicial",JOptionPane.QUESTION_MESSAGE);
            JOptionPane.showMessageDialog(null, jc2,"Escolha a data final",JOptionPane.QUESTION_MESSAGE);
            
            Map parametros = new HashMap();
            Date start = jc.getDate();
            Date end = jc2.getDate();          
            parametros.put("Start_Date",start);
            parametros.put("End_Date",end);          
            JasperPrint jasperPrint = JasperFillManager.fillReport(relat,
                                            parametros,Banco.getCon().getConexao());
            JasperViewer.viewReport(jasperPrint, false);
        } 
 
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private void evtRelCategoria(ActionEvent event) {
        gerarRelatorio("select * from produto as p, categoria as c where p.cat_id=c.cat_id order by cat_nome",
                        "MyReports/rel_categorias.jasper","Relatorio de categorias");
    }

}

    

    
    
    
    

