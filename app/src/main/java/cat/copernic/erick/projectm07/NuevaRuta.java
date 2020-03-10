package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NuevaRuta extends AppCompatActivity {


    private EditText etNombreR, etDescripcionR, etRutaR, etPaisR, etCiudadR;
    private static int idRutaGeneral = 0;
    private static final String sinDefinir = "Sense definir";

    //FIRE BASE
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;
    private DatabaseReference miNomRuta, miDescripcionRuta, miRutaRuta, miPaisRuta, miCiudadRuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_ruta);

        etNombreR = findViewById(R.id.etNombreNuevaRuta);
        etRutaR = findViewById(R.id.etRutaNuevaRuta);
        etDescripcionR = findViewById(R.id.etDescripcionRutaNueva);
        etPaisR = findViewById(R.id.etPaisRutaNueva);
        etCiudadR = findViewById(R.id.etCiudadRutaNueva);

        //REAL TIME -> MODIFICACION PERFIL
        //FIRE BASE inicializaciones
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void nuevaRuta(View view) {

        idRutaGeneral++;
        myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid())
                .child("rutas").child(String.valueOf(idRutaGeneral));


        String nomComp = etNombreR.getText().toString();
        String rutaComp = etRutaR.getText().toString();


        if (!nomComp.isEmpty() && !nomComp.equals(" ")
                && !rutaComp.isEmpty() && !rutaComp.equals(" ")) {
            if (!compTipoDatosString(nomComp)) {

                String toastInvalidoNombre = NuevaRuta.this.getResources().getString(R.string.nombreInvalidoNuevaRuta);
                Toast.makeText(NuevaRuta.this, toastInvalidoNombre + etNombreR.getText().toString(),
                        Toast.LENGTH_SHORT).show();

            } else if (!compTipoDatosString(rutaComp)) {

                String toastInvalidoRuta = NuevaRuta.this.getResources().getString(R.string.rutaInvalidaNuevaRuta);
                Toast.makeText(NuevaRuta.this, toastInvalidoRuta + etRutaR.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            } else {
                myRef.child("nombreRuta").setValue(etNombreR.getText().toString());
                myRef.child("ruta").setValue(etRutaR.getText().toString());
            }

            if (etDescripcionR.getText().toString().isEmpty()) {
                myRef.child("descripcionRuta").setValue(sinDefinir);
            } else if (!etDescripcionR.getText().toString().isEmpty()) {
                myRef.child("descripcionRuta").setValue(etDescripcionR.getText().toString());
            }

            if (etPaisR.getText().toString().isEmpty()) {
                myRef.child("paisRuta").setValue(sinDefinir);
            } else {
                if (!compTipoDatosString(etPaisR.getText().toString())) {

                    String toastInvalidoRuta = NuevaRuta.this.getResources().getString(R.string.paisInvalidoNuevaRuta);
                    Toast.makeText(NuevaRuta.this, toastInvalidoRuta + etPaisR.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    myRef.child("paisRuta").setValue(etPaisR.getText().toString());
                }
            }
            if (etCiudadR.getText().toString().isEmpty()) {
                myRef.child("ciudadRuta").setValue(sinDefinir);
            } else {
                if (!compTipoDatosString(etCiudadR.getText().toString())) {

                    String toastInvalidoCiudad = NuevaRuta.this.getResources().getString(R.string.ciudadInvalidaNuevaRuta);
                    Toast.makeText(NuevaRuta.this, toastInvalidoCiudad + etCiudadR.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    myRef.child("ciudadRuta").setValue(etCiudadR.getText().toString());
                }
            }
            myRef.child("idRuta").setValue(String.valueOf(idRutaGeneral));
            myRef.child("usuarioRuta").setValue(currentUser.getEmail());
            limpiarCampo();

            String toastAgregadaRuta = NuevaRuta.this.getResources().getString(R.string.rutaAgregadaCNuevaRuta);
            Toast.makeText(NuevaRuta.this, toastAgregadaRuta+ etNombreR.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        } else {

            String toastObligatorio = NuevaRuta.this.getResources().getString(R.string.nom_ruta_obligatoriosNuevaRuta);
            Toast.makeText(NuevaRuta.this.getApplicationContext(), toastObligatorio,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public boolean compTipoDatosString(String nombre) {
        boolean comp = true;
        Pattern patron = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚàèòÀÈÒçÇ ]*");
        Matcher matcherNombre = patron.matcher(nombre);
        if (!matcherNombre.matches()) {
            comp = false;
        }
        return comp;
    }

    private void limpiarCampo() {
        etNombreR.getText().clear();
        etDescripcionR.getText().clear();
        etRutaR.getText().clear();
        etPaisR.getText().clear();
        etCiudadR.getText().clear();
    }

    public void btnVolverConfigRutas(View view) {
        limpiarCampo();
        finish();
    }

}
