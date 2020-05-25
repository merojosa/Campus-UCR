package cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.foro_general.Repositories.RespuestaRepository;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

public class RespuestaViewModel extends AndroidViewModel {
    private RespuestaRepository mRepository;

    public RespuestaViewModel(Application application) {
        super(application);

        // MODIFICAR PARA QUE USE SOLO 1 BASE DE DATOS
        mRepository = new RespuestaRepository(application);
    }

    public LiveData<List<Respuesta>> getRespuestasDePregunta(int id) {
        return mRepository.getRespuestasDePregunta(id);
    }

    public void insert(Respuesta respuesta) {
        mRepository.insert(respuesta);
    }
}
