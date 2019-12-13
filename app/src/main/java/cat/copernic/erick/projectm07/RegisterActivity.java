package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cat.copernic.erick.projectm07.R;

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

    public void handleRegresar(View view) {
        this.finish();
    }

    public void handleRegistrar(View view) {
        if (usuario.getText().toString().equals("") || passwd.getText().toString().equals("") ||
                edad.getText().toString().equals("") ) {
            Toast.makeText(this, "Campos vacios", Toast.LENGTH_SHORT);
        } else {
            SharedPreferences sharedPrefs = getSharedPreferences("Login", MODE_PRIVATE);
            String nuevoUser = usuario.getText().toString();
            String nuevaPasswd = passwd.getText().toString();
            String nuevaEdad = edad.getText().toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();

            editor.putString("NuevoUser", nuevoUser);
            editor.putString("NuevaPasswd", nuevaPasswd);
            editor.putString("NuevaEdad", nuevaEdad);
            editor.commit();

            this.finish();
        }
    }
}
