package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class prueba extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
    }

    public void abrirMapaUbicacionActual2(View view) {
        Intent intent = new Intent(prueba.this, MapaUbicacionActual.class);
        startActivity(intent);
    }
}
