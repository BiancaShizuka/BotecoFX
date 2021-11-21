package botecofx.db.dal;

import botecofx.db.entidades.Categoria;
import botecofx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAL {
    
    public boolean salvar(Categoria c)
    {
        String sql="insert into categoria values (default,'#1')";
        sql=sql.replace("#1",""+c.getNome());
        
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Categoria c)
    {
        String sql="update categoria set cat_nome = '#1' where cat_id ="+c.getId();
        sql=sql.replace("#1",c.getNome());
        
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(int id)
    {
        return Banco.getCon().manipular("delete from categoria where cat_id = "+id);
    }
    public Categoria get(int id)
    {
        Categoria cat=null;
        ResultSet rs=Banco.getCon().consultar("select * from categoria where cat_id="+id);
        try{
            if(rs.next())
                cat=new Categoria(rs.getInt("cat_id"),rs.getString("cat_nome"));
        }catch(Exception e){System.out.println(e);}
        return cat;
    }
    
    public List<Categoria> get(String filtro)
    {
        List <Categoria> cats=new ArrayList();
        String sql="select * from categoria";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs=Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                cats.add(new Categoria(rs.getInt("cat_id"),
                                 rs.getString("cat_nome")));
     }catch(Exception e){System.out.println(e);}
     return cats;
    }
    
}
