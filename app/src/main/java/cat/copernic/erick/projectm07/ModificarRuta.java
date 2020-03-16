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
    private String nomRutaModificar, idRutaModificar, usuarioRutaModificar, descripcionRutaModificar, rutaRutaModificar, paisRutaModificar, ciudadRutaModificar;
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
        usuarioRutaModificar = getIntent().getStringExtra("usuarioRutaModificar");
        descripcionRutaModificar = getIntent().getStringExtra("descripcionRutaModificar");
        rutaRutaModificar = getIntent().getStringExtra("rutaRutaModificar");
        paisRutaModificar = getIntent().getStringExtra("paisRutaModificar");
        ciudadRutaModificar = getIntent().getStringExtra("ciudadRutaModificar");

        setNomRutaModificar = findViewById(R.id.tvTituloRutamodificar);
        setNomRutaModificar.setText(nomRutaModificar);
        setIdiRuta = findViewById(R.id.tvTituloModificar_idRuta);
        setIdiRuta.setText(idRutaModificar);
        setUsuarioRuta = findViewById(R.id.tvTituloModificar_usuarioRuta);
        setUsuarioRuta.setText(usuarioRutaModificar);


        //Inicializaciones
        etIdRutaM = findViewById(R.id.tvTituloModificar_idRuta);
        etUsuarioRutaM = findViewById(R.id.tvTituloModificar_usuarioRuta);
        etNombreRutaM = findViewById(R.id.etModificarRuta_nombre);
        etNombreRutaM.setHint(nomRutaModificar);
        etDescripcionRutaM = findViewById(R.id.etModificarRuta_descripcion);
        etDescripcionRutaM.setHint(descripcionRutaModificar);
        etRutaRutaM = findViewById(R.id.etModificarRuta_ruta);
        etRutaRutaM.setHint(rutaRutaModificar);
        etPaisRutaM = findViewById(R.id.etModificarRuta_pais);
        etPaisRutaM.setHint(paisRutaModificar);
        etCiudadRutaM = findViewById(R.id.etModificarRuta_ciudad);
        etCiudadRutaM.setHint(ciudadRutaModificar);

        modificarRutaRealTime = findViewById(R.id.btnModificarRuta);

        etNombreRutaM.requestFocus();


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
                String rutaR = (etRutaRutaM.getText().toString());
                String descripcionR = (etDescripcionRutaM.getText().toString());
                String paisR = (etPaisRutaM.getText().toString());
                String ciudadR = (etCiudadRutaM.getText().toString());


                //MODIFICAR NOMBRE
                if (!nombreComp.isEmpty() && !nombreComp.equals(" ")) {
                    if (!compTipoDatosString(nombreComp)) {

                        String toastNomInvalido = ModificarRuta.this.getResources().getString(R.string.nombreInvalidoModificarRuta);
                        Toast.makeText(ModificarRuta.this, toastNomInvalido + etNombreRutaM.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        modificarNombreRuta(etNombreRutaM.getText().toString(), idRuta);
                    }
                }

                //MODIFICAR RUTA
                if (!rutaR.isEmpty() && !rutaR.equals(" ")) {
                    if (!compTipoDatosString(rutaR)) {

                        String toastRutaInvalida = ModificarRuta.this.getResources().getString(R.string.rutaInvalidaModificarRuta);
                        Toast.makeText(ModificarRuta.this, toastRutaInvalida + etRutaRutaM.getText().toString() ,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        modificarRutaRuta(etRutaRutaM.getText().toString(), idRuta);
                    }
                }

                //MODIFICAR DESCRIPCION
                if (!descripcionR.isEmpty() && !descripcionR.equals(" ")) {
                    modificarDescripcionRuta(etDescripcionRutaM.getText().toString(), idRuta);
                }

                //MODIFICAR PAIS
                if (!paisR.isEmpty() && !paisR.equals(" ")) {
                    if (!compTipoDatosString(paisR)) {

                        String toastPaisInvalido = ModificarRuta.this.getResources().getString(R.string.paisInvalidoModificarRuta);
                        Toast.makeText(ModificarRuta.this, toastPaisInvalido + etPaisRutaM.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        modificarPaisRuta(etPaisRutaM.getText().toString(), idRuta);
                    }
                }

                //MODIFICAR CIUDAD
                if (!ciudadR.isEmpty() && !ciudadR.equals(" ")) {
                    if (!compTipoDatosString(ciudadR)) {

                        String toastCiudadInvalida = ModificarRuta.this.getResources().getString(R.string.ciudadInvalidoModificarRuta);
                        Toast.makeText(ModificarRuta.this, toastCiudadInvalida + etCiudadRutaM.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        modificarCiudadRuta(etCiudadRutaM.getText().toString(), idRuta);
                    }
                }
            }
        });


    }

    private void modificarNombreRuta(String nombre, String idRuta) {
        refNombreR = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas").child(idRuta).child("nombreRuta");
        refNombreR.setValue(nombre);
        setNomRutaModificar.setText(nombre);

        String toastNombreModificada = ModificarRuta.this.getResources().getString(R.string.nombreModificadoCModificarRuta);
        Toast.makeText(ModificarRuta.this, toastNombreModificada + nombre,
                Toast.LENGTH_SHORT).show();
    }

    private void modificarDescripcionRuta(String descripcion, String idRuta) {
        refDescripcionR = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas").child(idRuta).child("descripcionRuta");
        refDescripcionR.setValue(descripcion);

        String toastDescripcionModificada = ModificarRuta.this.getResources().getString(R.string.descripcionModificadoCModificarRuta);
        Toast.makeText(ModificarRuta.this, toastDescripcionModificada + descripcion,
                Toast.LENGTH_SHORT).show();
    }

    private void modificarRutaRuta(String ruta, String idRuta) {
        refRutaR = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas").child(idRuta).child("ruta");
        refRutaR.setValue(ruta);

        String toastRutaModificada = ModificarRuta.this.getResources().getString(R.string.rutaModificadaCModificarRuta);
        Toast.makeText(ModificarRuta.this, toastRutaModificada + ruta,
                Toast.LENGTH_SHORT).show();
    }

    private void modificarPaisRuta(String pais, String idRuta) {
        refPaisR = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas").child(idRuta).child("paisRuta");
        refPaisR.setValue(pais);

        String toastPaisModificado = ModificarRuta.this.getResources().getString(R.string.paisModificadoCModificarRuta);
        Toast.makeText(ModificarRuta.this, toastPaisModificado + pais,
                Toast.LENGTH_SHORT).show();
    }

    private void modificarCiudadRuta(String ciudad, String idRuta) {
        refCiudadR = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas").child(idRuta).child("ciudadRuta");
        refCiudadR.setValue(ciudad);

        String toastCiudadModificada = ModificarRuta.this.getResources().getString(R.string.ciudadModificadaCModificarRuta);
        Toast.makeText(ModificarRuta.this, toastCiudadModificada + ciudad,
                Toast.LENGTH_SHORT).show();
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
        etNombreRutaM.getText().clear();
        etDescripcionRutaM.getText().clear();
        etRutaRutaM.getText().clear();
        etPaisRutaM.getText().clear();
        etCiudadRutaM.getText().clear();
    }

    public void volverRutasConfiguracion(View view) {
        limpiarCampo();
        finish();
    }
}
