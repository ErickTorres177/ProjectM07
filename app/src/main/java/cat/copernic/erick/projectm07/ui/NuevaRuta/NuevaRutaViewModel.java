package cat.copernic.erick.projectm07.ui.NuevaRuta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NuevaRutaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NuevaRutaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("AÑADIR RUTA");
    }

    public LiveData<String> getText() {
        return mText;
    }
}