package botecofx.db.dal;

import botecofx.db.entidades.Produto;
import botecofx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAL {
    
    public boolean salvar(Produto p)
    {
        String sql="insert into produto values (default,#1,#2,'#3',#4,'#5')";
        sql=sql.replace("#1",""+p.getCategoria().getId());
        sql=sql.replace("#2",""+p.getUnidade().getId());
        sql=sql.replace("#3",p.getNome());
        sql=sql.replace("#4",""+p.getPreco());
        sql=sql.replace("#5",p.getDescricao());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(Produto p)
    {
        String sql="update produto set cat_id = #1, uni_id = #2, prod_nome = '#3', prod_preco = #4,prod_descr = '#5' where prod_id ="+p.getId();
        sql=sql.replace("#1",""+p.getCategoria().getId());
        sql=sql.replace("#2",""+p.getUnidade().getId());
        sql=sql.replace("#3",p.getNome());
        sql=sql.replace("#4",""+p.getPreco());
        sql=sql.replace("#5",p.getDescricao());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(int id)
    {
        return Banco.getCon().manipular("delete from produto where prod_id = "+id);
    }
    
    public Produto get(int id)
    {
        Produto prod=null;
        ResultSet rs=Banco.getCon().consultar("select * from produto where prod_id="+id);
        try{
            if(rs.next())
                prod=new Produto(rs.getInt("prod_id"),
                                 rs.getString("prod_nome"),
                                 rs.getDouble("prod_preco"),
                                 rs.getString("prod_descr"),
                                 new CategoriaDAL().get(rs.getInt("cat_id")),
                                 new UnidadeDAL().get(rs.getInt("uni_id")));
        }catch(Exception e){System.out.println(e);}
        return prod;
    }
    
    public List<Produto> get(String filtro)
    {
        List <Produto> prods=new ArrayList();
        String sql="select * from produto";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs=Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                prods.add(new Produto(rs.getInt("prod_id"),
                                 rs.getString("prod_nome"),
                                 rs.getDouble("prod_preco"),
                                 rs.getString("prod_descr"),
                                 new CategoriaDAL().get(rs.getInt("cat_id")),
                                 new UnidadeDAL().get(rs.getInt("uni_id"))));
     }catch(Exception e){System.out.println(e);}
     return prods;   
    }
}
