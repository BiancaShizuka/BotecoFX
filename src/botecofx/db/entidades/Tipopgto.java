package botecofx.db.entidades;

public class Tipopgto {
    private int id;
    private String nome;

    public Tipopgto(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Tipopgto() {
        this(0,"");
    }

    public Tipopgto(String nome) {
        this(0,nome);
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

    @Override
    public String toString() {
        return nome;
    }

}
