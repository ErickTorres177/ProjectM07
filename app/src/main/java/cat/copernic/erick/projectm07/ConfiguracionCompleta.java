package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ConfiguracionCompleta extends AppCompatActivity {

    private String key;
    private String idRuta;
    private String usuarioR;
    private String nombreR;
    private String descripcionR;
    private String rutaR;
    private String paisR;
    private String ciudadR;

    private TextView tvIdR, tvUserT,tvNombreR, tvDescripcionR, tvRutaR, tvPaisR, tvCiudadR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_completa);


        key = getIntent().getStringExtra("key");
        idRuta = getIntent().getStringExtra("idRuta");
        usuarioR = getIntent().getStringExtra("usuarioRuta");
        nombreR = getIntent().getStringExtra("nombreRuta");
        descripcionR = getIntent().getStringExtra("descripcionRuta");
        rutaR = getIntent().getStringExtra("ruta");
        paisR = getIntent().getStringExtra("paisRuta");
        ciudadR = getIntent().getStringExtra("ciudadRuta");



        /*tvIdR = findViewById(R.id.tvConfiguracionRutaC_);
        tvIdR.setText(idRuta);
        tvUserT = findViewById(R.id.tvConfiguracionRutaC_);
        tvUserT.setText(usuarioR);*/
        tvNombreR = findViewById(R.id.tvConfiguracionRutaC_nombre);
        tvNombreR.setText(nombreR);
        tvDescripcionR = findViewById(R.id.tvConfiguracionRutaC_descripcion);
        tvDescripcionR.setText(descripcionR);
        tvRutaR = findViewById(R.id.tvConfiguracionRutaC_ruta);
        tvRutaR.setText(rutaR);
        tvPaisR = findViewById(R.id.tvConfiguracionRutaC_pais);
        tvPaisR.setText(paisR);
        tvCiudadR = findViewById(R.id.tvConfiguracionRutaC_ciudad);
        tvCiudadR.setText(ciudadR);

    }

    public void guardarModificacionRuta(View view) {
    }

    public void volverRutasConfiguracion(View view) {
        finish();
    }

}
