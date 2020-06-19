package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

public class AssignedOrder
{
    private String correoRepartidor;
    private Order orden;

    public AssignedOrder(String correoRepartidor, Order orden)
    {
        this.correoRepartidor = correoRepartidor;
        this.orden = orden;
    }

    public String getCorreoRepartidor()
    {
        return correoRepartidor;
    }

    public void setCorreoRepartidor(String correoRepartidor)
    {
        this.correoRepartidor = correoRepartidor;
    }

    public Order getOrden()
    {
        return orden;
    }

    public void setOrden(Order orden)
    {
        this.orden = orden;
    }
}
