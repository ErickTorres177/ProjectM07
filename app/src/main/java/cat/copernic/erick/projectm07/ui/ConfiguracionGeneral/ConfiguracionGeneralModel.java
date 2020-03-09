package cat.copernic.erick.projectm07.ui.ConfiguracionGeneral;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfiguracionGeneralModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConfiguracionGeneralModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}