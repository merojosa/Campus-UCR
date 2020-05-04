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

    /**
     * Constructor del adaptador personalizado de temas
     * @param context indica en que contexto se encuentra el objeto actual (la lista)
     * @param listItems es donde se guardan los objetos de la lista
     */
    public AdaptadorTemas(Context context, ArrayList<Temas> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    /**
     * lleva la cuenta de cuantos elementos hay en la lista
     * @return listItems.size que es el numero total de elementos en la lista
     */
    @Override
    public int getCount() {
        return listItems.size();
    }

    /**
     * Este método devuelve el objeto que se encuentra en la posición dada
     * @param position es la posición en el array del objeto actual
     * @return
     */
    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Este método procesa los elementos para que puedan ser presentados como una lista
     * @param position la posición actual en la lista
     * @param convertView espacio para la lista personalizada
     * @param parent
     * @return la lista a presentar
     */
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
