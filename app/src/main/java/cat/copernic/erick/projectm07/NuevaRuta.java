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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NuevaRuta extends AppCompatActivity {


    private EditText etNombreR, etDescripcionR, etRutaR, etPaisR, etCiudadR;
    private Button btnAñadirNuevaRuta;
    private static int idRutaGeneral = 1;

    //FIRE BASE
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_ruta);

        etNombreR = findViewById(R.id.etNombreNuevaRuta);
        etDescripcionR = findViewById(R.id.etDescripcionRutaNueva);
        etRutaR = findViewById(R.id.etRutaNuevaRuta);
        etPaisR = findViewById(R.id.etPaisRutaNueva);
        etCiudadR = findViewById(R.id.etCiudadRutaNueva);
        btnAñadirNuevaRuta = findViewById(R.id.btnAñadirNuevaRuta);


        //REAL TIME -> MODIFICACION PERFIL
        //FIRE BASE inicializaciones
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();

        btnAñadirNuevaRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarRutaNuevaRT();
            }
        });



    }

    private void guardarRutaNuevaRT() {

        String nombreComp = (etNombreR.getText().toString());
        String descripcionR = (etDescripcionR.getText().toString());
        String rutaR = (etRutaR.getText().toString());

        if (nombreComp.isEmpty() || nombreComp.equals("") || nombreComp.equals(null)) {
            Toast.makeText(NuevaRuta.this, "Ingressa el nom de la ruta: " + etNombreR.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        } else if (!compNombreRuta(nombreComp)) {
            Toast.makeText(NuevaRuta.this, "El nom és invàlid: " + etNombreR.getText().toString() + ", tens d'introduir només lletres.",
                    Toast.LENGTH_SHORT).show();
        } else if (descripcionR.isEmpty() || descripcionR.equals("") || descripcionR.equals(null)) {
            Toast.makeText(NuevaRuta.this, "La descripció és invàlida: " + etDescripcionR.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        } else if (rutaR.isEmpty() || rutaR.equals("") || rutaR.equals(null)) {
            Toast.makeText(NuevaRuta.this, "La ruta és invàlida: " + etRutaR.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        } else {
            //DatabaseReference currentUserDB = myRef.child(mAuth.getCurrentUser().getUid()).child("rutas");
            idRutaGeneral++;
            myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas");
            myRef.child("idRuta").setValue(String.valueOf(idRutaGeneral));
            myRef.child("usuarioRuta").setValue(currentUser.getEmail());
            myRef.child("nombreRuta").setValue(etNombreR.getText().toString());
            myRef.child("descripcionRuta").setValue(etDescripcionR.getText().toString());
            myRef.child("ruta").setValue(etRutaR.getText().toString());
            myRef.child("ciudadRuta").setValue("Sense definir");
            myRef.child("paisRuta").setValue("Sense definir");
        }
    }

    public boolean compNombreRuta(String nombre) {
        boolean comp = true;
        Pattern patron = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚàèòÀÈÒçÇ ]*");
        Matcher matcherNombre = patron.matcher(nombre);
        if (!matcherNombre.matches()) {
            comp = false;
        }
        return comp;
    }

}
