package cat.copernic.erick.projectm07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsuario, etPasswd, etNombreUsuario, etEdad;
    private ImageView iconoRegitrar;
    private Button btnRegistrarU;
    private static final int edadMax = 110;
    private static final int edadMin = 5;

    //FIRE BASE
    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    //---
    DatabaseReference myReference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsuario = findViewById(R.id.txtNombreUsuario_perfil);
        etPasswd = findViewById(R.id.txtEdad_perfil);
        etNombreUsuario = findViewById(R.id.txtDireccion_pefil);
        etEdad = findViewById(R.id.txtEdad);


        //Data fase
        database = FirebaseDatabase.getInstance();
        myReference = database.getReference("Usuarios");


        // Inicializamos y establecemos el click del icono de login
        iconoRegitrar = findViewById(R.id.imgGuardar_perfil);
        iconoRegitrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuario(etUsuario.getText().toString(), etPasswd.getText().toString());
            }
        });

        //currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

        //FIRE BASE
        //mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        btnRegistrarU = findViewById(R.id.btnAñadirNuevaRuta);

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

    public void crearUsuario(final String email, final String password) {

        if (email.isEmpty() || password.isEmpty()) {

            String toastCredencialesIncorrectas = RegisterActivity.this.getResources().getString(R.string.credencialesIcorrectas);
            Toast.makeText(RegisterActivity.this, toastCredencialesIncorrectas,
                    Toast.LENGTH_SHORT).show();

        } else {
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

                                    String toastCuentaCreada = RegisterActivity.this.getResources().getString(R.string.cuentaCreada);
                                    guardatUsuarioFB();
                                    Toast.makeText(RegisterActivity.this, toastCuentaCreada + ": " + email,
                                            Toast.LENGTH_SHORT).show();

                                    limpiarCampo();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());

                                    String toastRegistroFallido = RegisterActivity.this.getResources().getString(R.string.registroFallido);
                                    Toast.makeText(RegisterActivity.this, toastRegistroFallido + ": " + email,
                                            Toast.LENGTH_SHORT).show();

                                    updateUI(null);
                                }
                            } else {
                                String toastEmailInvalido = RegisterActivity.this.getResources().getString(R.string.emailInvalido);
                                Toast.makeText(RegisterActivity.this, toastEmailInvalido + ": " + email,
                                        Toast.LENGTH_SHORT).show();
                            }
                            // ...
                        }
                    });
        }
    }

    private void guardatUsuarioFB() {
        DatabaseReference currentUserDB = myReference.child(mAuth.getCurrentUser().getUid());

        String nombreComp = (etNombreUsuario.getText().toString());
        String edadComp = (etEdad.getText().toString());

        if (nombreComp.isEmpty() || nombreComp.equals("") || nombreComp.equals(null)){
            Toast.makeText(RegisterActivity.this, "Ingressa el teu nom: " + etNombreUsuario.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        } else if (!compNombreIntroducido(nombreComp)) {
            Toast.makeText(RegisterActivity.this, "El nom és invàlid: " + etNombreUsuario.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        } else if (edadComp.isEmpty() || edadComp.equals("") || edadComp.equals(null)) {
            Toast.makeText(RegisterActivity.this, "Ingressa a teva edat: " + etEdad.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        } else if (!compIsNumericAndRango(Integer.parseInt(edadComp))) {
            Toast.makeText(RegisterActivity.this, "L'edat és incorrecta: " + etEdad.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        } else {

            currentUserDB.child("usuario").setValue(etUsuario.getText().toString());
            currentUserDB.child("nombre").setValue(etNombreUsuario.getText().toString());
            currentUserDB.child("edad").setValue(etEdad.getText().toString());
            currentUserDB.child("sexo").setValue("Sense definir");
            currentUserDB.child("direccion").setValue("Sense definir");

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

    public boolean compIsNumericAndRango(int edad) {
        boolean comp = true;
        if (!isNumeric(edad)) {
            comp = false;
        } else if (edad > edadMax) {
            comp = false;
        } else if (edad < edadMin) {
            comp = false;
        }
        return comp;
    }

    private boolean isNumeric(int numeroComp) {
        String a = String.valueOf(numeroComp);
        try {
            Integer.parseInt(a);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean compNombreIntroducido(String nombre) {
        boolean comp = true;
        Pattern patron = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚàèòÀÈÒçÇ ]*");
        Matcher matcherNombre = patron.matcher(nombre);
        if (!matcherNombre.matches()) {
            comp = false;
        }
        return comp;
    }

    public void handleRegresar(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    private void limpiarCampo() {
        etUsuario.getText().clear();
        etNombreUsuario.getText().clear();
        etPasswd.getText().clear();
        etEdad.getText().clear();
    }
}