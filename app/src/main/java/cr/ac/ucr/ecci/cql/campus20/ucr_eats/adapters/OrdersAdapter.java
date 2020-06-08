package cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.Holder>
{
    private List<Order> list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class Holder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public TextView meal;
        public TextView restaurant;
        public TextView date;

        public Holder(View view)
        {
            super(view);
            meal = view.findViewById(R.id.orden_platillo);
            restaurant = view.findViewById(R.id.orden_restaurante);
            date = view.findViewById(R.id.orden_fecha);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrdersAdapter(List<Order> data) {
        list = data;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orden_pendiente, parent, false);

        return new Holder(view);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(Holder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.meal.setText(list.get(position).getMeal().getName());

        holder.restaurant.setText(list.get(position).getRestaurant());

        String formatDate = DateFormat.format("HH:mm dd/MM/yyyy",
                list.get(position).getDate()).toString();
        holder.date.setText(formatDate);

        // DateFormat.format("HH:mm dd/MM/yyyy", Calendar.getInstance().getTime()).toString();

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
