package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

public enum OrderStatus
{
    PENDIENTE(0),
    HACIA_SODA(1),
    HACIA_CASA(2);

    private int valor;

    OrderStatus(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

}
