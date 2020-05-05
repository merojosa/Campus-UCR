package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Utilities.UtilDates;

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
        createCoordinates(context);
        createComments(context);
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
        /*
        List<School> schoolList = new ArrayList<>();
        String[] SchoolNames = {"Escuela de Ciencias de la Computación e Informática", "Escuela de Ingeniería Eléctrica"};
        String[] SchoolDescriptions = {"", ""};
        /*Both belong to Engineering Faculty, in position 8 of Faculties array above.*/
        int[] FacultiesFK = {8, 8};
        /*ECCI is in finca 1, and EIE is in finca 2.
        int[] PlacesFK = {1, 2};
        for(int i = 0; i < SchoolNames.length; ++i){
            schoolList.add(new School(i, FacultiesFK[i], PlacesFK[i], SchoolNames[i], SchoolDescriptions[i]));
        }
        for(School s : schoolList){
            s.insert(context);
        }
*/

        //Schools goes from 0 to n, in alph order

        School artes = new School(0, 0, 0, "Artes Dramáticas", "");
        artes.insert(context);
        School artes1 = new School(1, 0, 0, "Artes Plásticas", "");
        artes1.insert(context);
        School artes2 = new School(2, 0, 0, "Artes Musicales", "");
        artes2.insert(context);

        School agro = new School(3, 1, 0, "Agronomía", "");
        agro.insert(context);
        School agro1 = new School(4, 1, 0, "Zootecnia", "");
        agro1.insert(context);
        School agro2 = new School(5, 1, 0, "Tecnología de Alimentos", "");
        agro2.insert(context);

        School ciencias = new School(6, 2, 0, "Física", "");
        ciencias.insert(context);
        School ciencias1 = new School(7, 2, 0, "Geología", "");
        ciencias1.insert(context);
        School ciencias2 = new School(8, 2, 0, "Matemática", "");
        ciencias2.insert(context);
        School ciencias3 = new School(9, 2, 0, "Química", "");
        ciencias3.insert(context);
        School ciencias4 = new School(10, 2, 0, "Biología", "");
        ciencias4.insert(context);

        School economicas = new School(11, 3, 0, "Administración de Negocios", "");
        economicas.insert(context);
        School economicas1 = new School(12, 3, 0, "Administración Pública", "");
        economicas1.insert(context);
        School economicas2 = new School(13, 3, 0, "Economía", "");
        economicas2.insert(context);
        School economicas3 = new School(14, 3, 0, "Estadística", "");
        economicas3.insert(context);

        School sociales = new School(15, 4, 1, "Psicología", "");
        sociales.insert(context);
        School sociales1 = new School(16, 4, 1, "Ciencias Políticas", "");
        sociales1.insert(context);
        School sociales2 = new School(17, 4, 1, "Comunicación Colectiva", "");
        sociales2.insert(context);
        School sociales3 = new School(18, 4, 1, "Trabajo Social", "");
        sociales3.insert(context);
        School sociales4 = new School(19, 4, 1, "Historia", "");
        sociales4.insert(context);
        School sociales5 = new School(20, 4, 1, "Geografía", "");
        sociales5.insert(context);
        School sociales6 = new School(21, 4, 1, "Antropología", "");
        sociales6.insert(context);
        School sociales7 = new School(22, 4, 1, "Sociología", "");
        sociales7.insert(context);

        School derecho = new School(23, 5, 0, "Derecho", "");
        derecho.insert(context);

        School educacion = new School(24, 6, 0, "Formación Docente", "");
        educacion.insert(context);
        School educacion1 = new School(25, 6, 0, "Orientación y Educación Especial", "");
        educacion1.insert(context);
        School educacion2 = new School(26, 6, 0, "Bibliotecología y Ciencias de la Información", "");
        educacion2.insert(context);
        School educacion3 = new School(27, 6, 3, "Educación Física y Deportes", "");
        educacion3.insert(context);
        School educacion4 = new School(28, 6, 0, "Administración Educativa", "");
        educacion4.insert(context);

        School farmacia = new School(29, 7, 0, "Farmacia", "");
        farmacia.insert(context);

        School ingenieria = new School(30, 8, 1, "Ingeniería Civil", "");
        ingenieria.insert(context);
        School ingenieria1 = new School(31, 8, 1, "Ingeniería Eléctrica", "");
        ingenieria1.insert(context);
        School ingenieria2 = new School(32, 8, 1, "Ingeniería Industrial", "");
        ingenieria2.insert(context);
        School ingenieria3 = new School(33, 8, 1, "Ingeniería Mecánica", "");
        ingenieria3.insert(context);
        School ingenieria4 = new School(34, 8, 1, "Ingeniería Química", "");
        ingenieria4.insert(context);
        School ingenieria5 = new School(35, 8, 0, "Arquitectura", "");
        ingenieria5.insert(context);
        School ingenieria6 = new School(36, 8, 0, "Computación e Informática", "");
        ingenieria6.insert(context);
        School ingenieria7 = new School(37, 8, 1, "Ingeniería de Biosistemas", "");
        ingenieria7.insert(context);
        School ingenieria8 = new School(38, 8, 1, "Ingeniería Topográfica", "");
        ingenieria8.insert(context);

        School letras = new School(39, 9, 0, "Filología, Lingüistica y Literatura", "");
        letras.insert(context);
        School letras2 = new School(40, 9, 0, "Filosofía", "");
        letras2.insert(context);
        School letras3 = new School(41, 9, 0, "Lenguas Modernas", "");
        letras3.insert(context);

        School medicina = new School(42, 10, 0, "Enfermería", "");
        medicina.insert(context);
        School medicina1 = new School(43, 10, 0, "Medicina", "");
        medicina1.insert(context);
        School medicina2 = new School(44, 10, 0, "Nutrición", "");
        medicina2.insert(context);
        School medicina3 = new School(45, 10, 0, "Tecnologías de la Salud", "");
        medicina3.insert(context);
        School medicina4 = new School(46, 10, 0, "Salud Púbilca", "");
        medicina4.insert(context);

        School micro = new School(47, 11, 0, "Microbiología", "");
        micro.insert(context);

        School odonto = new School(48, 12, 0, "Odontología", "");
        odonto.insert(context);



        db.close();
        Log.d("places", "Schools were inserted in database.");
    }

    private static void createCoordinates(Context context){
        DataAccess db = new DataAccess(context);
        List<Coordinate> coordinateList = new ArrayList<>();
        int[] placesFK = {0,1};
        double[] latitude = {9.9380801, 9.9371256};
        double[] longitude = {-84.0528859, -84.0441968};
        for(int i = 0; i < latitude.length; ++i){
            coordinateList.add(new Coordinate(i, placesFK[i], latitude[i], longitude[i]));
        }
        for(Coordinate c : coordinateList){
            c.insert(context);
        }
        db.close();
        Log.d("coordinates", "Coordinates were inserted in database.");
    }

    private static void createComments(Context context){
        DataAccess db = new DataAccess(context);
        List<Comment> commentList = new ArrayList<>();
        int[] placesFK = {0,1};
        String[] comments = {"La mejor escuela de la universidad.", "No tan buena, creen que son de compu pero no lo son."};
        for(int i = 0; i < placesFK.length; ++i){
            commentList.add(new Comment(i, placesFK[i], comments[i], UtilDates.DateToString(Calendar.getInstance().getTime())));
        }
        for(Comment c : commentList){
            c.insert(context);
        }
        db.close();
        Log.d("comments", "Comments were inserted in database.");
    }
}
