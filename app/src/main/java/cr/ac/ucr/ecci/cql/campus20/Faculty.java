package cr.ac.ucr.ecci.cql.campus20;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Faculty extends GeneralData {// implements Comparable<Faculty>{

    String facultyName;
    String description;

    public Faculty(){}

    public Faculty(String facultyName, String description){
        this.facultyName = facultyName;
        this.description = description;
    }

    // Hay que implementar getter y setters de los atributos que tenga Faculty en la BD


    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String getTitle() {
        return facultyName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    // Únicamente para efectos de prueba
    public List<Faculty> getFacultiesList(Context context) {
        List<Faculty> list = new ArrayList<>();
        String[] Faculties = {"Artes", "Ciencias Agroalimentarias", "Ciencias Básicas", "Ciencias Económicas", "Ciencias Sociales", "Derecho",
                            "Educación", "Farmacia","Ingeniería", "Letras", "Medicina", "Microbiología", "Odontología"};

        for (String faculty:Faculties) {
            list.add(new Faculty(faculty, ""));
        }
        return list;
    }

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
}
