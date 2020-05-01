package cr.ac.ucr.ecci.cql.campus20.IPModel;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @class DeploymentScript
 * @brief Enables the app to have a single point of database entities creation.
 * Here should be placed all methods with test data to be written to the database.
 * */
public class DeploymentScript {

    /**
     * Executes all the statements that create and populate the database.
     * Should be called once on application create in MainActivity.
     * @param context Current application context.
     * */
    public static void RunScript(Context context){
        clearDatabase(context);
        createFaculties(context);
        createPlaces(context);
        createSchools(context);
    }

    private static void clearDatabase(Context context){
        DataAccess db = new DataAccess(context);
        db.resetDatabase();
        Log.d("reset", "Database was reset");
    }

    private static void createFaculties(Context context) {
        DataAccess db = new DataAccess(context);
        List<Faculty> list = new ArrayList<>();
        String[] Faculties = {"Artes", "Ciencias Agroalimentarias", "Ciencias Básicas", "Ciencias Económicas", "Ciencias Sociales", "Derecho",
                "Educación", "Farmacia","Ingeniería", "Letras", "Medicina", "Microbiología", "Odontología"};

        for (int i = 0; i < Faculties.length; ++i) {
            list.add(new Faculty(i, Faculties[i], ""));
        }
        for(Faculty f : list){
            f.insert(context);
        }
        db.close();
        Log.d("faculties", "Faculties were inserted in database.");
    }

    private static void createPlaces(Context context){
        DataAccess db = new DataAccess(context);
        List<Place> placesList = new ArrayList<>();
        String[] PlaceNames = {"Finca 1", "Finca 2", "Finca 3"};
        String[] PlaceDescriptions = {"Finca principal.", "Ciudad de la Investigación", "Deportivas"};
        String PlaceType = "Finca";
        int[] rating = {5, 3, 4};
        int floor = 0;
        for(int i = 0; i < PlaceNames.length; ++i){
            placesList.add(new Place(i, PlaceNames[i], PlaceDescriptions[i], PlaceType, rating[i], floor));
        }
        for(Place p : placesList){
            p.insert(context);
        }
        db.close();
        Log.d("places", "Places were inserted in database.");
    }

    private static void createSchools(Context context){
        DataAccess db = new DataAccess(context);
        List<School> schoolList = new ArrayList<>();
        String[] SchoolNames = {"Escuela de Ciencias de la Computación e Informática", "Escuela de Ingeniería Eléctrica"};
        String[] SchoolDescriptions = {"", ""};
        /*Both belong to Engineering Faculty, in position 8 of Faculties array above.*/
        int[] FacultiesFK = {8, 8};
        /*ECCI is in finca 1, and EIE is in finca 2.*/
        int[] PlacesFK = {1, 2};
        for(int i = 0; i < SchoolNames.length; ++i){
            schoolList.add(new School(i, FacultiesFK[i], PlacesFK[i], SchoolNames[i], SchoolDescriptions[i]));
        }
        for(School s : schoolList){
            s.insert(context);
        }

        db.close();
        Log.d("places", "Schools were inserted in database.");
    }
}
