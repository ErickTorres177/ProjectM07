package cat.copernic.erick.projectm07.ui.ConfiguracionRutas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfiguracionRutasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConfiguracionRutasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}