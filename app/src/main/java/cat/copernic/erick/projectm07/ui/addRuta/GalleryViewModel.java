package cat.copernic.erick.projectm07.ui.addRuta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("AÑADIR RUTA");
    }

    public LiveData<String> getText() {
        return mText;
    }
}