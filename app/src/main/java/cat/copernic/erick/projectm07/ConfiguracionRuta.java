package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ConfiguracionRuta extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView tvSinrutasConfigurar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_ruta);


        tvSinrutasConfigurar = findViewById(R.id.tvSinRutasConfiguracion);
        mRecyclerView = findViewById(R.id.recyclerViewConfiguracionRuta);
        new FireBaseDHConfiguracionRuta().leerRutasAConfigurar(new FireBaseDHConfiguracionRuta.DataStatus() {
            @Override
            public void DataIsLoaded(List<Rutas> rutas, List<String> keys) {
                //new RecyclerView_Config().setConfig(mRecyclerView,HomeFragment.this(),rutas,keys);



                if (rutas.size() <= 0) {

                    tvSinrutasConfigurar.setVisibility(View.VISIBLE);
                    String toastSinRutas = ConfiguracionRuta.this.getResources().getString(R.string.sinRutas);
                    tvSinrutasConfigurar.setText(toastSinRutas);
                    Toast.makeText(ConfiguracionRuta.this, toastSinRutas,
                            Toast.LENGTH_SHORT).show();
                } else {
                    tvSinrutasConfigurar.setVisibility(View.GONE);
                    new RecyclerConfiguracionRuta_Config().setConfig(mRecyclerView,ConfiguracionRuta.this,rutas,keys);
                }


                //new RecyclerConfiguracionRuta_Config().setConfig(mRecyclerView,ConfiguracionRuta.this,rutas,keys);
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
