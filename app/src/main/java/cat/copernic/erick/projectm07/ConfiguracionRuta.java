package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class ConfiguracionRuta extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_ruta);


        mRecyclerView = findViewById(R.id.recyclerViewConfiguracionRuta);
        new FireBaseDHConfiguracionRuta().leerRutasAConfigurar(new FireBaseDHConfiguracionRuta.DataStatus() {
            @Override
            public void DataIsLoaded(List<Rutas> rutas, List<String> keys) {
                //new RecyclerView_Config().setConfig(mRecyclerView,HomeFragment.this(),rutas,keys);
                new RecyclerConfiguracionRuta_Config().setConfig(mRecyclerView,ConfiguracionRuta.this,rutas,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdate() {

            }

            @Override
            public void DataIsDelete() {

            }
        });


    }
}
