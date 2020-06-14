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
//    private List<String> originalList;

    public SchoolPlacesAdapter(List<Place> data, Context context, Boolean helperImage) {
        mData = data;
        mContext = context;
        verifyImage(helperImage);
//        originalList = mData;
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
//        boolean showImag = showImage(place);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView title = (TextView)rowView.findViewById(R.id.tv_item_title);
        title.setText(place.getName());

//        if(showImag){
            ImageView imagen = (ImageView)rowView.findViewById(R.id.imageListIP);
            imagen.setImageResource(idImage);
//        }

        return rowView;
    }

    public void verifyImage(boolean helperImage){
        if(!helperImage){
            idImage = R.drawable.drop_ampliar;
        }else{
            idImage = R.drawable.drop_contraer;
        }
    }

//    public boolean showImage(String place){
//        boolean show = false;
//        int cont = 0;
//        while(!show && cont < originalList.size()){
//            if (place.equals(originalList.get(0))){
//                show = true;
//            }else{
//                cont += 1;
//            }
//        }
//
//        return show;
//    }

}

