package cat.copernic.erick.projectm07.ui.Rutas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RutasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RutasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("AÑADIR RUTA");
    }

    public LiveData<String> getText() {
        return mText;
    }
}