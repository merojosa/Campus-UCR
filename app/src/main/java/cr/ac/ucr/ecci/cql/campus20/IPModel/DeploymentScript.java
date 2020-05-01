package cr.ac.ucr.ecci.cql.campus20.IPModel;

import android.content.Context;

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
        createFaculties(context);
    }

    private static void createFaculties(Context context) {
        DataAccess db = new DataAccess(context);
        db.resetDatabase();
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
    }

    private static void createPlaces(Context context){

    }
}
