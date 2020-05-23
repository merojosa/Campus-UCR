package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Utilities.UtilDates;
import cr.ac.ucr.ecci.cql.campus20.R;

/**
 * @class DeploymentScript
 * @brief Enables the app to have a single point of database entities creation.
 * Here should be placed all methods with test data to be written to the database.
 */
public class DeploymentScript {

    /**
     * Executes all the statements that create and populate the database.
     * Should be called once on application create in MainActivity.
     *
     * @param context Current application context.
     */
    public static void RunScript(Context context) {
        clearDatabase(context);
        createFaculties(context);
        createPlaces(context);
        createSchools(context);
        createCoordinates(context);
        createComments(context);
        createCoffeShops(context);
    }

    private static void clearDatabase(Context context) {
        DataAccess db = new DataAccess(context);
        db.resetDatabase();
        Log.d("reset", "Database was reset");
    }

    private static void createFaculties(Context context) {
        DataAccess db = new DataAccess(context);
        List<Faculty> list = new ArrayList<>();
        String[] Faculties = {"Artes", "Ciencias Agroalimentarias", "Ciencias Básicas", "Ciencias Económicas", "Ciencias Sociales", "Derecho",
                "Educación", "Farmacia", "Ingeniería", "Letras", "Medicina", "Microbiología", "Odontología"}; //Espacio vacío
        int[] Imagenes = {R.drawable.artes512px, R.drawable.agro512px, R.drawable.ciencias512,
                R.drawable.economicas512px, R.drawable.sociales512p, R.drawable.derecho215px,
                R.drawable.educa512px, R.drawable.farmacia512px, R.drawable.ingenieria512px,
                R.drawable.letras512px, R.drawable.medicina512px, R.drawable.micro512px,
                R.drawable.odonto512px};

        for (int i = 0; i < Faculties.length; ++i) {
            list.add(new Faculty(i, Faculties[i], "", Imagenes[i]));
        }
        for (Faculty f : list) {
            f.insert(context);
        }
        db.close();
        Log.d("faculties", "Faculties were inserted in database.");
    }

    private static void createPlaces(Context context) {
        DataAccess db = new DataAccess(context);
        List<Place> placesList = new ArrayList<>();
        String[] PlaceNames = {"Finca 1", "Finca 2", "Finca 3"};
        String[] PlaceDescriptions = {"Finca principal.", "Ciudad de la Investigación", "Deportivas"};
        String PlaceType = "Finca";
        int[] rating = {5, 3, 4};
        int floor = 0;
        for (int i = 0; i < PlaceNames.length; ++i) {
            placesList.add(new Place(i, PlaceNames[i], PlaceDescriptions[i], PlaceType, rating[i], floor));
        }
        for (Place p : placesList) {
            p.insert(context);
        }
        db.close();
        Log.d("places", "Places were inserted in database.");
    }

    private static void createSchools(Context context) {
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

        School artes = new School(0, 0, 0, "Artes Dramáticas", "", R.drawable.artesdramaticas512px);
        artes.insert(context);
        School artes1 = new School(1, 0, 0, "Artes Plásticas", "", R.drawable.artesplasticas512px);
        artes1.insert(context);
        School artes2 = new School(2, 0, 0, "Artes Musicales", "", R.drawable.musica512px);
        artes2.insert(context);

        School agro = new School(3, 1, 0, "Agronomía", "", R.drawable.agronomia512px);
        agro.insert(context);
        School agro1 = new School(4, 1, 0, "Zootecnia", "", R.drawable.zootecnia512px);
        agro1.insert(context);
        School agro2 = new School(5, 1, 0, "Tecnología de Alimentos", "", R.drawable.alimentos512px);
        agro2.insert(context);

        School ciencias = new School(6, 2, 0, "Física", "", R.drawable.fisica512px);
        ciencias.insert(context);
        School ciencias1 = new School(7, 2, 0, "Geología", "", R.drawable.geologia512px);
        ciencias1.insert(context);
        School ciencias2 = new School(8, 2, 0, "Matemática", "", R.drawable.mate512px);
        ciencias2.insert(context);
        School ciencias3 = new School(9, 2, 0, "Química", "", R.drawable.quimica512px);
        ciencias3.insert(context);
        School ciencias4 = new School(10, 2, 0, "Biología", "", R.drawable.biolo512px);
        ciencias4.insert(context);

        School economicas = new School(11, 3, 0, "Administración de Negocios", "", R.drawable.admin512px);
        economicas.insert(context);
        School economicas1 = new School(12, 3, 0, "Administración Pública", "", R.drawable.administracion);
        economicas1.insert(context);
        School economicas2 = new School(13, 3, 0, "Economía", "", R.drawable.economicas);
        economicas2.insert(context);
        School economicas3 = new School(14, 3, 0, "Estadística", "", R.drawable.estadisticas512px);
        economicas3.insert(context);

        School sociales = new School(15, 4, 1, "Psicología", "", R.drawable.psico512px);
        sociales.insert(context);
        School sociales1 = new School(16, 4, 1, "Ciencias Políticas", "", R.drawable.policitos512px);
        sociales1.insert(context);
        School sociales2 = new School(17, 4, 1, "Comunicación Colectiva", "", R.drawable.comunicacion512px);
        sociales2.insert(context);
        School sociales3 = new School(18, 4, 1, "Trabajo Social", "", R.drawable.ts512px);
        sociales3.insert(context);
        School sociales4 = new School(19, 4, 1, "Historia", "", R.drawable.historia512px);
        sociales4.insert(context);
        School sociales5 = new School(20, 4, 1, "Geografía", "", R.drawable.geografia512px);
        sociales5.insert(context);
        School sociales6 = new School(21, 4, 1, "Antropología", "", R.drawable.antropologia512px);
        sociales6.insert(context);
        School sociales7 = new School(22, 4, 1, "Sociología", "", R.drawable.socio512px);
        sociales7.insert(context);

        School derecho = new School(23, 5, 0, "Derecho", "", R.drawable.derecho512px);
        derecho.insert(context);

        School educacion = new School(24, 6, 0, "Formación Docente", "", R.drawable.docente512px);
        educacion.insert(context);
        School educacion1 = new School(25, 6, 0, "Orientación y Educación Especial", "", R.drawable.orientacion512px);
        educacion1.insert(context);
        School educacion2 = new School(26, 6, 0, "Bibliotecología y Ciencias de la Información", "", R.drawable.biblio512px);
        educacion2.insert(context);
        School educacion3 = new School(27, 6, 3, "Educación Física y Deportes", "", R.drawable.edufi512px);
        educacion3.insert(context);
        School educacion4 = new School(28, 6, 0, "Administración Educativa", "", R.drawable.admineduca512px);
        educacion4.insert(context);

        School farmacia = new School(29, 7, 0, "Farmacia", "", R.drawable.farmacos512px);
        farmacia.insert(context);

        School ingenieria = new School(30, 8, 1, "Ingeniería Civil", "", R.drawable.civil512px);
        ingenieria.insert(context);
        School ingenieria1 = new School(31, 8, 1, "Ingeniería Eléctrica", "", R.drawable.electrica512px);
        ingenieria1.insert(context);
        School ingenieria2 = new School(32, 8, 1, "Ingeniería Industrial", "", R.drawable.industrial512px);
        ingenieria2.insert(context);
        School ingenieria3 = new School(33, 8, 1, "Ingeniería Mecánica", "", R.drawable.mecanica512px);
        ingenieria3.insert(context);
        School ingenieria4 = new School(34, 8, 1, "Ingeniería Química", "", R.drawable.ingquimica512px);
        ingenieria4.insert(context);
        School ingenieria5 = new School(35, 8, 0, "Arquitectura", "", R.drawable.arqui512px);
        ingenieria5.insert(context);
        School ingenieria6 = new School(36, 8, 0, "Computación e Informática", "", R.drawable.compu512px);
        ingenieria6.insert(context);
        School ingenieria7 = new School(37, 8, 1, "Ingeniería de Biosistemas", "", R.drawable.biosistemas512px);
        ingenieria7.insert(context);
        School ingenieria8 = new School(38, 8, 1, "Ingeniería Topográfica", "", R.drawable.topo512px);
        ingenieria8.insert(context);

        School letras = new School(39, 9, 0, "Filología, Lingüistica y Literatura", "", R.drawable.filologia512px);
        letras.insert(context);
        School letras2 = new School(40, 9, 0, "Filosofía", "", R.drawable.filo512px);
        letras2.insert(context);
        School letras3 = new School(41, 9, 0, "Lenguas Modernas", "", R.drawable.lenguas512px);
        letras3.insert(context);

        School medicina = new School(42, 10, 0, "Enfermería", "", R.drawable.enfermeria512px);
        medicina.insert(context);
        School medicina1 = new School(43, 10, 0, "Medicina", "", R.drawable.medicinas512px);
        medicina1.insert(context);
        School medicina2 = new School(44, 10, 0, "Nutrición", "", R.drawable.nutricion512px);
        medicina2.insert(context);
        School medicina3 = new School(45, 10, 0, "Tecnologías de la Salud", "", R.drawable.alimentos512px);
        medicina3.insert(context);
        School medicina4 = new School(46, 10, 0, "Salud Púbilca", "", R.drawable.saludpublica512px);
        medicina4.insert(context);

        School micro = new School(47, 11, 0, "Microbiología", "", R.drawable.micro512px);
        micro.insert(context);

        School odonto = new School(48, 12, 0, "Odontología", "", R.drawable.odonto512px);
        odonto.insert(context);


        db.close();
        Log.d("places", "Schools were inserted in database.");
    }

    private static void createCoffeShops(Context context) {
        DataAccess db = new DataAccess(context);

        Coffe angar = new Coffe(0,  "Café Angar", "");
        angar.insert(context);

        Coffe noventaYCinco = new Coffe(1, "95 grados", "");
        noventaYCinco.insert(context);


        db.close();
        Log.d("coffeShops", "CoffeShops were inserted in database.");
    }

    private static void createCoordinates(Context context) {
        DataAccess db = new DataAccess(context);
        List<Coordinate> coordinateList = new ArrayList<>();
        int size = 49;
        int[] placesFK = new int[size];
        for (int i = 0; i < size; ++i) {
            placesFK[i] = i;
        }

        // Adding the real coordinates
        double[] pairCoodinates = {
                9.9342365, -84.050532,
                9.9363702, -84.0483954,
                9.9374068, -84.0503629,
                9.9386464, -84.0505298,
                9.9393851, -84.0488164,
                9.9403089, -84.0481914,
                9.9364933, -84.0523176,
                9.9381168, -84.0535935,
                9.9364933, -84.0523176,
                9.9372417, -84.0490453,
                9.9376465, -84.0516436,
                9.9368941, -84.0518023,
                9.9370248, -84.0514024,
                9.9372525, -84.0519284,
                9.9369581, -84.0514777,
                9.9376971, -84.0441797,
                9.937377, -84.0444508,
                9.9375567, -84.0441602,
                9.9374072, -84.0445572,
                9.9376551, -84.0444486,
                9.9373135, -84.0447285,
                9.9376218, -84.0447799,
                9.9367071, -84.0529196,
                9.9367365, -84.0535668,
                9.9367365, -84.0535668,
                9.936537, -84.0521004,
                9.9383532, -84.0556187,
                9.9439988, -84.0474695,
                9.9361541, -84.0509078,
                9.938875, -84.0521707,
                9.9377147, -84.0439456,
                9.936884, -84.0442343,
                9.9376215, -84.0462053,
                9.9367091, -84.0440834,
                9.9372878, -84.0501568,
                9.934584, -84.0547457,
                9.9379246, -84.0541789,
                9.9379246, -84.0541789,
                9.9376555, -84.0463562,
                9.9384127, -84.0528675,
                9.9384127, -84.0528675,
                9.9384127, -84.0528675,
                9.9230603, -84.052573,
                9.9387007, -84.0529767,
                9.9390325, -84.0470175,
                9.9384571, -84.0560422,
                9.9388922, -84.0479994,
                9.9379464, -84.0514961,
                9.943473, -84.0469052
        };
        // End of adding the real coordinates

        for (int i = 0, index = 0; i < pairCoodinates.length - 1; i += 2, ++index) {
            coordinateList.add(new Coordinate(index, placesFK[index], pairCoodinates[i], pairCoodinates[i + 1]));
        }
        for (Coordinate c : coordinateList) {
            c.insert(context);
        }
        db.close();
        Log.d("coordinates", "Coordinates were inserted in database.");
    }

    private static void createComments(Context context) {
        DataAccess db = new DataAccess(context);
        List<Comment> commentList = new ArrayList<>();
        int[] placesFK = {0, 1};
        String[] comments = {"La mejor escuela de la universidad.", "No tan buena, creen que son de compu pero no lo son."};
        for (int i = 0; i < placesFK.length; ++i) {
            commentList.add(new Comment(i, placesFK[i], comments[i], UtilDates.DateToString(Calendar.getInstance().getTime())));
        }
        for (Comment c : commentList) {
            c.insert(context);
        }
        db.close();
        Log.d("comments", "Comments were inserted in database.");
    }
}
