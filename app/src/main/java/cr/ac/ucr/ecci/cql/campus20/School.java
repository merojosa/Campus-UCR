package cr.ac.ucr.ecci.cql.campus20;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<School> getSchoolList(Context context ,String Faculty){
        List<School> list = new ArrayList<>();

        //Se necesita hacer una lista de escuelas por cada facultad

        String[] Artes = {"Artes Dramáticas", "Artes Plásticas", "Artes Musicales"};
        String[] Ciencias_Agroalimentarias;
        String[] Ciencias_Básicas;
        String[] Ciencias_Económicas;
        String[] Ciencias_Sociales;
        String[] Derecho;
        String[] Educación;
        String[] Farmacia;
        String[] Ingeniería;
        String[] Letras = {"Filología, Linguistica y Leteratura", "Filosofía", "Lenguas Modernas"};
        String[] Medicina;
        String[] Microbiología;
        String[] Odontología;


        return list;

    }



}
