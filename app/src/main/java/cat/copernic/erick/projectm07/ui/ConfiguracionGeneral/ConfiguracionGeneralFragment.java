package cat.copernic.erick.projectm07.ui.ConfiguracionGeneral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import cat.copernic.erick.projectm07.R;

public class ConfiguracionGeneralFragment extends Fragment {

    private ConfiguracionGeneralModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(ConfiguracionGeneralModel.class);
        View root = inflater.inflate(R.layout.fragment_configuracion_general, container, false);
        return root;
    }
}