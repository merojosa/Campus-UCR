package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;

public class MapUtilities {
    private Double currentLatitude;
    private Double currentLongitude;

    // List of pairs, the place and the distance between that place and the current location of the user
    private List<Place> newPlaces = new ArrayList<>();
    private List<Double> newDistances = new ArrayList<>();

    // To get the user location
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    public MapUtilities(Double currentLatitude, Double currentLongitude) {
        this.currentLongitude = currentLongitude;
        this.currentLatitude = currentLatitude;
        Log.d(this.currentLatitude + "", this.currentLongitude + "");
    }

    public List orderByDistance(List<Place> places) {
        int amountOfPlaces = places.size();

        for(int i = 0; i < amountOfPlaces; ++i) {

            Place tempPlace = places.get(i);
            Double newDistance = this.getDistance(tempPlace.getLatitude(), tempPlace.getLongitude());
            int orderedPlacesSize = this.newPlaces.size(); // The size of the elements that are already ordered

            if(orderedPlacesSize == 0) {
                this.newPlaces.add(tempPlace);
                this.newDistances.add(newDistance);
            } else {
                Boolean agregado = false;
                for(int i2 = 0; i2 < orderedPlacesSize; ++i2) {

                    Place orderedTempPlace = this.newPlaces.get(i2);
                    Double tempDistance = this.newDistances.get(i2);

                    if(newDistance <= tempDistance) {
                        // the new place must be in the i2 index of the this.placesAndDistances
                        Pair<Place, Double> newPair = new Pair<>(tempPlace, newDistance);
                        this.newPlaces.add(i2, tempPlace);
                        this.newDistances.add(i2, newDistance);
                        agregado = true;
                        break;
                    }
                }

                // If the element hasn't been added, then it's added at the end
                if(!agregado) {
                    this.newPlaces.add(tempPlace);
                    this.newDistances.add(newDistance);
                }
            }
        }

        return newPlaces;
    }

    // Method that returns the distance between the latitude and longitude in parameters
    // and the current latitude and logitide of the user
    private Double getDistance(Double latitude, Double longitude) {
        Double result = 0.0;
        Double earthRadius = 6371.0;

        Double lat1 = this.currentLatitude * (Math.PI/180);
        Double lon1 = this.currentLongitude * (Math.PI/180);

        Double lat2 = latitude * (Math.PI/180);
        Double lon2 = longitude * (Math.PI/180);

        Double difLats = (lat2 - lat1) * (Math.PI/180);
        difLats = (difLats < 0) ? (difLats*-1) : (difLats);

        Double difLongs = (lon2 - lon1) * (Math.PI/180);
        difLongs = (difLongs < 0) ? (difLongs*-1) : (difLongs);


        Double a = Math.sin(difLats/2) * Math.sin(difLats/2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(difLongs/2) * Math.sin(difLongs/2);

        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1- a));
        result = earthRadius * c;

        return result;
    }

}
