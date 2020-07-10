package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.ActivityInfo;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.ActivityInfoDao;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.IPRoomDatabase;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Utilities.UtilDates;
import cr.ac.ucr.ecci.cql.campus20.R;

/**
 * @class DeploymentScript
 * @brief Enables the app to have a single point of database entities creation.
 * Here should be placed all methods with test data to be written to the database.
 */
public class DeploymentScript {

    /**
     * Enum representing the activity names inserted in the Room database.
     * */
    public enum ActivityNames {
        INTEREST_POINTS,
        COFFEE_SHOPS,
        FOOD_COURTS,
        PHOTOCOPIERS,
        FACULTIES,
        LIBRARIES,
        OFFICES
    }

    private FirebaseDB db;
    /**
     * Executes all the statements that create and populate the database.
     * Should be called once on application create in MainActivity.
     *
     * @param db Firebase database instance where the data will be inserted.
     */
    public void RunScript(FirebaseDB db, Context context) {
        this.db = db;
        createFaculties();
        //createPlaces();
        createSchools();
        createCoffeShops();
        createLibrary();
        createOffice();
        createSoda();
        createPhotocopier();
        createLaboratory();
        createAsociation();
        createBathrooms();
        /*Saves the activity names in room database.*/
        AsyncTask.execute(new Runnable() {
            @Override
            public void run(){
                createActivityNames(context);
            }
        });
    }

    private void createActivityNames(Context context){
        IPRoomDatabase roomDatabase = Room.databaseBuilder(context, IPRoomDatabase.class, "IPRoomDatabase").build();
        ActivityInfoDao activityInfoDao = roomDatabase.activityInfoDao();
        String[] activityNames = {"Puntos de interés", "Cafeterías", "Sodas", "Fotocopiadoras", "Facultades", "Bibliotecas", "Oficinas"};
        for (int i = 0; i < activityNames.length; ++i) {
            activityInfoDao.insert(new ActivityInfo(i, activityNames[i]));
        }

    }

    private void createFaculties() {
        List<Faculty> list = new ArrayList<>();
        String[] falcuties = {"Artes", "Ciencias Agroalimentarias", "Ciencias Básicas", "Ciencias Económicas", "Ciencias Sociales", "Derecho",
                "Educación", "Farmacia", "Ingeniería", "Letras", "Medicina", "Microbiología", "Odontología"}; //Espacio vacío
        int[] Images = {R.drawable.artes512px, R.drawable.agro512px, R.drawable.ciencias512,
                R.drawable.economicas512px, R.drawable.sociales512p, R.drawable.derecho215px,
                R.drawable.educa512px, R.drawable.farmacia512px, R.drawable.ingenieria512px,
                R.drawable.letras512px, R.drawable.medicina512px, R.drawable.micro512px,
                R.drawable.odonto512px};

        for (int id = 0; id < falcuties.length; ++id) {
            list.add(new Faculty(id, falcuties[id], "", Images[id], "faculty"));
        }
        for (Faculty f : list) {
            db.insert("Faculty", f);
        }
        Log.d("faculties", "Faculties were inserted in database.");
    }

    //Creación de las cafeterías en la base de datos
    private void createCoffeShops() {

        Coffee angar = new Coffee(0,  "Café Angar", "El cafesito de los de Info", R.drawable.coffeshop512px,9.938051, -84.052969, "9:00 am - 6:00 pm", false, "Capuccino Vainilla");
        db.insert(Place.TYPE_COFFEE, angar);

        Coffee noventaYCinco = new Coffee(1, "95 grados", "La mejor cafetería de San Pedro", R.drawable.coffeshop512px,9.932305, -84.049769, "10:00 am - 8:00 pm", true, "Moccaccino" );
        db.insert(Place.TYPE_COFFEE, noventaYCinco);

        Coffee krakovia = new Coffee(2,  "Café Krakovia", "El mejor capucciono del país", R.drawable.coffeshop512px,9.937929, -84.053974, "09:00 am - 7:00 pm", true, "Pan Bom con Café");
        db.insert(Place.TYPE_COFFEE, krakovia);

        Coffee aroma = new Coffee(3, "Aroma y Sabor", "Para pasar la tarde", R.drawable.coffeshop512px,9.935199, -84.051267, "11:00 am - 8:00 pm", false, "Sandwich de Pollo");
        db.insert(Place.TYPE_COFFEE, aroma);

        Coffee musmanni = new Coffee(4,  "Musmanni San Pedro", "A 3 por mil el melcochón", R.drawable.pan512px,9.931796, -84.052945, "11:00 am - 8:00 pm", true, "Sandwich de Pollo");
        db.insert(Place.TYPE_COFFEE, musmanni);

        Coffee rincon = new Coffee(5, "Café El Rincón de la Vieja", "CAfé para toda la familia", R.drawable.coffeshop512px,9.931822, -84.052903, "09:00 am - 6:00 pm", false, "2x1 Cafés");
        db.insert(Place.TYPE_COFFEE, rincon);

        Coffee cafe_esquina = new Coffee(6, "La Cafetería de la Esquina", "La esquinita de San Peter ", R.drawable.coffeshop512px,9.931693, -84.052243, "10:00 am - 6:00 pm", true, "Café con Empanada");
        db.insert(Place.TYPE_COFFEE, cafe_esquina);

        Coffee ruiseñor = new Coffee(7, "Cafetería Ruiseñor", "Viva la liga...", R.drawable.coffeshop512px, 9.934383, -84.056577, "07:00 am - 5:00 pm", true, "Pie de Limón");
        db.insert(Place.TYPE_COFFEE, ruiseñor);

        Log.d("coffeShops", "CoffeShops were inserted in database.");
    }

    /*private void createPlaces() {
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
            db.insert("Place", p);
        }
        Log.d("places", "Places were inserted in database.");
    }*/

    private void createSchools() {

        int[] FacultiesFK = {8, 8};

        ArrayList<Comment> commentList = new ArrayList<>();
        /*ID's de la ECCI respectivamente.*/
        String[] comments = {"La mejor escuela de la universidad.", "No tan buena, creen que son de compu pero no lo son."};
        for (int i = 0; i < comments.length; ++i) {
            commentList.add(new Comment(i, 36, Place.TYPE_SCHOOL, comments[i], UtilDates.DateToString(Calendar.getInstance().getTime()), 0, null, 5, 10));
        }

        School artes = new School(0, 0, 0, "Artes Dramáticas", "", R.drawable.artesdramaticas512px,9.9342365, -84.050532);
        db.insert("School", artes);
        School artes1 = new School(1, 0, 0, "Artes Plásticas", "", R.drawable.artesplasticas512px, 9.9363702, -84.0483954);
        db.insert("School", artes1);
        School artes2 = new School(2, 0, 0, "Artes Musicales", "", R.drawable.musica512px, 9.9374068, -84.0503629);
        db.insert("School", artes2);

        School agro = new School(3, 1, 0, "Agronomía", "", R.drawable.agronomia512px, 9.9386464, -84.0505298);
        db.insert("School", agro);
        School agro1 = new School(4, 1, 0, "Zootecnia", "", R.drawable.zootecnia512px, 9.9393851, -84.0488164);
        db.insert("School", agro1);
        School agro2 = new School(5, 1, 0, "Tecnología de Alimentos", "", R.drawable.alimentos512px, 9.9403089, -84.0481914);
        db.insert("School", agro2);

        School ciencias = new School(6, 2, 0, "Física", "", R.drawable.fisica512px, 9.9364933, -84.0523176);
        db.insert("School", ciencias);
        School ciencias1 = new School(7, 2, 0, "Geología", "", R.drawable.geologia512px, 9.9381168, -84.0535935);
        db.insert("School", ciencias1);
        School ciencias2 = new School(8, 2, 0, "Matemática", "", R.drawable.mate512px, 9.9364933, -84.0523176);
        db.insert("School", ciencias2);
        School ciencias3 = new School(9, 2, 0, "Química", "", R.drawable.quimica512px,  9.9372417, -84.0490453);
        db.insert("School", ciencias3);
        School ciencias4 = new School(10, 2, 0, "Biología", "", R.drawable.biolo512px, 9.9376465, -84.0516436);
        db.insert("School", ciencias4);

        School economicas = new School(11, 3, 0, "Administración de Negocios", "", R.drawable.admin512px, 9.9368941, -84.0518023);
        db.insert("School", economicas);
        School economicas1 = new School(12, 3, 0, "Administración Pública", "", R.drawable.administracion, 9.9370248, -84.0514024);
        db.insert("School", economicas1);
        School economicas2 = new School(13, 3, 0, "Economía", "", R.drawable.economicas, 9.9372525, -84.0519284);
        db.insert("School", economicas2);
        School economicas3 = new School(14, 3, 0, "Estadística", "", R.drawable.estadisticas512px, 9.9369581, -84.0514777);
        db.insert("School", economicas3);

        School sociales = new School(15, 4, 1, "Psicología", "", R.drawable.psico512px, 9.9376971, -84.0441797);
        db.insert("School", sociales);
        School sociales1 = new School(16, 4, 1, "Ciencias Políticas", "", R.drawable.policitos512px,  9.937377, -84.0444508);
        db.insert("School", sociales1);
        School sociales2 = new School(17, 4, 1, "Comunicación Colectiva", "", R.drawable.comunicacion512px, 9.9375567, -84.0441602);
        db.insert("School", sociales2);
        School sociales3 = new School(18, 4, 1, "Trabajo Social", "", R.drawable.ts512px,  9.9374072, -84.0445572);
        db.insert("School", sociales3);
        School sociales4 = new School(19, 4, 1, "Historia", "", R.drawable.historia512px,  9.9376551, -84.0444486);
        db.insert("School", sociales4);
        School sociales5 = new School(20, 4, 1, "Geografía", "", R.drawable.geografia512px, 9.9373135, -84.0447285);
        db.insert("School", sociales5);
        School sociales6 = new School(21, 4, 1, "Antropología", "", R.drawable.antropologia512px, 9.9376218, -84.0447799);
        db.insert("School", sociales6);
        School sociales7 = new School(22, 4, 1, "Sociología", "", R.drawable.socio512px, 9.9367071, -84.0529196);
        db.insert("School", sociales7);

        School derecho = new School(23, 5, 0, "Derecho", "", R.drawable.derecho512px,9.9367365, -84.0535668);
        db.insert("School", derecho);

        School educacion = new School(24, 6, 0, "Formación Docente", "", R.drawable.docente512px, 9.9367365, -84.0535668);
        db.insert("School", educacion);
        School educacion1 = new School(25, 6, 0, "Orientación y Educación Especial", "", R.drawable.orientacion512px, 9.936537, -84.0521004);
        db.insert("School", educacion1);
        School educacion2 = new School(26, 6, 0, "Bibliotecología y Ciencias de la Información", "", R.drawable.biblio512px, 9.9383532, -84.0556187);
        db.insert("School", educacion2);
        School educacion3 = new School(27, 6, 3, "Educación Física y Deportes", "", R.drawable.edufi512px, 9.9439988, -84.0474695);
        db.insert("School", educacion3);
        School educacion4 = new School(28, 6, 0, "Administración Educativa", "", R.drawable.admineduca512px, 9.9361541, -84.0509078);
        db.insert("School", educacion4);

        School farmacia = new School(29, 7, 0, "Farmacia", "", R.drawable.farmacos512px, 9.938875, -84.0521707);
        db.insert("School", farmacia);

        School ingenieria = new School(30, 8, 1, "Ingeniería Civil", "", R.drawable.civil512px,  9.9377147, -84.0439456);
        db.insert("School", ingenieria);
        School ingenieria1 = new School(31, 8, 1, "Ingeniería Eléctrica", "", R.drawable.electrica512px, 9.936884, -84.0442343);
        db.insert("School", ingenieria1);
        School ingenieria2 = new School(32, 8, 1, "Ingeniería Industrial", "", R.drawable.industrial512px, 9.9376215, -84.0462053);
        db.insert("School", ingenieria2);
        School ingenieria3 = new School(33, 8, 1, "Ingeniería Mecánica", "", R.drawable.mecanica512px, 9.9367091, -84.0440834);
        db.insert("School", ingenieria3);
        School ingenieria4 = new School(34, 8, 1, "Ingeniería Química", "", R.drawable.ingquimica512px, 9.9372878, -84.0501568);
        db.insert("School", ingenieria4);
        School ingenieria5 = new School(35, 8, 0, "Arquitectura", "", R.drawable.arqui512px, 9.934584, -84.0547457);
        db.insert("School", ingenieria5);
        School ingenieria6 = new School(36, 8, 0, "Computación e Informática", "", R.drawable.compu512px, 9.9379246, -84.0541789, commentList);
        db.insert("School", ingenieria6);
        School ingenieria7 = new School(37, 8, 1, "Ingeniería de Biosistemas", "", R.drawable.biosistemas512px, 9.9379246, -84.0541789);
        db.insert("School", ingenieria7);
        School ingenieria8 = new School(38, 8, 1, "Ingeniería Topográfica", "", R.drawable.topo512px, 9.9376555, -84.0463562);
        db.insert("School", ingenieria8);

        School letras = new School(39, 9, 0, "Filología, Lingüistica y Literatura", "", R.drawable.filologia512px, 9.9384127, -84.0528675);
        db.insert("School", letras);
        School letras2 = new School(40, 9, 0, "Filosofía", "", R.drawable.filo512px, 9.9384127, -84.0528675);
        db.insert("School", letras2);
        School letras3 = new School(41, 9, 0, "Lenguas Modernas", "", R.drawable.lenguas512px, 9.9384127, -84.0528675);
        db.insert("School", letras3);

        School medicina = new School(42, 10, 0, "Enfermería", "", R.drawable.enfermeria512px, 9.9230603, -84.052573);
        db.insert("School", medicina);
        School medicina1 = new School(43, 10, 0, "Medicina", "", R.drawable.medicinas512px, 9.9387007, -84.0529767);
        db.insert("School", medicina1);
        School medicina2 = new School(44, 10, 0, "Nutrición", "", R.drawable.nutricion512px, 9.9390325, -84.0470175);
        db.insert("School", medicina2);
        School medicina3 = new School(45, 10, 0, "Tecnologías de la Salud", "", R.drawable.alimentos512px, 9.9384571, -84.0560422);
        db.insert("School", medicina3);
        School medicina4 = new School(46, 10, 0, "Salud Púbilca", "", R.drawable.saludpublica512px, 9.9388922, -84.0479994);
        db.insert("School", medicina4);

        School micro = new School(47, 11, 0, "Microbiología", "", R.drawable.micro512px, 9.9379464, -84.0514961);
        db.insert("School", micro);

        School odonto = new School(48, 12, 0, "Odontología", "", R.drawable.odonto512px, 9.943473, -84.0469052);
        db.insert("School", odonto);

        Log.d("places", "Schools were inserted in database.");
    }

    private void createLaboratory(){
        Laboratory labFisica = new Laboratory(0, 6, 0, "Laboratorio de fisica", "Este es el laboratorio de fisica", 1, 25, true, false, false, true);
        db.insert("Laboratory", labFisica);

        Laboratory labQuimica1 = new Laboratory(1, 9, 0, "Laboratorio 101", "Laboratorio de quimica", 1, 25, true, false, false, true);
        db.insert("Laboratory", labQuimica1);

        Laboratory labQuimica2 = new Laboratory(2, 9, 0, "Laboratorio 102", "Laboratorio de quimica", 1, 25, false, false, false, true);
        db.insert("Laboratory", labQuimica2);

        Laboratory labQuimica3 = new Laboratory(3, 9, 0, "Laboratorio 103", "Laboratorio de quimica", 2, 25, false, false, false, true);
        db.insert("Laboratory", labQuimica3);

        Laboratory labQuimica4 = new Laboratory(4, 9, 0, "Laboratorio 205", "Laboratorio de quimica", 2, 25, true, false, false, true);
        db.insert("Laboratory", labQuimica4);

        Laboratory labQuimica5 = new Laboratory(5, 9, 0, "Laboratorio 206", "Laboratorio de quimica", 2, 25, true, false, false, true);
        db.insert("Laboratory", labQuimica5);

        Laboratory labComputacion1 = new Laboratory(6, 36, 0, "Laboratorio 104", "Laboratorio de computacion", 1, 40, true, true, true, false);
        db.insert("Laboratory", labComputacion1);

        Laboratory labComputacion2 = new Laboratory(7, 36, 0, "Laboratorio 105", "Laboratorio de computacion", 1, 40, true, true, true, false);
        db.insert("Laboratory", labComputacion2);

        Laboratory labComputacion3 = new Laboratory(8, 36, 0, "Laboratorio 106", "Laboratorio de computacion", 1, 40, true, true, true, false);
        db.insert("Laboratory", labComputacion3);

        Laboratory labComputacion4 = new Laboratory(9, 36, 0, "Laboratorio 107", "Laboratorio de computacion", 1, 40, true, true, true, false);
        db.insert("Laboratory", labComputacion4);

        Laboratory labComputacion5 = new Laboratory(10, 36, 0, "Lab redes y oper", "Laboratorio exclusivo para estudiantes del " +
                "proyecto integador de redes y sitemas operativos", 3, 25, true, true, true, false);
        db.insert("Laboratory", labComputacion5);

        Laboratory labComputacion6 = new Laboratory(11, 36, 0, "Lab bases e inge", "Laboratorio de exclusivo para estudiantes del " +
                "proyecto integrador de sistemas operativos y bases de datos", 4, 40, true, true, true, false);
        db.insert("Laboratory", labComputacion6);

        Laboratory  labMicro1 = new Laboratory(12, 47, 0, "Laboratorio 110", "Laboratorio de Microbiología", 1, 30, false, false, false, true );
        db.insert("Laboratory", labMicro1);

        Laboratory  labMicro2 = new Laboratory(13, 47, 0, "Laboratorio 113", "Laboratorio de Microbiología", 1, 30, false, false, false, true );
        db.insert("Laboratory", labMicro2);

        Laboratory  labMicro3 = new Laboratory(14, 47, 0, "Laboratorio 115", "Laboratorio de Microbiología", 1, 20, false, false, false, true );
        db.insert("Laboratory", labMicro3);

        Laboratory  labMicro4 = new Laboratory(15, 47, 0, "Laboratorio 119", "Laboratorio de Microbiología", 1, 20, true, false, true, true );
        db.insert("Laboratory", labMicro4);

        Laboratory  labMicro5 = new Laboratory(16, 47, 0, "Laboratorio 121", "Laboratorio de Microbiología de Aguas", 1, 15, false, false, false, true );
        db.insert("Laboratory", labMicro5);

        Laboratory  labMicro6 = new Laboratory(17, 47, 0, "Laboratorio 122", "Laboratorio de Microbiología", 1, 25, false, false, false, true );
        db.insert("Laboratory", labMicro6);

        Laboratory  labMicro7 = new Laboratory(18, 47, 0, "Laboratorio 125", "Laboratorio para Maestría de Microbiología", 2, 25, true, false, true, true );
        db.insert("Laboratory", labMicro7);

        Laboratory  labMicro8 = new Laboratory(19, 47, 0, "Laboratorio 130", "Laboratorio para Diplomado de Microbiología", 2, 50, true, false, false, true );
        db.insert("Laboratory", labMicro8);

        Laboratory labLenguas1 = new Laboratory(20, 41, 0, "Laboratorio 201", "Laboratorio para Ingles Conversacional", 1, 30, true, true, true, false );
        db.insert("Laboratory", labLenguas1);

        Laboratory labLenguas2 = new Laboratory(21, 41, 0, "Laboratorio 210", "Laboratorio para Francés Conversacional", 1, 30, true, true, false, true);
        db.insert("Laboratory", labLenguas2);

        Laboratory labLenguas3 = new Laboratory(22, 41, 0, "Laboratorio 215", "Laboratorio para Literatura Inglesa", 1, 35, true, true, true, true);
        db.insert("Laboratory", labLenguas3);

        Laboratory labLenguas4 = new Laboratory(23, 41, 0, "Laboratorio 230", "Laboratorio para Literatura Francesa", 2, 30, false, false, false, true);
        db.insert("Laboratory", labLenguas4);

        Laboratory labLenguas5 = new Laboratory(24, 41, 0, "Laboratorio 243", "Laboratorio de Gramática en Inglés", 2, 20, true, true, true, true);
        db.insert("Laboratory", labLenguas5);

        Laboratory labLenguas6 = new Laboratory(25, 41, 0, "Laboratorio 243", "Laboratorio de Gramática en Francés", 2, 20, true, true, false, true);
        db.insert("Laboratory", labLenguas6);

    }

    //verificar más adelante, pues es una asocia por escuela
    private void createAsociation(){
        Asociation asocia0 = new Asociation(0, 36, 0, "Asocia de Info", "Asociación de estudiantes de la escuela de Computación e Informática", 2, 15, true, true);
        db.insert("Asociation", asocia0);

        Asociation asocia1 = new Asociation(1, 47, 0, "Asocia de Micro", "Asociación de estudiantes de la escuela de Microbiología", 1, 10, true, true);
        db.insert("Asociation", asocia1);

        Asociation asocia2 = new Asociation(2, 9, 0, "Asocia de Química", "Asociación de estudiantes de la escuela de Química", 3, 20, true, false);
        db.insert("Asociation", asocia2);

        Asociation asocia3 = new Asociation(3, 23, 0, "Asocia de Derecho", "Asociación de estudiantes de la escuela de Derecho", 1, 15, true, true);
        db.insert("Asociation", asocia3);

        Asociation asocia4 = new Asociation(4, 0, 0, "Asocia de Artes Dramáticas", "Asociación de estudiantes de la escuela de Artes Dramáticas", 1, 15, true, false);
        db.insert("Asociation", asocia4);
    }

    private void createBathrooms(){
        Bathroom br1 = new Bathroom(0, 0, 0, "Baño de Artes Dramáticas", "", 1, 3, true);
        db.insert("Bathroom", br1);

        Bathroom br2 = new Bathroom(1, 0, 0, "Baño de Artes Dramáticas", "", 2, 3, true);
        db.insert("Bathroom", br2);

        Bathroom br3 = new Bathroom(2, 0, 0, "Baño de Artes Dramáticas", "", 3, 5, true);
        db.insert("Bathroom", br3);

        Bathroom br4 = new Bathroom(3, 1, 0, "Baño de Artes Plásticas", "", 1, 4, true);
        db.insert("Bathroom", br4);

        Bathroom br5 = new Bathroom(4, 1, 0, "Baño de Artes Plásticas", "", 2, 3, false);
        db.insert("Bathroom", br5);

        Bathroom br6 = new Bathroom(5, 1, 0, "Baño de Artes Plásticas", "", 3, 5, false);
        db.insert("Bathroom", br6);

        Bathroom br7 = new Bathroom(6, 2, 0, "Baño de Artes Musicales", "", 1, 2, true);
        db.insert("Bathroom", br7);

        Bathroom br8 = new Bathroom(7, 2, 0, "Baño de Artes Musicales", "", 2, 4, false);
        db.insert("Bathroom", br8);

        Bathroom br9 = new Bathroom(8, 2, 0, "Baño de Artes Musicales", "", 3, 3, true);
        db.insert("Bathroom", br9);

        Bathroom br10 = new Bathroom(9, 3, 0, "Baño de Agronomía", "", 1, 2, true);
        db.insert("Bathroom", br10);

        Bathroom br11 = new Bathroom(10, 3, 0, "Baño de Agronomía", "", 2, 2, false);
        db.insert("Bathroom", br11);

        Bathroom br12 = new Bathroom(11, 4, 0, "Baño de Zootecnia", "", 1, 3, true);
        db.insert("Bathroom", br12);

        Bathroom br13 = new Bathroom(12, 4, 0, "Baño de Zootecnia", "", 2, 3, true);
        db.insert("Bathroom", br13);

        Bathroom br14 = new Bathroom(13, 5, 0, "Baño de Tecnología de Alimentos", "", 1, 2, true);
        db.insert("Bathroom", br14);

        Bathroom br15 = new Bathroom(14, 5, 0, "Baño de Tecnología de Alimentos", "", 2, 2, false);
        db.insert("Bathroom", br15);

        Bathroom br16 = new Bathroom(15, 5, 0, "Baño de Tecnología de Alimentos", "", 3, 2, false);
        db.insert("Bathroom", br16);

        Bathroom br17 = new Bathroom(16, 6, 0, "Baño de Física", "", 1, 3,true);
        db.insert("Bathroom", br17);

        Bathroom br18 = new Bathroom(17, 6, 0, "Baño de Física", "", 2, 5,true);
        db.insert("Bathroom", br18);

        Bathroom br19 = new Bathroom(18, 6, 0, "Baño de Física", "", 3, 2,false);
        db.insert("Bathroom", br19);

        Bathroom br20 = new Bathroom(19, 7, 0, "Baño de Geología", "", 1, 1,true);
        db.insert("Bathroom", br20);

        Bathroom br21 = new Bathroom(20, 7, 0, "Baño de Geología", "", 2, 3, true);
        db.insert("Bathroom", br21);

        Bathroom br22 = new Bathroom(21, 8, 0, "Baño de Matemática", "", 1, 2,true);
        db.insert("Bathroom", br22);

        Bathroom br23 = new Bathroom(22, 8, 0, "Baño de Matemática", "", 2, 3,false);
        db.insert("Bathroom", br23);


    }


    //Creación de las bibliotecas en la base de datos
    private void createLibrary() {
        Library tinoco = new Library(0,  "Luis Demetrio Tinoco Castro", "", R.drawable.libros, 9.936151, -84.052704, "9:00 - 16:00", true);
        db.insert("Library", tinoco);

        Library carlos = new Library(1, "Carlos Monge Alfaro", "", R.drawable.libros, 9.936166, -84.051072, "9:00 - 16:00", true);
        db.insert("Library", carlos);

        Library salud = new Library(2, "Biblioteca Ciencias de la Salud", "", R.drawable.libros,9.938715, -84.051115, "9:00 - 16:00", true);
        db.insert("Library", salud);

        Library agro = new Library(3, "Biblioteca Ciencias Agroalimentarias", "", R.drawable.libros, 9.939773, -84.047757, "9:00 - 16:00", true);
        db.insert("Library", agro);

        Library educa = new Library(4, "Biblioteca Facultad de Educación", "", R.drawable.libros, 9.936271, -84.048458, "9:00 - 16:00", true);
        db.insert("Library", educa);

        Library derecho = new Library(4, "Biblioteca Facultad de Derecho", "", R.drawable.libros, 9.936271, -84.048458, "9:00 - 16:00", true);
        db.insert("Library", derecho);
    }

    //Creación de las oficinas en la base de datos
    private void createOffice() {
        Office oficina1 = new Office(0,  "Oficina de Generales", "", R.drawable.administracion, 9.936313, -84.050436, "7:00-16:00", "2211-0000");
        db.insert("Office", oficina1);

        Office oficina2 = new Office(1,  "OCCI", "", R.drawable.administracion, 9.938030, -84.052168, "7:00-16:00", "2211-0000");
        db.insert("Office", oficina2);

        Office ori = new Office(2, "Oficinas de Registro e Información", "", R.drawable.administracion, 9.937079, -84.051010, "7:00-16:00", "2211-0000");
        db.insert("Office", ori);

        Office becas = new Office(3, "Oficina de Becas y Atención Socioeconomicas", "", R.drawable.administracion, 9.935737, -84.054143, "7:00-16:00", "2211-0000");
        db.insert("Office", becas);

        Office orientacion = new Office(4, "Oficina de Orientación Vocacional", "", R.drawable.administracion,9.937486, -84.053076, "7:00-16:00", "2211-0000");
        db.insert("Office", orientacion);

        Office juridica = new Office(5, "Oficina Jurídica de la UCR","",R.drawable.administracion,9.933635, -84.053301, "7:00-16:00", "2211-0000");
        db.insert("Office", juridica);

        Office ambiente = new Office(6, "Unidad de Gestión Ambiental", "", R.drawable.administracion, 9.933984, -84.052995, "7:00-16:00", "2211-0000");
        db.insert("Office", ambiente);

        Office contra = new Office(7,  "Contraloría Universitaria", "", R.drawable.administracion, 9.935174, -84.054931, "7:00-16:00", "2211-0000");
        db.insert("Office", contra);

        Office obas = new Office(8,  "Oficinas de Bienestar y Salud (OBAS)", "", R.drawable.administracion,9.935477, -84.052358, "7:00-16:00", "2211-0000");
        db.insert("Office", obas);

    }

    private void createSoda() {
        Soda farma = new Soda(0,  "Soda de Farmacia", "", R.drawable.sandwich, 9.939096, -84.049535, true,"7:00 am - 7:00pm", "Melcochon: 2x1", false);
        db.insert("Soda", farma);

        Soda generales = new Soda(1,  "Soda de Generales", "", R.drawable.sandwich, 9.936242, -84.050514, false, "5:00 am - 5:00pm", "Cafe con leche: 5x1", true);
        db.insert("Soda", generales);

        Soda lucy = new Soda(2,  "Soda Lucy", "Gran variedad de almuerzos todos los días, vengan a probar nuestra cuchara", R.drawable.sandwich, 9.934352, -84.052875, true, "7:00 am - 7:00pm", "Chicky's gratis", false);
        db.insert("Soda", lucy);

        Soda derecho = new Soda(3,  "Soda de Derecho", "", R.drawable.sandwich, 9.936421, -84.054012, false, "7:00 am - 3:00pm",  "Rice and Beans: 2x1", true);
        db.insert("Soda", derecho);

        Soda educa = new Soda(4,  "Soda de Educación", "", R.drawable.sandwich, 9.936302, -84.048890, true, "9:00 am - 3:30pm",  "PanBon: 2x1", true);
        db.insert("Soda", educa);

        Soda comedor = new Soda(5,  "Comedor Estudiantil", "", R.drawable.sandwich, 9.938669, -84.053600, true, "10:00 am - 2:00pm",  "Lasagna: 3000", false);
        db.insert("Soda", comedor);

        Soda sodau = new Soda(6,  "Soda U", "", R.drawable.sandwich, 9.934635, -84.051040, true, "7:00 am - 7:00pm",  "Empanadas a mil", true);
        db.insert("Soda", sodau);

        Soda agro = new Soda(7,  "Soda de Agronomía", "", R.drawable.sandwich, 9.939332, -84.048473, true,"7:00 am - 7:00pm", "Elotes frescos a 500", false);
        db.insert("Soda", agro);

    }

    private void createPhotocopier() {
        Photocopier exactas = new Photocopier(0,  "CopiasExactas", "", R.drawable.impresora, 9.943473, -84.0469052, true, "9:00-19:30", "₡15 B/N \n₡25 Color");
        db.insert("Photocopier", exactas);

        Photocopier millenium = new Photocopier(1,  "Impresiones Millenium", "", R.drawable.impresora, 9.935302, -84.050955, true, "9:00-19:30", "₡15 B/N \n₡25 Color");
        db.insert("Photocopier", millenium);

        Photocopier misCopias = new Photocopier(2,  "Mis Copias", "", R.drawable.impresora, 9.934919, -84.051658, true, "9:00-19:30", "₡15 B/N \n₡25 Color");
        db.insert("Photocopier", misCopias);

        Photocopier kike = new Photocopier(3,  "Copias KIKE", "", R.drawable.impresora,9.935144, -84.051384, true, "9:00-19:30", "₡15 B/N \n₡25 Color");
        db.insert("Photocopier", kike);

        Photocopier derecho = new Photocopier(4,  "Centro de Fotocopiado Derecho UCR", "", R.drawable.impresora, 9.936306, -84.054077, true, "9:00-19:30", "₡15 B/N \n₡25 Color");
        db.insert("Photocopier", derecho);

        Photocopier activas = new Photocopier(5,  "Copias Activas", "", R.drawable.impresora, 9.932461, -84.051043, true, "9:00-19:30", "₡15 B/N \n₡25 Color");
        db.insert("Photocopier", activas);

        Photocopier sui = new Photocopier(6,  "Fotocopiadora Sui Generis", "", R.drawable.impresora, 9.934026, -84.050932, true, "9:00-19:30", "₡15 B/N \n₡25 Color");
        db.insert("Photocopier", sui);

        Photocopier logo = new Photocopier(7,  "Logo Print", "", R.drawable.impresora,9.935319, -84.050932, true, "9:00-19:30", "₡15 B/N \n₡25 Color");
        db.insert("Photocopier", logo);

        Photocopier express = new Photocopier(8,  "Kopias Express", "", R.drawable.impresora, 9.935945, -84.057178, true, "9:00-19:30", "₡15 B/N \n₡25 Color");
        db.insert("Photocopier", express);

        Photocopier meju = new Photocopier(9,  "MejuCopi", "", R.drawable.impresora, 9.932985, -84.051620, true, "9:00-19:30", "₡15 B/N \n₡25 Color");
        db.insert("Photocopier", meju);

        Photocopier copicentro = new Photocopier(10, "Copicentro", "", R.drawable.impresora, 9.933344, -84.068378, true, "9:00-19:30", "₡15 B/N \n₡25 Color");
        db.insert("Photocopier", copicentro);
    }

}
