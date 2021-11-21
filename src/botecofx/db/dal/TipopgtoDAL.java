package botecofx.db.dal;

import botecofx.db.entidades.Tipopgto;
import botecofx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TipopgtoDAL {
    public boolean salvar(Tipopgto t){
        String sql="insert into tipopgto values (default,'#1')";
        sql=sql.replace("#1", t.getNome());
       
        return Banco.getCon().manipular(sql);
       
    }
   
    public boolean alterar(Tipopgto t)
    {
        String sql="update tipopgto set tpg_nome = '#1' where tpg_id = "+t.getId();
        sql=sql.replace("#1",t.getNome());
       
        return Banco.getCon().manipular(sql);
    }
   
    public boolean apagar(int id)
    {
        return Banco.getCon().manipular("delete from tipopgto where tpg_id = "+id);
    }public List<Tipopgto> get(String filtro)
    {
        List <Tipopgto> tipos = new ArrayList();
        String sql="select * from tipopgto";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
       
            while(rs.next())
                tipos.add(new Tipopgto(rs.getInt("tpg_id"),
                                 rs.getString("tpg_nome")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       
        return tipos;
    }
   
    
   
   
    public Tipopgto get(int id)
    {
        Tipopgto tipo=null;
        ResultSet rs = Banco.getCon().consultar("select * from tipopgto where tpg_id="+id);
        try {
            if(rs.next())
            {
               tipo = new Tipopgto(rs.getInt("tpg_id"),
                                 rs.getString("tpg_nome"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       
        return tipo;
    }
}
