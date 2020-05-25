package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Faculty extends GeneralData {// implements Comparable<Faculty>{

    int id;
    String facultyName;
    String description;
    int image;

    public Faculty(){}

    public Faculty(String facultyName, String description){
        this.facultyName = facultyName;
        this.description = description;
    }

    public Faculty(int id, String facultyName, String description){
        this.id = id;
        this.facultyName = facultyName;
        this.description = description;
    }

    public Faculty(int id, String facultyName, String description, int image) {
        this.id = id;
        this.facultyName = facultyName;
        this.description = description;
        this.image = image;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
