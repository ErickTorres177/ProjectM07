package cat.copernic.erick.projectm07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModificarPerfil extends AppCompatActivity {

    private Spinner spinnerS;
    private String spinnerSelectedString;
    private EditText tvNombreUModifcar, tvEdadUModifcar, tvSexoUModifcar, tvDireccionUModifcar;
    private Button btnModificarPerfil;
    private static final int edadMax = 110;
    private static final int edadMin = 5;
    final String TAG = "REALTIMEDATABASE";

    private String nombreMostrar, edadMostrar, sexoMostrar, direccionMostrar;

    public List<Rutas> listContent;
    //FIRE BASE
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;
    //Para modificar solo los datos del usuario no las rutas
    private DatabaseReference miNom, miDireccion, miEdad, miSexo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil);

        //REAL TIME -> MODIFICACION PERFIL
        //FIRE BASE inicializaciones
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();

        //INICIALIZACIONES
        nombreMostrar = getIntent().getStringExtra("nombrePerfil");
        nombreMostrar = getIntent().getStringExtra("edadPerfil");
        nombreMostrar = getIntent().getStringExtra("sexoPerfil");
        nombreMostrar = getIntent().getStringExtra("direccionPerdil");


        //Spiner
        spinnerS = findViewById(R.id.spinnerTipSexo);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.tipo_sexo,
                android.R.layout.simple_spinner_item);
        spinnerS.setAdapter(adapterSpinner);
        spinnerS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelectedString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //-----------------------------------
        tvNombreUModifcar = findViewById(R.id.txtNombreUsuario_perfil);
        tvNombreUModifcar.setHint(nombreMostrar);
        tvEdadUModifcar = findViewById(R.id.txtEdad_perfil);
        tvEdadUModifcar.setHint(edadMostrar);
        tvSexoUModifcar = findViewById(R.id.txtNombreUsuario_perfil);
        tvSexoUModifcar.setHint(sexoMostrar);
        tvDireccionUModifcar = findViewById(R.id.txtDireccion_pefil);
        tvDireccionUModifcar.setHint(direccionMostrar);

        //myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid());

        btnModificarPerfil = findViewById(R.id.btnAñadirNuevaRuta);

        //MODIFICAR - PERFIL USUARIO ACTUAL
        btnModificarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuarios usuarios = new Usuarios();

                String nombreComp = (tvNombreUModifcar.getText().toString());
                String edadComp = (tvEdadUModifcar.getText().toString());
                String direccionComp = (tvDireccionUModifcar.getText().toString());


                //MODIFICAR NOMBRE PERFIL
                if (!nombreComp.isEmpty() && !nombreComp.equals(" ")) {
                    if (!compTipoDatosString(nombreComp)) {
                        Toast.makeText(ModificarPerfil.this, "El nom és invàlid: " + tvNombreUModifcar.getText().toString() + ", tens d'introduir només lletres.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        modificarNombrePerfil(tvNombreUModifcar.getText().toString());
                    }
                }

                //MODIFICAR EDAD PERFIL
                if (!edadComp.isEmpty() && !edadComp.equals(" ")) {
                    if (!compIsNumericAndRango(Integer.valueOf(nombreComp))) {
                        Toast.makeText(ModificarPerfil.this, "La edat és invàlida: " + tvEdadUModifcar.getText().toString() + ", (5 - 110).",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        modificarEdadPerfil(tvNombreUModifcar.getText().toString());
                    }
                }

                //MODIFICAR SEXO PERFIL
                if (!spinnerSelectedString.isEmpty() && !spinnerSelectedString.equals("Sexe")) {
                    modificarSexoPerfil(spinnerSelectedString);
                }

                //MODIFICAR DIRECCION PERFIL
                if (!direccionComp.isEmpty() && !direccionComp.equals(" ")) {
                    modificarDireccionPerfil(tvDireccionUModifcar.getText().toString());
                }
            }
        });
    }

    private void modificarNombrePerfil(String nombre) {
        miNom = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("nombre");
        miNom.setValue(nombre);
        tvNombreUModifcar.setText(nombre);
        Toast.makeText(ModificarPerfil.this, "Nom modificat correctament: " + nombre,
                Toast.LENGTH_SHORT).show();
    }

    private void modificarEdadPerfil(String edad) {
        miEdad = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("edad");
        miEdad.setValue(edad);
        tvEdadUModifcar.setText(edad);
        Toast.makeText(ModificarPerfil.this, "Edat modificada correctament: " + edad,
                Toast.LENGTH_SHORT).show();
    }

    private void modificarSexoPerfil(String sexo) {
        miSexo = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("sexo");
        miSexo.setValue(sexo);
        tvSexoUModifcar.setText(sexo);
        Toast.makeText(ModificarPerfil.this, "Sexe modificat correctament: " + sexo,
                Toast.LENGTH_SHORT).show();
    }

    private void modificarDireccionPerfil(String direccion) {
        miDireccion = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("direccion");
        miDireccion.setValue(direccion);
        tvDireccionUModifcar.setText(direccion);
        Toast.makeText(ModificarPerfil.this, "Direcció modificada correctament: " + direccion,
                Toast.LENGTH_SHORT).show();
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

    public boolean compTipoDatosString(String nombre) {
        boolean comp = true;
        Pattern patron = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚàèòÀÈÒçÇ ]*");
        Matcher matcherNombre = patron.matcher(nombre);
        if (!matcherNombre.matches()) {
            comp = false;
        }
        return comp;
    }

    public void handleRegresarPerfil(View view) {
        finish();
    }
}
