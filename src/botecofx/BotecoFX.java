package botecofx;

import botecofx.db.util.Banco;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class BotecoFX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("TelaCadastro.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaPrincipalFXML.fxml"));
        Parent root = (Parent) loader.load(); 
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        if(!Banco.conectar())
        {
            JOptionPane.showMessageDialog(null, "Erro ao conectar "
                    + Banco.getCon().getMensagemErro());
            if (JOptionPane.showConfirmDialog(null, "Confirma a tentativa de criação de uma nova base de dados") == JOptionPane.YES_OPTION) {
                if (Banco.criarBD("boteco")) {
                    try {
                        Banco.restaurar("bdutil/boteco.backup");
                        Banco.conectar();
                    } catch (Exception e) {
                    }
                }
                else
                {
                    Platform.exit();
                }
            }
        }
        launch(args);
 
    } 
    
}

/*
/*
        ProdutoDAL dal= new ProdutoDAL();
        Produto prod=dal.get(1);
        dal.apagar(1);
        for(Produto p: dal.get(""))
            System.out.println(p.getNome());
        */
        
        /*
        dal.salvar(new Produto("Coxinha de frango",5.50,"massa de batata",
                    new CategoriaDAL().get(1),new UnidadeDAL().get(1)));
        */
        
        /*Produto prod=dal.get(1);
        prod.setNome("Vodka");
        dal.alterar(prod);
        System.out.println("--->"+ prod.getNome());*/
        
        //dal.apagar(1);
        
        /*
        for(Produto p: dal.get(""))
            System.out.println(p.getCategoria());
        //launch(args);
        Platform.exit();*/
        
        //---------------------------------------------
        
        /*
        GarconDAL dalG=new GarconDAL();
        Garcon gar=dalG.get(2);
        dalG.apagar(2);
        //gar.setFone("(11)99815-9999");
        //dalG.alterar(gar);
        //dalG.salvar(new Garcon("Ana Beatriz","123.123.123-12","15326-45","Rua rua1 nº3","São paulo","SP","(18)1564-4856"));
        
        for(Garcon g: dalG.get(""))
            System.out.println(g.getNome());
        */
        /*
        Comanda comanda=new Comanda();
        comanda.setNumero(1000);
        comanda.setNome("balcao");
        comanda.setGarcon(new Garcon());
        comanda.addItem(new ProdutoDAL().get(0),2);
        new ComandaDAL().salvar(comanda);
*/
