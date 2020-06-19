package cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;

public class PendingOrdersPresenter
{
    private List<Order> orders;

    public PendingOrdersPresenter(List<Order> orders)
    {
        this.setOrders(orders);
    }

    public void onBindOrderAtPosition(int position, PendingOrdersView view)
    {
        if(this.orders != null && this.orders.size() > 0)
        {
            Order order = this.orders.get(position);

            view.setRestaurant(order.getRestaurant());
            view.setMeal(order.getMeal().getName());
            view.setDate(order.getDate().toString());
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
