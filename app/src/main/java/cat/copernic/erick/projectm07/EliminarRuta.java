package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class EliminarRuta extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_ruta);

        mRecyclerView = findViewById(R.id.recyclerViewEliminarRuta);
        new FireBaseDHEliminarRuta().leerRutasAEliminar(new FireBaseDHEliminarRuta.DataStatus() {
            @Override
            public void DataIsLoaded(List<Rutas> rutas, List<String> keys) {
                //new RecyclerView_Config().setConfig(mRecyclerView,HomeFragment.this(),rutas,keys);
                new RecyclerEliminarRuta_Config().setConfig(mRecyclerView, EliminarRuta.this, rutas, keys);
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
