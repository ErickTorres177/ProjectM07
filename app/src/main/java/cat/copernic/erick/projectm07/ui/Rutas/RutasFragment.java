package cat.copernic.erick.projectm07.ui.Rutas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;

import cat.copernic.erick.projectm07.ConfiguracionRuta;
import cat.copernic.erick.projectm07.EliminarRuta;
import cat.copernic.erick.projectm07.NuevaRuta;
import cat.copernic.erick.projectm07.R;

public class RutasFragment extends Fragment {

    private RutasViewModel rutasViewModel;

    Button btnNuevaR, btnEliminarR, btnConfiguracioR, btnCancela;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rutasViewModel =
                ViewModelProviders.of(this).get(RutasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rutas, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
       /*  galleryViewModel.getText().observe(this, new Observer<String>() {
           @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        //inicializacion de las variables --
        View view = inflater.inflate(R.layout.fragment_rutas, container, false);
        btnNuevaR = view.findViewById(R.id.btnNuevaRuta);
        btnConfiguracioR = view.findViewById(R.id.btnConfiguracionRuta);
        btnEliminarR = view.findViewById(R.id.btnEliminarRuta);
        btnCancela = view.findViewById(R.id.btnCancelarRuta);

        btnNuevaR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NuevaRuta.class);
                intent.putExtra("some", "some data");
                startActivity(intent);
            }
        });


        btnConfiguracioR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConfiguracionRuta.class);
                intent.putExtra("some", "some data");
                startActivity(intent);
            }
        });

        btnEliminarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EliminarRuta.class);
                intent.putExtra("some", "some data");
                startActivity(intent);
            }
        });

        btnCancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });




        return view;
    }
}