package botecofx.db.dal;

import botecofx.db.entidades.Unidade;
import botecofx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UnidadeDAL {
    public boolean salvar(Unidade u){
        String sql="insert into unidade values (default,'#1')";
        sql=sql.replace("#1", u.getNome());
       
        return Banco.getCon().manipular(sql);
       
    }
   
    public boolean alterar(Unidade u)
    {
       
        String sql="update unidade set uni_nome = '#1' where uni_id = "+u.getId();
        sql=sql.replace("#1",u.getNome());
        return Banco.getCon().manipular(sql);
    }
   
    public boolean apagar(int id)
    {
        return Banco.getCon().manipular("delete from unidade where uni_id = "+id);
    }
   
    public List<Unidade> get(String filtro)
    {
        List <Unidade> unis = new ArrayList();
        String sql="select * from unidade";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while(rs.next())
                unis.add(new Unidade(rs.getInt("uni_id"),
                             rs.getString("uni_nome")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       
        return unis;
    }
    public Unidade get(int id)
    {
        Unidade uni=null;
        ResultSet rs = Banco.getCon().consultar("select * from unidade where uni_id="+id);
        try {
            if(rs.next())
                uni=new Unidade(rs.getInt("uni_id"),rs.getString("uni_nome"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       
        return uni;
    }
    
}
