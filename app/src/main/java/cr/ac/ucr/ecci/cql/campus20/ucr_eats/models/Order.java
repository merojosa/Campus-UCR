package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

import java.util.Date;

public class Order
{
    private String username;
    private String restaurant;
    private double restLatitude;
    private double restLongitude;
    private String idOrder;
    private Meal meal;
    private Date date;
    private double latitude;
    private double longitude;
    private OrderStatus status;

    // Si se borra esto, se cae la compra jeje
    public Order(){}

//    public Order(String username, Meal meal, String restaurant, Date date, double latitude, double longitude)
//    {
//        this.username = username;
//        this.meal = meal;
//        this.restaurant = restaurant;
//        this.date = date;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }

    public Order(String username, Meal meal, String restaurant, double rlatitude, double rlongitude, Date date, double latitude, double longitude, OrderStatus status)
    {
        this.username = username;
        this.meal = meal;
        this.restaurant = restaurant;
        this.restLatitude = rlatitude;
        this.restLongitude = rlongitude;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRestLatitude() {
        return restLatitude;
    }

    public void setRestLatitude(double restLatitude) {
        this.restLatitude = restLatitude;
    }

    public double getRestLongitude() {
        return restLongitude;
    }

    public void setRestLongitude(double restLongitude) {
        this.restLongitude = restLongitude;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
