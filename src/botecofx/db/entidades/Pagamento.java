package botecofx.db.entidades;

public class Pagamento {
    private int id;
    private double valor;
    private Tipopgto tipopag;

    public Pagamento(int id, double valor, Tipopgto tipopag) {
        this.id = id;
        this.valor = valor;
        this.tipopag = tipopag;
    }

    public Pagamento(double valor, Tipopgto tipopag) {
        this(0,valor,tipopag);
    }

    public Pagamento() {
        this(0,0,new Tipopgto());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Tipopgto getTipopag() {
        return tipopag;
    }

    public void setTipopag(Tipopgto tipopag) {
        this.tipopag = tipopag;
    }
    
    
}
