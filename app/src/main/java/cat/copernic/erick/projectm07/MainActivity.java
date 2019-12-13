package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText user;
    private EditText pass;

    private SharedPreferences preferencias;
    //private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPass);

        preferencias = getSharedPreferences("Login", Context.MODE_PRIVATE);
    }
        // Parte del anterior onCreate

        /*editor = preferencias.edit();

        if (user.getText().toString().equals("") || pass.getText().toString().equals("")) {
            Toast.makeText(this, "Campos vacios", Toast.LENGTH_SHORT);
        } else {
            if (preferencias.contains("NuevoUser")){
                Intent intent =  new Intent(this, MainAplication.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT);
            }
        }
        */
    /*
    btnIngresar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String usuario = user.getText().toString();
        String password = pass.getText().toString();


        preferencias = getSharedPreferences("misPref",MODE_PRIVATE);

        String usuarioDetail =  preferencias.getString(usuario + password + "data", "Usuario o Contraseña incorrectas.");
        editor = preferencias.edit();
        editor.putString("display" , usuarioDetail);

        Intent diplayPantallas = new Intent (MainActivity.this, MainAplication.class);

        startActivity(diplayPantallas);


        }
        });


    }
    */
    public void inciarSesion(View view) {

        if (user.getText().toString().equals("") || pass.getText().toString().equals("")) {
            Toast.makeText(this, "Campos vacios", Toast.LENGTH_SHORT).show();
        } else {
            if (preferencias.getString("NuevaEdad", "vacio").equals("vacio")) {
                Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, MainAplication.class);
                startActivity(intent);
            }
        }
    }
        /*
         String usuario = user.getText().toString();
         String password = pass.getText().toString();

         //Guardamos los datos del usuario en el archivo de SHARED P.
            editor.putString("userP", usuario);
            editor.putString("passP", password);
            //Confirmamos
        editor.commit();
        System.out.println("Tu usuario es: " + usuario + "\nTu contraseña es: " + password);

        //Le mandamos a otra actividad
        Intent intent = new Intent(this, MainAplication.class);
        startActivity(intent);
        finish();

    }
    */


    public void iniciarRegistro(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
