package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EliminarRuta extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView tvSinrutasEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_ruta);

        tvSinrutasEliminar = findViewById(R.id.tvSinRutasEliminarRuta);
        mRecyclerView = findViewById(R.id.recyclerViewEliminarRuta);
        new FireBaseDHEliminarRuta().leerRutasAEliminar(new FireBaseDHEliminarRuta.DataStatus() {
            @Override
            public void DataIsLoaded(final List<Rutas> rutas, final List<String> keys) {
                //new RecyclerView_Config().setConfig(mRecyclerView,HomeFragment.this(),rutas,keys);


                /*if (rutas.size() <= 0) {

                    tvSinrutasEliminar.setVisibility(View.VISIBLE);
                    String toastSinRutas = EliminarRuta.this.getResources().getString(R.string.sinRutas);
                    tvSinrutasEliminar.setText(toastSinRutas);
                    Toast.makeText(EliminarRuta.this, toastSinRutas,
                            Toast.LENGTH_SHORT).show();

                } else {*/
                    //tvSinrutasEliminar.setVisibility(View.GONE);
                    new RecyclerEliminarRuta_Config().setConfig(mRecyclerView, EliminarRuta.this, rutas, keys);

                //}

                //--
                //new RecyclerEliminarRuta_Config().setConfig(mRecyclerView, EliminarRuta.this, rutas, keys);
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
        //

    }


}
