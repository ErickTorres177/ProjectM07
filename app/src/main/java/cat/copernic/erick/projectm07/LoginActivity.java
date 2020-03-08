package cat.copernic.erick.projectm07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private EditText user, pass, nombreUsuariu, edat;
    private ImageButton btn_maps, btn_web, btn_insta, btn_gmail;
    private ImageView img_login, img_registro;
    private String useruarioLogin, passLogin;

    //FIRE BASE
    private Button btnLogin, btnCambiarIdioma;
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    //DatabaseReference myReference;
    //FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FIRE BASE
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mAuth.signOut();

        user = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPass);

        //PONER DATOS DEL USER REGISTRADO
        useruarioLogin = getIntent().getStringExtra("usuer");
        passLogin = getIntent().getStringExtra("pass");

        user.setText(useruarioLogin);
        pass.setText(passLogin);
        //---
        //database = FirebaseDatabase.getInstance();
        //database.getReference();

        /*
            NO BORRAR -> PREGUNTAR A ESTER

        Bundle extras = getIntent().getExtras();
        String nom = extras.getString("nombre");

        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+nom);

        user.setText(nom);
        //str = getIntent().getExtras().getString("Value");
         */


        //BOTONES DE REDIRECCION (SIN MAS)
        btn_maps = findViewById(R.id.imgBtn_Gmaps);
        btn_web = findViewById(R.id.imgBtn_web);
        btn_insta = findViewById(R.id.imgBtn_instagram);
        btn_gmail = findViewById(R.id.imgBtn_instagram);
        btnLogin = findViewById(R.id.btnIniciarSesion);
        btnCambiarIdioma = findViewById(R.id.btnCambiarIdioma);

        // Iconos de accesibilidad
        img_login = findViewById(R.id.imgLogin);

        //user.addTextChangedListener(loginTW);
        //pass.addTextChangedListener(loginTW);

        //controlDeCampos(currentUser);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                    String toastCredenciales = LoginActivity.this.getResources().getString(R.string.credencialesIcorrectas);
                    Toast.makeText(LoginActivity.this, toastCredenciales,
                            Toast.LENGTH_SHORT).show();

                } else {
                    loginUser(user.getText().toString(), pass.getText().toString());
                }
            }
        });

        img_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                    String toastCredenciales = LoginActivity.this.getResources().getString(R.string.credencialesIcorrectas);
                    Toast.makeText(LoginActivity.this, toastCredenciales,
                            Toast.LENGTH_SHORT).show();

                } else {
                    loginUser(user.getText().toString(), pass.getText().toString());
                }
            }
        });

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(getResources().getString(R.string.app_name));

        btnCambiarIdioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarCambiarIdioma();
            }
        });
    }

    private void mostrarCambiarIdioma() {
        final String[] lista_item = {"Catalán", "English", "Castellano "};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
        mBuilder.setTitle("Tria l'idioma");
        mBuilder.setSingleChoiceItems(lista_item, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    setLocale("ca");
                    recreate();
                } else if (i == 1) {
                    setLocale("en");
                    recreate();
                } else if (i == 2) {
                    setLocale("es");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }
    private void setLocale(String lenguaje) {
        Locale locale = new Locale(lenguaje);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("miLenguaje", lenguaje);
        editor.apply();
    }

    private void cargaLocale() {
        SharedPreferences preferences = getSharedPreferences("Settings", LoginActivity.MODE_PRIVATE);
        String lenguaje = preferences.getString("miLenguaje", "");
        setLocale(lenguaje);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //if (currentUser != null){
        //  mAuth.signOut();
        //}
        updateUI(currentUser);
    }

    public void loginUser(final String email, final String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String toastHola = LoginActivity.this.getResources().getString(R.string.holaUsuario);
                            Toast.makeText(LoginActivity.this, toastHola + ": " + email,
                                    Toast.LENGTH_SHORT).show();

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(LoginActivity.this, NavegationDrawer.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            String toastIniSFallida = LoginActivity.this.getResources().getString(R.string.credencialesIcorrectas);
                            Toast.makeText(LoginActivity.this, toastIniSFallida,
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
        } else {
        }
    }

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

    //METODO QUE CONTROLA QUE NO SE PULSE LOGIN(BTN) HASTA QUE RELLENE LOS DOS CAMPOS (USER AND PASS)
   /*  private TextWatcher loginTW  = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

       @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String userNameInput = user.getText().toString().trim();
            String passNameInput = user.getText().toString().trim();

            btnLogin.setEnabled(!userNameInput.isEmpty() && !passNameInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };*/
    private void controlDeCampos(FirebaseUser currentUser) {
        if (currentUser != null) {
            user.getText().clear();
            //pass.getText().clear();
        } else {
            user.getText().clear();
            pass.getText().clear();
        }
    }
}