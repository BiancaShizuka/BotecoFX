package botecofx.db.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Comanda {
    //inner class
    public static class Item{
        private Produto produto;
        private int quantidade;
        private double preco;

        public Item(Produto produto, int quantidade) {
            this.produto = produto;
            this.quantidade = quantidade;
            this.preco = produto.getPreco()*quantidade;
        }

        public Produto getProduto() {
            return produto;
        }

        public void setProduto(Produto produto) {
            this.produto = produto;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public double getPreco() {
            return preco;
        }

        public void setPreco(double preco) {
            this.preco = preco;
        }
        
    }
    
    private int id;
    private Garcon garcon;
    private int numero;
    private String nome;
    private LocalDate data;
    private String desc;
    private double valor;
    private char status;
    private List<Item> itens;
    private List<Pagamento> pgtos;

    public Comanda(Garcon garcon, int numero, String nome, LocalDate data, String desc, double valor, char status) {
        this(0,garcon,numero,nome,data,desc,valor,'A');
    }

    public Comanda(int id, Garcon garcon, int numero, String nome, LocalDate data, String desc, double valor, char status) {
        this.id = id;
        this.garcon = garcon;
        this.numero = numero;
        this.nome = nome;
        this.data = data;
        this.desc = desc;
        this.valor = valor;
        this.status = status;
        itens=new ArrayList();
        pgtos=new ArrayList();
    }
    
    public Comanda() {
        this(0,new Garcon(),0,"",LocalDate.now(),"",0,'A');
         
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Garcon getGarcon() {
        return garcon;
    }

    public void setGarcon(Garcon garcon) {
        this.garcon = garcon;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }
    
    public List<Item> getItens() {
        return itens;
    }
    
    public boolean addItem(Produto p,int q){
        return itens.add(new Item(p,q));
    }
    
    public boolean addPagamento(Tipopgto tp,double v){
        return pgtos.add(new Pagamento(v,tp));
    }
    
    public double getValorPagamentos(){
        double tot=0;
        for(Pagamento p:pgtos)
            tot+=p.getValor();
        return tot;
    }

    public List<Pagamento> getPgtos() {
        return pgtos;
    }
    
    public double getValorComanda(){
        double tot=0;
        for(Item i:itens)
            tot+=i.getPreco();
        return tot;
    }
    
}
