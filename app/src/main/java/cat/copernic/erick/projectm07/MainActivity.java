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

        //USUARIO -> SHARED PREFENCES
        user = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPass);
        preferencias = getSharedPreferences("Login", Context.MODE_PRIVATE);



    }

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



    public void iniciarRegistro(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
