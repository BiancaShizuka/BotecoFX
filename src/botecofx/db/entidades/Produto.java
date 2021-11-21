package botecofx.db.entidades;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private String descricao;
    private Categoria categoria; 
    private Unidade unidade;

    public Produto() {
        this(0,"",0,"",new Categoria(),new Unidade());
    }
    
    public Produto(int id, String nome, double preco, String descricao, Categoria categoria, Unidade unidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.categoria = categoria;
        this.unidade = unidade;
    }

    public Produto(String nome, double preco, String descricao, Categoria categoria, Unidade unidade) {
        this(0,nome,preco,descricao,categoria,unidade);
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    @Override
    public String toString() {
        return  nome;
    }
    
    
}
