package cr.ac.ucr.ecci.cql.campus20.foro_general;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.R;

public class AdaptadorTemas extends BaseAdapter {
    private Context context;
    private ArrayList<Temas> listItems;

    public AdaptadorTemas(Context context, ArrayList<Temas> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Temas Item = (Temas) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_tema, null);
        ImageView img = convertView.findViewById(R.id.img);
        TextView name = convertView.findViewById(R.id.name);
        TextView description = convertView.findViewById(R.id.description);

        img.setImageResource(Item.getImg());
        name.setText(Item.getName());
        description.setText(Item.getDescription());
        return convertView;
    }
}
