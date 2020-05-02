package cr.ac.ucr.ecci.cql.campus20;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

/*
public class School extends GeneralData {

    String schoolName;
    String schoolDescription;

    public School(){} //Constructor normal
    public School(String schoolName, String schoolDescription){
        this.schoolName = schoolName;
        this.schoolDescription = schoolDescription;
    }

    @Override
    public String getTitle() { return schoolName; }
    @Override
    public String getDescription() { return schoolDescription; }

    //public List<School> getSchoolList(Context context, String faculty){


   // }
    // Metodo para obtener las facultades una vez esté implementada la BD
//    public List<Faculty> getFacultiesList(Context context){
//        List<Faculty> list = new ArrayList<>();
//        DataAccess dataAccess = DataAccess.getInstance(context);
//        dataAccess.open();
//        Cursor cursor = dataAccess.getList("Faculty");
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            list.add(new Faculty(cursor.getString(0),... // Obtener atributos
//            ));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        DataAccess.close();
//        Collections.sort(list);
//        return list;
    //   }


    /*
     List<School> list = new ArrayList<>();

        //Se necesita hacer una lista de escuelas por cada facultad
        HashMap<String, String[]> schools = new HashMap<String, String[]>();

        String[] Artes = {"Artes Dramáticas", "Artes Plásticas", "Artes Musicales"};
        schools.put("Artes", Artes);

        String[] Ciencias_Agroalimentarias;
        String[] Ciencias_Básicas;
        String[] Ciencias_Económicas;
        String[] Ciencias_Sociales;
        String[] Derecho;
        String[] Educación;
        String[] Farmacia;
        String[] Ingeniería;
        String[] Letras = {"Filología, Linguistica y Leteratura", "Filosofía", "Lenguas Modernas"};
        schools.put("Letras", Letras);
        String[] Medicina;
        String[] Microbiología;
        String[] Odontología;

        String from;
        String[] selected;
        if (schools.containsKey(faculty)){
            from = faculty;
            selected = schools.get(from);

            for(String schoolSelected:selected){
                list.add(new School(schoolSelected, "" ));

            }
        } else {
            return list;
        }
        return list;
     */



//}
