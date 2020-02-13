package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.nio.file.Files;

public class LoginActivity extends AppCompatActivity {
    // Variables donde se almacenarán los datos de las SharedPreferences
    private EditText user;
    private EditText pass;
    private ImageButton btn_maps, btn_web, btn_insta, btn_gmail;

    // Variable de la cual se obtendran los datos de las SharedPreferences
    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPass);

        preferencias = getSharedPreferences("Login", Context.MODE_PRIVATE);

        btn_maps = findViewById(R.id.imgBtn_Gmaps);
        btn_web = findViewById(R.id.imgBtn_web);
        btn_insta = findViewById(R.id.imgBtn_instagram);
        btn_gmail = findViewById(R.id.imgBtn_instagram);
    }

    /**
     * Método que se encarga de lanzar la activity principal después de realizar la comprovación
     * de los datos de usuario
     *
     * @param view
     */
    public void inciarSesion(View view) {
        String nombre = preferencias.getString("NuevoUser", "vacio");
        String passwd = preferencias.getString("NuevaPasswd", "vacio");

        // Comprovación de que en las SharedPreferences hay algun usuario dado de alta.
        if (nombre.equals("vacio") || passwd.equals("vacio")) {
            Toast.makeText(this, "No hay ningun usuario registrado", Toast.LENGTH_SHORT).show();
        } else {
            // Comprovación para ver si los campos usuario o contraseña se encuentran vacios.
            if (user.getText().toString().equals("")) {
                Toast.makeText(this, "Introduce el nombre de usuario", Toast.LENGTH_SHORT).show();
            } else if (pass.getText().toString().equals("")) {
                Toast.makeText(this, "Introduce la contraseña", Toast.LENGTH_SHORT).show();
            } else {
                // En caso de que no esten vacios, se pasa a comprovar que los datos introducidos sean
                // los de las SharedPreferences.
                if (!user.getText().toString().equals(nombre)) {
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                } else if (!pass.getText().toString().equals(passwd)) {
                    Toast.makeText(this, "Contraseña erronea", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), NavegationDrawer.class);
                    startActivity(intent);
                }
            }
        }
    }

    /**
     * Método encargado de iniciar la activity para Registrar un usuario, cuando el botón
     * "Regístrate" es pulsado.
     *
     * @param view
     */
    public void iniciarRegistro(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void mostrarUbicacion(View view) {
        // TO DO
    }

    public void abrirPagina(View view) {
        // TO DO
    }

    public void abrirInstagram(View view) {
        // TO DO
    }

    public void abrirGmail(View view) {
        // TO DO
    }

}
