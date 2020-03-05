package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModificarRuta extends AppCompatActivity {


    private EditText etNombreRutaM, etDescripcionRutaM, etRutaRutaM, etPaisRutaM, etCiudadRutaM;
    private Button modificarRutaRealTime;
    private String nomRutaModificar, idRutaModificar, usuarioRuta;
    private TextView setNomRutaModificar, setIdiRuta, setUsuarioRuta, etIdRutaM, etUsuarioRutaM;


    //FIRE BASE
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef, refNombreR, refDescripcionR, refRutaR, refPaisR, refCiudadR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_ruta);

        nomRutaModificar = getIntent().getStringExtra("nombreRutaModificar");
        idRutaModificar = getIntent().getStringExtra("idRutaModificar");
        usuarioRuta = getIntent().getStringExtra("usuarioRutaModificar");

        setNomRutaModificar = findViewById(R.id.tvTituloRutamodificar);
        setNomRutaModificar.setText(nomRutaModificar);
        setIdiRuta = findViewById(R.id.tvTituloModificar_idRuta);
        setIdiRuta.setText(idRutaModificar);
        setUsuarioRuta = findViewById(R.id.tvTituloModificar_usuarioRuta);
        setUsuarioRuta.setText(usuarioRuta);


        //Inicializaciones
        etIdRutaM = findViewById(R.id.tvTituloModificar_idRuta);
        etUsuarioRutaM = findViewById(R.id.tvTituloModificar_usuarioRuta);
        etNombreRutaM = findViewById(R.id.etModificarRuta_nombre);
        etDescripcionRutaM = findViewById(R.id.etModificarRuta_descripcion);
        etRutaRutaM = findViewById(R.id.etModificarRuta_ruta);
        etPaisRutaM = findViewById(R.id.etModificarRuta_pais);
        etCiudadRutaM = findViewById(R.id.etModificarRuta_ciudad);

        modificarRutaRealTime = findViewById(R.id.btnModificarRuta);


        //REAL TIME -> MODIFICACION PERFIL
        //FIRE BASE inicializaciones
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();

        modificarRutaRealTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idRuta = idRutaModificar;

                String nombreComp = (etNombreRutaM.getText().toString());
                String descripcionR = (etDescripcionRutaM.getText().toString());
                String rutaR = (etRutaRutaM.getText().toString());
                String paisR = (etPaisRutaM.getText().toString());
                String ciudadR = (etCiudadRutaM.getText().toString());

                if (!nombreComp.isEmpty() || !nombreComp.equals("")) {
                    if (compTipoDatosString(nombreComp)) {
                        modificarNombreRuta(nombreComp, idRuta);
                    } else {
                        /*Toast.makeText(ModificarRuta.this, "El nom és invàlid: " + nombreComp,
                                Toast.LENGTH_SHORT).show();*/
                        toastModificacionCorrecta();
                    }
                } else if (!descripcionR.isEmpty() || !descripcionR.equals("")) {
                    if (compTipoDatosString(descripcionR)) {
                        modificarDescripcionRuta(descripcionR, idRuta);
                    } else {
                        toastModificacionCorrecta();
                       /* Toast.makeText(ModificarRuta.this, "La descripció és invàlida: " + descripcionR,
                                Toast.LENGTH_SHORT).show();*/
                    }
                } else if (!rutaR.isEmpty() || !rutaR.equals("")) {
                    if (compTipoDatosString(rutaR)) {
                        modificarRutaRuta(rutaR, idRuta);
                    } else {
                        toastModificacionCorrecta();
                      /*  Toast.makeText(ModificarRuta.this, "La ruta és invàlida: " + rutaR,
                                Toast.LENGTH_SHORT).show();*/
                    }
                } else if (!paisR.isEmpty() || !paisR.equals("")) {
                    if (compTipoDatosString(paisR)) {
                        modificarDescripcionRuta(paisR, idRuta);
                    } else {
                        toastModificacionCorrecta();
                       /* Toast.makeText(ModificarRuta.this, "El país és invàlid: " + paisR,
                                Toast.LENGTH_SHORT).show();*/
                    }
                } else if (!ciudadR.isEmpty() || !ciudadR.equals("")) {
                    if (compTipoDatosString(ciudadR)) {
                        modificarDescripcionRuta(ciudadR, idRuta);
                    } else {
                        toastModificacionCorrecta();
                       /*Toast.makeText(ModificarRuta.this, "La ciutat és invàlida: " + ciudadR,
                                Toast.LENGTH_SHORT).show();*/
                    }
                } else {
                    Toast.makeText(ModificarRuta.this, "Sense modificar: ",
                            Toast.LENGTH_SHORT).show();
                }








                /*


            } else if(descripcionR.isEmpty()||descripcionR.equals("")||descripcionR.equals(null))

            {
                Toast.makeText(ModificarRuta.this, "La descripció és invàlida.",
                        Toast.LENGTH_SHORT).show();
            } else if(rutaR.isEmpty()||rutaR.equals("")||rutaR.equals(null))

            {
                Toast.makeText(ModificarRuta.this, "La ruta és invàlida.",
                        Toast.LENGTH_SHORT).show();
            } else if(paisR.isEmpty()||paisR.equals("")||paisR.equals(null))

            {
                Toast.makeText(ModificarRuta.this, "El país és invàlid.",
                        Toast.LENGTH_SHORT).show();
            } else if(!

            compTipoDatosString(paisR))

            {
                Toast.makeText(ModificarRuta.this, "El país es invàlid: " + etPaisRutaM.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            } else if(ciudadR.isEmpty()||ciudadR.equals("")||ciudadR.equals(null))

            {
                Toast.makeText(ModificarRuta.this, "La ciutat es invàlida.",
                        Toast.LENGTH_SHORT).show();
            } else if(!

            compTipoDatosString(ciudadR))

            {
                Toast.makeText(ModificarRuta.this, "La ciutat es invàlida: " + etCiudadRutaM.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            } else

            {

                guardarRutaNuevaRT();
            }

                Rutas ruta = new Rutas();
                //ruta.setIdRuta(idRutaModificar);
                ruta.setNombreRuta(etNombreRutaM.getText().toString());
                ruta.setDescripcionRuta(etDescripcionRutaM.getText().toString());
                ruta.setRuta(etRutaRutaM.getText().toString());
                ruta.setPaisRuta(etPaisRutaM.getText().toString());
                ruta.setCiudadRuta(etCiudadRutaM.getText().toString());
                ruta.setUsuarioRuta(etNombreRutaM.getText().toString());

                //new FireBaseDHConfiguracionRuta().updateRutaConfiguracion(key, );
*/


            }
        });


    }

    private void modificarNombreRuta(String nombre, String idRuta) {
        refNombreR = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas").child(idRuta).child("nombreRuta");
        refNombreR.setValue(nombre);
        limpiarCampo();
        Toast.makeText(ModificarRuta.this, "Nom modificat correctament: " + nombre,
                Toast.LENGTH_SHORT).show();
    }

    private void modificarDescripcionRuta(String descripcion, String idRuta) {
        refDescripcionR = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas").child(idRuta).child("descripcionRuta");
        refDescripcionR.setValue(descripcion);
        limpiarCampo();
        Toast.makeText(ModificarRuta.this, "Descripció modificada correctament: " + descripcion,
                Toast.LENGTH_SHORT).show();
    }

    private void modificarRutaRuta(String ruta, String idRuta) {
        refRutaR = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas").child(idRuta).child("ruta");
        refRutaR.setValue(ruta);
        limpiarCampo();
        Toast.makeText(ModificarRuta.this, "Ruta modificada correctament: " + ruta,
                Toast.LENGTH_SHORT).show();
    }

    private void modificarPaisRuta(String pais, String idRuta) {
        refPaisR = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas").child(idRuta).child("paisRuta");
        refPaisR.setValue(pais);
        limpiarCampo();
        Toast.makeText(ModificarRuta.this, "País modificat correctament: " + pais,
                Toast.LENGTH_SHORT).show();
    }

    private void modificarCiudadRuta(String ciudad, String idRuta) {
        refCiudadR = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas").child(idRuta).child("ciudadRuta");
        refCiudadR.setValue(ciudad);
        limpiarCampo();
        Toast.makeText(ModificarRuta.this, "Ciutat modificada correctament: " + ciudad,
                Toast.LENGTH_SHORT).show();
    }


    private void guardarRutaNuevaRT() {

/*
        //DatabaseReference currentUserDB = myRef.child(mAuth.getCurrentUser().getUid()).child("rutas");
        idRutaGeneral++;
        myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid())
                .child("rutas").child(String.valueOf(idRutaGeneral));
        myRef.child("idRuta").setValue(String.valueOf(idRutaGeneral));
        myRef.child("usuarioRuta").setValue(currentUser.getEmail());
        myRef.child("nombreRuta").setValue(etNombreR.getText().toString());
        myRef.child("descripcionRuta").setValue(etDescripcionR.getText().toString());
        myRef.child("ruta").setValue(etRutaR.getText().toString());
        myRef.child("ciudadRuta").setValue(etCiudadR.getText().toString());
        myRef.child("paisRuta").setValue(etPaisR.getText().toString());

        limpiarCampo();
        Toast.makeText(NuevaRuta.this, "Ruta agregada correctament: " + etNombreR.getText().toString(),
                Toast.LENGTH_SHORT).show();


    }*/

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
       /*etNombreR.getText().clear();
        etDescripcionR.getText().clear();
        etRutaR.getText().clear();
        etPaisR.getText().clear();
        etCiudadR.getText().clear();*/
    }

    public void btnVolverConfigRutas(View view) {
        limpiarCampo();
        finish();
    }

    public void volverRutasConfiguracion(View view) {
        finish();
    }

    public void toastModificacionCorrecta() {
        Toast.makeText(ModificarRuta.this, "Modificació correcta.: ",
                Toast.LENGTH_SHORT).show();

    }

}
