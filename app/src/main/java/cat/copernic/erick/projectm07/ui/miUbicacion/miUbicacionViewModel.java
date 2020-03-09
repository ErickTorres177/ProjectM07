package cat.copernic.erick.projectm07.ui.miUbicacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class miUbicacionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public miUbicacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}