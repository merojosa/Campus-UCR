package cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.AssignedOrder;

public class AssignedOrdersPresenter
{
    private List<AssignedOrder> orders;

    public AssignedOrdersPresenter(List<AssignedOrder> orders)
    {
        this.setOrders(orders);
    }

    public void onBindOrderAtPosition(int position, AssignedOrdersView view)
    {
        if(this.orders != null && this.orders.size() > 0)
        {
            AssignedOrder order = this.orders.get(position);

            view.setRestaurant(order.getOrden().getRestaurant());
            view.setMeal(order.getOrden().getMeal().getName());
            view.setDate(order.getOrden().getDate().toString());
        }
    }

    public List<AssignedOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<AssignedOrder> orders) {
        this.orders = orders;
    }
}
