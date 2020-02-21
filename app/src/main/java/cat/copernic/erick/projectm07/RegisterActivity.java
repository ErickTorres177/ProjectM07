package cat.copernic.erick.projectm07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    EditText etUsuario, etPasswd, etEdad;

    Button btnRegistrarU;

    //FIRE BASE
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

        btnRegistrarU = findViewById(R.id.btnRegistrarUsuario);

        btnRegistrarU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuario(etUsuario.getText().toString(), etPasswd.getText().toString());
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void crearUsuario(final String email, String password) {

    if (email.isEmpty() || password.isEmpty()) {
        Toast.makeText(RegisterActivity.this, "Credencials incorrectes",
                Toast.LENGTH_SHORT).show();
    }else{
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (compEmail(email) == true) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                Toast.makeText(RegisterActivity.this, "Compte registrada: " + email,
                                        Toast.LENGTH_SHORT).show();

                                etUsuario.getText().clear();
                                etPasswd.getText().clear();
                                etEdad.getText().clear();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Registre fallit: " + email,
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Ingressa un e-mail vàlid." + email,
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
    }
    private void updateUI(FirebaseUser currentUser) {

    }

    public boolean compEmail(String email) {
        Boolean comp;

        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            comp = true;
        } else {
            comp = false;
        }
        return comp;
    }

    public void handleRegresar(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        //this.finish();
    }
}