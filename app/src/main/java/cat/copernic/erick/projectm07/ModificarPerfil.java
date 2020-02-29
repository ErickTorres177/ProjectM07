package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ModificarPerfil extends AppCompatActivity {

    private Spinner spinnerS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil);

        //Spiner
        spinnerS = findViewById(R.id.spinnerTipSexo);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,R.array.tipo_sexo,
                android.R.layout.simple_spinner_item);
        spinnerS.setAdapter(adapterSpinner);
    }

    public void handleRegresarPerfil(View view) {
        finish();
    }
}
