package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {
    EditText usuario;
    EditText passwd;
    EditText edad;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usuario = findViewById(R.id.txtNombre);
        passwd = findViewById(R.id.txtPasswd);
        edad = findViewById(R.id.txtEdad);
    }

    public void handleRegresar(View view) { this.finish(); }

    public void handleRegistrar(View view) {
        if (usuario.getText().toString().equals("")) {
            Toast.makeText(this, "Introduzca nombre del usuario", Toast.LENGTH_SHORT).show();
        } else if (passwd.getText().toString().equals("")) {
            Toast.makeText(this, "Introduzca una contraseña", Toast.LENGTH_SHORT).show();
        } else if (edad.getText().toString().equals("")) {
            Toast.makeText(this, "Introduzca la edad", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sharedPrefs = getSharedPreferences("Login", MODE_PRIVATE);
            String nuevoUser = usuario.getText().toString();
            String nuevaPasswd = passwd.getText().toString();
            String nuevaEdad = edad.getText().toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();

            editor.putString("NuevoUser", nuevoUser);
            editor.putString("NuevaPasswd", nuevaPasswd);
            editor.putString("NuevaEdad", nuevaEdad);
            editor.apply();

            this.finish();
        }
    }
}
