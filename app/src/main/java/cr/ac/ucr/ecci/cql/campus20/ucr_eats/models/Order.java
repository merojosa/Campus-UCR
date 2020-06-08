package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

import java.util.Date;

public class Order
{
    private String username;
    private String restaurant;
    private String idOrder;
    private Meal meal;
    private Date date;

    // Si se borra esto, se cae la compra jeje
    public Order(){}

    public Order(String username, Meal meal, String restaurant, Date date)
    {
        this.username = username;
        this.meal = meal;
        this.restaurant = restaurant;
        this.date = date;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
