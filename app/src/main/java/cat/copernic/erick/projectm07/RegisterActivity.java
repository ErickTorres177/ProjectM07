package cat.copernic.erick.projectm07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsuario;
    EditText etPasswd;
    EditText etEdad;

    //FireBase Declaracion Variables
    Button btnRegistrarU;

    //FIRE BASE ->
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsuario = findViewById(R.id.txtNombre);
        etPasswd = findViewById(R.id.txtPasswd);
        etEdad = findViewById(R.id.txtEdad);


        //currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        //FIRE BASE IMPLEMENTACION
        // Initialize Firebase Auth
        //mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //final int edadUsuario = Integer.parseInt(etEdad.getText().toString());
        btnRegistrarU = findViewById(R.id.btnRegistrarUsuario);

        btnRegistrarU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuario(etUsuario.getText().toString(), etPasswd.getText().toString());
                //Con esto el usuario solo podra clickar una vez
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void crearUsuario(final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (compEmail(email) == true) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(RegisterActivity.this, "Autentificación valida.",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                //btnLogin.setEnabled(true);
                                //btnNuevo.setEnabled(false);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Autentificación invalida.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                                //btnLogin.setEnabled(true);
                                //btnNuevo.setEnabled(true);
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Usuario incorrecto(email).",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            btnRegistrarU.setEnabled(false);

        } else {
            btnRegistrarU.setEnabled(true);
        }
    }



    public boolean compEmail(String email) {

        Boolean comp;

        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);

        if (mather.find() == true) {
            //Toast.makeText(MainActivity.this, "Email Valido.",
            //Toast.LENGTH_SHORT).show();
            comp = true;
            //System.out.println("El email ingresado es válido.");
        } else {
            //Toast.makeText(MainActivity.this, "Email inválido.",
            //Toast.LENGTH_SHORT).show();
            comp = false;
            //System.out.println("El email ingresado es inválido.");
        }
        return comp;
    }


/*
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
    }*/

    public void handleRegresar(View view) {
        this.finish();
    }
}