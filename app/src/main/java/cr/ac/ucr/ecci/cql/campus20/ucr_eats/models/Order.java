package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

public class Order
{
    private String username;
    private Meal meal;

    public Order(String username, Meal meal)
    {
        this.username = username;
        this.meal = meal;
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
