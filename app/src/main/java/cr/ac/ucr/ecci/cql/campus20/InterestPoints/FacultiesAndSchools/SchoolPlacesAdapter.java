package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.R;

public class SchoolPlacesAdapter extends BaseAdapter {
    private List<Place> mData;
    private Context mContext;
    private int idImage;
    private String[] auxOriginalList;
    private int[] auxIndexName;

    public SchoolPlacesAdapter(List<Place> data, Context context, String[] originalList) {
        mData = data;
        mContext = context;
        auxOriginalList = originalList;
        idImage = R.drawable.drop_ampliar;
        auxIndexName = new int[]{idImage, idImage, idImage};
    }

    public int getCount() {
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    public Place getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Place place = mData.get(position);
        View rowView = inflater.inflate(R.layout.list_item_school_places, parent, false);
        TextView title = rowView.findViewById(R.id.tv_item_title);
        title.setText(place.getName());

        if(showImage(place.getName())){
            ImageView imagen = rowView.findViewById(R.id.imageListSchoolPlaces);
            imagen.setImageResource(idImage);
        }

        return rowView;
    }

    //Metodo para determinar que icono de dropdown debe ser mostrado
    public void verifyImage(boolean helperImage, String itemName){
        cleanIndexArray();
        if(helperImage){
            for(int i = 0; i < auxOriginalList.length; i++){
                if(auxOriginalList[i].equals(itemName)){
                    auxIndexName[i] = R.drawable.drop_contraer;
                }
            }
        }
    }

    //Metodo para determinar enque casos de la lista se debe mostrar el icono de dropdown
    public boolean showImage(String place){
        boolean show = false;
        int cont = 0;
        while(!show && cont < auxOriginalList.length){
            if (place.equals(auxOriginalList[cont])){
                show = true;
                idImage = auxIndexName[cont]; //Para desplegar el icono correcto
            }else{
                cont += 1;
            }
        }
        return show;
    }

    public void cleanIndexArray(){
        for(int i = 0; i<auxIndexName.length; i++){
            auxIndexName[i] = R.drawable.drop_ampliar;
        }
    }

}

