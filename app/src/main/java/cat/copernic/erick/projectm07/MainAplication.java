package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainAplication extends AppCompatActivity {
    private TextView tvMostrar;
    private SharedPreferences pPreferences;
    //private SharedPreferences.Editor pEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aplication);

        tvMostrar = findViewById(R.id.tvMuestraUser);

        pPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        //pEditor = pPreferences.edit();

        mostrarDatos();

        //SharedPreferences preferences= getSharedPreferences("misPreferencias", MODE_PRIVATE);
        //String display = preferences.getString("display", "");

        //TextView displayInfo = findViewById(R.id.tvMuestraUser);
        //displayInfo.setText(display);
    }

    public void mostrarDatos(){
        String userP2 = pPreferences.getString("NuevoUser", "vacio");
        String passP2= pPreferences.getString("NuevaPasswd", "vacio");
        String edadP2= pPreferences.getString("NuevaEdad", "vacio");

        String datos = "Usuario: " + userP2 +
                        "\nPassword: " + passP2 +
                        "\nEdad: " + edadP2;
        tvMostrar.setText(datos);
    }

    public void cerrarSesion(View view) {
        this.finish();
    }
}
