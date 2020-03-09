package cat.copernic.erick.projectm07.ui.ConfiguracionRutas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import cat.copernic.erick.projectm07.R;

public class ConfiguracionRutasFragment extends Fragment {

    private ConfiguracionRutasViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(ConfiguracionRutasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_configuracion_rutas, container, false);



        return root;
    }
}