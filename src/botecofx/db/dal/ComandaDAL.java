package botecofx.db.dal;

import botecofx.db.entidades.Comanda;
import botecofx.db.entidades.Comanda.Item;
import botecofx.db.entidades.Pagamento;
import botecofx.db.entidades.Produto;
import botecofx.db.util.Banco;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComandaDAL {
    public boolean salvar(Comanda c)
    {
        String sql="insert into comanda values (default,#1,#2,'#3','#4','#5',#6,'#7')";
        sql=sql.replace("#1",""+c.getGarcon().getId());
        sql=sql.replace("#2",""+c.getNumero());
        sql=sql.replace("#3",c.getNome());
        sql=sql.replace("#4",""+c.getData().toString());
        sql=sql.replace("#5",c.getDesc());
        sql=sql.replace("#6",""+c.getValor());
        sql=sql.replace("#7",""+c.getStatus());
        Banco.getCon().manipular(sql);
        int pk = Banco.getCon().getMaxPK("comanda", "com_id");
        
        
        for(Pagamento p:c.getPgtos()){
            sql="insert into pagamento values (default,"+pk+",#1,#2)";
            System.out.println(p.getValor()+" "+pk+" "+p.getTipopag().getId());
            
            sql = sql.replace("#1",""+p.getValor());
            sql = sql.replace("#2", ""+p.getTipopag().getId());
            if(Banco.getCon().manipular(sql));
                System.out.println("Sucesso");
            
        }
        
        for(Item i:c.getItens()){
            sql="insert into item values ("+pk+",#1,#2,#3)";
            sql = sql.replace("#1",""+i.getProduto().getId());
            sql = sql.replace("#2",""+i.getQuantidade());
            sql = sql.replace("#3", ""+i.getPreco());
           
            Banco.getCon().manipular(sql);
      
        }
        return true;
    }
    
    public boolean alterar(Comanda c)
    {
       
        
        String sql="update comanda set gar_id=#1,com_numero=#2,com_nome='#3',com_data='#4',com_desc='#5',com_valor=#6,com_status='#7' " +
                "where com_id= "+c.getId();
        sql=sql.replace("#1",""+c.getGarcon().getId());
        sql=sql.replace("#2",""+c.getNumero());
        sql=sql.replace("#3",c.getNome());
        sql=sql.replace("#4",""+c.getData().toString());
        sql=sql.replace("#5",c.getDesc());
        sql=sql.replace("#6",""+c.getValor());
        sql=sql.replace("#7",""+c.getStatus());
       
        Banco.getCon().manipular(sql);
    
        sql="delete from pagamento where com_id = "+c.getId();
        Banco.getCon().manipular(sql);
        sql="delete from item where com_id = "+c.getId();
        Banco.getCon().manipular(sql);
        
        
        
        for(Pagamento p:c.getPgtos()){
            sql="insert into pagamento values (default,"+c.getId()+",#1,#2)";
            sql = sql.replace("#1",""+p.getValor());
            sql = sql.replace("#2", ""+p.getTipopag().getId());
            if(Banco.getCon().manipular(sql))
                System.out.println("Sucesso");
            
        }
        
        for(Item i:c.getItens()){
            sql="insert into item values ("+c.getId()+",#1,#2,#3)";
            
            sql = sql.replace("#1",""+i.getProduto().getId());
            sql = sql.replace("#2",""+i.getQuantidade());
            sql = sql.replace("#3", ""+i.getPreco());
           
            Banco.getCon().manipular(sql);
      
        }
        
        return true;

    }
    
    public boolean apagar(int id)
    {
        String sql="";
        sql="delete from pagamento where com_id = "+id;
        Banco.getCon().manipular(sql);
        sql="delete from item where com_id = "+id;
        Banco.getCon().manipular(sql);
        sql="delete from comanda where com_id = "+id;
        return Banco.getCon().manipular(sql);
    }
    
    public Comanda get(int id)
    {
        
        List<Item> itens=new ArrayList();
        List<Pagamento> pgtos=new ArrayList();
        Comanda com=null;
        ResultSet rs=Banco.getCon().consultar("select * from comanda where com_id="+id);
        try{
            if(rs.next())
            {
                com=new Comanda(rs.getInt("com_id"),
                                 new GarconDAL().get(rs.getInt("gar_id")),
                                 rs.getInt("com_numero"),
                                 rs.getString("com_nome"),
                                 LocalDate.parse(rs.getString("com_data")),
                                 rs.getString("com_desc"),
                                 rs.getDouble("com_valor"),
                                 rs.getString("com_status").charAt(0)
                                 );
            }
        }catch(Exception e){System.out.println(e);}
        if(com!=null)
        {
            rs=Banco.getCon().consultar("select * from pagamento where com_id="+id);
            try{
                while(rs.next())
                {
                    com.addPagamento(new TipopgtoDAL().get(rs.getInt("tpg_id")),rs.getDouble("pag_valor"));
                }
            }catch(Exception e){System.out.println(e);}
            rs=Banco.getCon().consultar("select * from item where com_id="+id);
            try{
                while(rs.next())
                {
                    com.addItem(new ProdutoDAL().get(rs.getInt("prod_id")), rs.getInt("it_quantidade"));
                    
                }
            }catch(Exception e){System.out.println(e);}
        }
        return com;
    }
    
    public List<Comanda> get(String filtro)
    {
        List <Comanda> coms=new ArrayList();
        Comanda c=null;
        String sql="select * from comanda";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs=Banco.getCon().consultar(sql),rs2;
        try{
            while(rs.next()){
               
                c=new Comanda(rs.getInt("com_id"),
                                 new GarconDAL().get(rs.getInt("gar_id")),
                                 rs.getInt("com_numero"),
                                 rs.getString("com_nome"),
                                 LocalDate.parse(rs.getString("com_data")),
                                 rs.getString("com_desc"),
                                 rs.getDouble("com_valor"),
                                 rs.getString("com_status").charAt(0)
                                 );
                rs2=Banco.getCon().consultar("select * from pagamento where com_id="+c.getId());
                try{
                    while(rs2.next())
                    {
                        c.addPagamento(new TipopgtoDAL().get(rs2.getInt("tpg_id")),rs2.getDouble("pag_valor"));
                    }
                }catch(Exception e){System.out.println(e);}
                rs2=Banco.getCon().consultar("select * from item where com_id="+c.getId());
                try{
                    while(rs2.next())
                    {
                        c.addItem(new ProdutoDAL().get(rs2.getInt("prod_id")), rs2.getInt("it_quantidade"));

                    }
                }catch(Exception e){System.out.println(e);}
                
                coms.add(c);
            }
     }catch(Exception e){System.out.println(e);}
     return coms;   
    }
}
