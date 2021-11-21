package botecofx.db.dal;

import botecofx.db.entidades.Garcon;
import botecofx.db.util.Banco;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class GarconDAL {
    public boolean salvar(Garcon g)
    {
        String sql="insert into garcon values (default,'#1','#2','#3','#4','#5','#6','#7',#8)";
        sql=sql.replace("#1",g.getNome());
        sql=sql.replace("#2",g.getCpf());
        sql=sql.replace("#3",g.getCep());
        sql=sql.replace("#4",g.getEndereco());
        sql=sql.replace("#5",g.getCidade());
        sql=sql.replace("#6",g.getUf());
        sql=sql.replace("#7",g.getFone());
        sql=sql.replace("#8",""+g.getFoto());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Garcon g)
    {
        String sql="update garcon set gar_nome = '#1', gar_cpf = '#2', gar_cep = '#3', "+
                    "gar_endereco = '#4' , gar_cidade= '#5', gar_uf = '#6', "+
                    "gar_fone= '#7'"+
                    "where gar_id ="+g.getId();
        sql=sql.replace("#1",g.getNome());
        sql=sql.replace("#2",g.getCpf());
        sql=sql.replace("#3",g.getCep());
        sql=sql.replace("#4",g.getEndereco());
        sql=sql.replace("#5",g.getCidade());
        sql=sql.replace("#6",g.getUf());
        sql=sql.replace("#7",g.getFone());
        //sql=sql.replace("#8",""+g.getFoto());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(int id)
    {
        return Banco.getCon().manipular("delete from garcon where gar_id = "+id);
    }
    public Garcon get(int id)
    {
        Garcon gar=null;
        ResultSet rs=Banco.getCon().consultar("select * from garcon where gar_id="+id);
        try{
            if(rs.next())
                gar=new Garcon(rs.getInt("gar_id"),
                                rs.getString("gar_nome"),
                                rs.getString("gar_cpf"),
                                rs.getString("gar_cep"),
                                rs.getString("gar_endereco"),
                                rs.getString("gar_cidade"),
                                rs.getString("gar_uf"),
                                rs.getString("gar_fone"));
                                //rs.getBlob("gar_foto"));
        }catch(Exception e){System.out.println(e);}
        return gar;
    }
    public List<Garcon> getCidade()
    {
        int i=0;
        List <Garcon> gars=new ArrayList();
        String sql="select * from garcon",cidade="";
        ResultSet rs=Banco.getCon().consultar(sql);
        try{
            while(rs.next())
            {
                
                i=0;
 
                while(i<gars.size() && !rs.getString("gar_cidade").equals(gars.get(i).getCidade()))
                {
            
                    i++;
                }
                if(i==gars.size()){
             
                    gars.add(new Garcon(rs.getInt("gar_id"),
                                    rs.getString("gar_nome"),
                                    rs.getString("gar_cpf"),
                                    rs.getString("gar_cep"),
                                    rs.getString("gar_endereco"),
                                    rs.getString("gar_cidade"),
                                    rs.getString("gar_uf"),
                                    rs.getString("gar_fone")));
                                    //rs.getBlob("gar_foto")));
                }
            }
     }catch(Exception e){System.out.println(e);}
     return gars;
    
    }
    public List<Garcon> get(String filtro)
    {
        List <Garcon> gars=new ArrayList();
        String sql="select * from garcon";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs=Banco.getCon().consultar(sql);
        try{
            while(rs.next())
                gars.add(new Garcon(rs.getInt("gar_id"),
                                rs.getString("gar_nome"),
                                rs.getString("gar_cpf"),
                                rs.getString("gar_cep"),
                                rs.getString("gar_endereco"),
                                rs.getString("gar_cidade"),
                                rs.getString("gar_uf"),
                                rs.getString("gar_fone")));
                                //rs.getBlob("gar_foto")));
     }catch(Exception e){System.out.println(e);}
     return gars;
    }
    public boolean SalvarImagem(int id, BufferedImage img)
    {
        try
        {
           PreparedStatement st;
            st = Banco.getCon().getConexao().prepareStatement("update garcon set gar_foto=? where gar_id=?");
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           ImageIO.write(img, "jpg", baos);
           InputStream is = new ByteArrayInputStream(baos.toByteArray());
           st.setBinaryStream(1, is, baos.toByteArray().length);
           st.setInt(2, id);
           st.executeUpdate();
           return true;
           
        }catch(Exception e)
        {System.out.println(e);}
        return false;
    }
    public File recuperarImagem(int id)
    {
        try{
            PreparedStatement ps = Banco.getCon().getConexao().prepareStatement("SELECT gar_foto FROM garcon WHERE gar_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
              byte[] imgBytes = rs.getBytes(1);
              // transforma um byte[] em uma imagem  
              InputStream in = new ByteArrayInputStream(imgBytes);
              BufferedImage bImageFromConvert = ImageIO.read(in);
              File file=new File("imagem.jpg");
              ImageIO.write(bImageFromConvert, "jpg", file);
              return file;
            }
            rs.close();
            ps.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

   
    
}

