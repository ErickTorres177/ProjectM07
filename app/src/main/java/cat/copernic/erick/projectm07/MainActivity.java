package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText user;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPass);
        //final EditText pass = (EditText) findViewById(R.id.etPass);
        Button btnIngresar=  findViewById(R.id.btnIniciarSesion);
        Button btnRegistrar=  findViewById(R.id.btnRegistrar);

btnIngresar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String usuario = user.getText().toString();
        String password = pass.getText().toString();


        SharedPreferences preferencias = getSharedPreferences("misPref",MODE_PRIVATE);

        String usuarioDetail =  preferencias.getString(usuario + password + "data", "Usuario o Contraseña incorrectas.");
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("display" , usuarioDetail);

        Intent diplayPantallas = new Intent (MainActivity.this, MainAplication.class);

        startActivity(diplayPantallas);


    }
});


    }




    public void abrirRegistro(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
