package cat.copernic.erick.projectm07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.nio.file.Files;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    // Variables donde se almacenarán los datos de las SharedPreferences
    private EditText user, pass;
    private ImageButton btn_maps, btn_web, btn_insta, btn_gmail;

    // Variable de la cual se obtendran los datos de las SharedPreferences
    //private SharedPreferences preferencias;

    //FIRE BASE
    Button btnLogin;
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPass);

        // preferencias = getSharedPreferences("Login", Context.MODE_PRIVATE);

        //BOTONES DE REDIRECCION (SIN MAS)
        btn_maps = findViewById(R.id.imgBtn_Gmaps);
        btn_web = findViewById(R.id.imgBtn_web);
        btn_insta = findViewById(R.id.imgBtn_instagram);
        btn_gmail = findViewById(R.id.imgBtn_instagram);

        //FIRE BASE

        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();


        btnLogin = findViewById(R.id.btnIniciarSesion);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(user.getText().toString(), pass.getText().toString());
                //Con esto el usuario solo podra clickar una vez
                //btnLogin.setEnabled(false);
                //btnNuevo.setEnabled(false);
            }
        });


    }

    public void loginUser(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(LoginActivity.this, "Hola usuario: " + email,
                                    Toast.LENGTH_SHORT).show();
                            //Start intent
                            Intent intent = new Intent(getApplicationContext(), NavegationDrawer.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            //btnLogin.setEnabled(true);
                            //btnNuevo.setEnabled(true);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {


        } else {

        }
    }

    /**
     * Método que se encarga de lanzar la activity principal después de realizar la comprovación
     * de los datos de usuario
     *
     * @param view
     */
    /*
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
    }*/

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

    /**
     * Abre la aplicación de google maps mostrando las coordenadas del Nicolau
     *
     * @param view
     */
    public void mostrarUbicacion(View view) {
        Uri intentUri = Uri.parse("geo:41.569940,1.996553?z=16&q=41.569940,1.996553(INS+Nicolau+Copernic)");
        Intent gMaps = new Intent(Intent.ACTION_VIEW, intentUri);

        //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitud, longitud);
        //Intent maps = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(gMaps);
    }

    /**
     * Abre la pagina web de Dinahosting en el navegador
     *
     * @param view
     */
    public void abrirPagina(View view) {
        String url = "http://www.findyourway.cat/";
        Intent web = new Intent(Intent.ACTION_VIEW);
        web.setData(Uri.parse(url));
        startActivity(web);
    }

    /**
     * Comprueba si se tiene la aplicación isntagram instalada
     * <p>
     * SI --> Abre el perfil de usuaruio en la app
     * NO --> Abre el perfil de usuario en el navegador
     *
     * @param view
     */
    public void abrirInstagram(View view) {
        Uri uri = Uri.parse("https://www.instagram.com/findyourway_fow/?hl=es");
        Intent insta = new Intent(Intent.ACTION_VIEW, uri);

        insta.setPackage("com.instagram.android");

        try {
            startActivity(insta);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    /**
     * Se encarga de abrir la aplicación del gmail especificando ya el correo de atención
     *
     * @param view
     */
    public void abrirGmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", "findyourwayFOW@gmail.com", null));
        startActivity(emailIntent);
    }

}
