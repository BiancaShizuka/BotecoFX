package botecofx;

import botecofx.db.util.Banco;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaCadastroController implements Initializable {

    @FXML
    private BorderPane painel;
    @FXML
    private TitledPane titlepane_cadastro;
    @FXML
    private VBox vbox_cadatros;
    @FXML
    private TitledPane titlepane_cadastro1;
    @FXML
    private VBox vbox_cadatros1;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void evtCadProduto(ActionEvent event){/*
        Parent root = FXMLLoader.load(getClass().getResource("TelaRelProdutos.fxml"));
        
        painel.setCenter(root);*/
        try{
            
            Parent root = FXMLLoader.load(getClass().getResource("TelaRelProduto.fxml"));
            painel.setCenter(root);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @FXML
    private void evtCadGarcon(ActionEvent event) {
        try{
            
            Parent root = FXMLLoader.load(getClass().getResource("TelaRelGarcon.fxml"));
            painel.setCenter(root);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @FXML
    private void evtCadCategoria(ActionEvent event) {
        try{
            
            Parent root = FXMLLoader.load(getClass().getResource("TelaRelCategoria.fxml"));
            painel.setCenter(root);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @FXML
    private void evtCadUnidade(ActionEvent event) {
        try{
            
            Parent root = FXMLLoader.load(getClass().getResource("TelaRelUnidade.fxml"));
            painel.setCenter(root);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @FXML
    private void evTipoPagamento(ActionEvent event) {
        try{
            
            Parent root = FXMLLoader.load(getClass().getResource("TelaRelTpgo.fxml"));
            painel.setCenter(root);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void evtMostrarComandas(ActionEvent event) {
        try{
            
            Stage stage=new Stage();
            Scene scene=new Scene(FXMLLoader.load(getClass().getResource("TelaPainelComandaFXML.fxml")));
            stage.setScene(scene);
            stage.setTitle("Comandas");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setHeight(700);
            stage.setWidth(1200);
            stage.showAndWait();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @FXML
    private void evtBackup(ActionEvent event) {
        try {
            Banco.backup("bkp.backup");
        } catch (Exception e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro no backup "+ e);
            alert.showAndWait();
        }
    }

    @FXML
    private void evtRestauracao(ActionEvent event) {
        try {
            Banco.restaurar("bkp.backup");
        } catch (Exception e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro no backup "+ e);
            alert.showAndWait();
        }
        
    }
    
}
