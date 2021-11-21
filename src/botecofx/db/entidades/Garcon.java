package botecofx.db.entidades;

import java.sql.Blob;

public class Garcon {
    private int id;
    private String nome;
    private String cpf;
    private String cep;
    private String endereco;
    private String cidade;
    private String uf;
    private String fone;
    private Blob foto;
    public Garcon() {
        this(0,"","","","","","","");
    }
    public Garcon(int id, String nome, String cpf, String cep, String endereco, String cidade, String uf, String fone/*, Blob foto*/) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cep = cep;
        this.endereco = endereco;
        this.cidade = cidade;
        this.uf = uf;
        this.fone = fone;
        this.foto = null;
    }

    public Garcon(String nome, String cpf, String cep, String endereco, String cidade, String uf, String fone) {
        this(0,nome,cpf,cep,endereco,cidade,uf,fone);
    } 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
