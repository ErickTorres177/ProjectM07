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
    private String spinnerSelected;
    private EditText tvNombreUModifcar, tvEdadUModifcar, tvSexoUModifcar, tvDireccionUModifcar;
    private Button btnModificarPerfil;
    private static final int edadMax = 110;
    private static final int edadMin = 5;
    final String TAG = "REALTIMEDATABASE";

    public List<Rutas> listContent;
    //FIRE BASE
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;

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

        //Spiner
        spinnerS = findViewById(R.id.spinnerTipSexo);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.tipo_sexo,
                android.R.layout.simple_spinner_item);
        spinnerS.setAdapter(adapterSpinner);
        spinnerS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //-----------------------------------
        tvNombreUModifcar = findViewById(R.id.txtNombreUsuario_perfil);
        tvEdadUModifcar = findViewById(R.id.txtEdad_perfil);
        tvSexoUModifcar = findViewById(R.id.txtNombreUsuario_perfil);
        tvDireccionUModifcar = findViewById(R.id.txtDireccion_pefil);

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

                if (nombreComp.isEmpty() || nombreComp.equals("") || nombreComp.equals(null)) {
                    Toast.makeText(ModificarPerfil.this, "Ingressa el teu nom: " + tvNombreUModifcar.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                } else if (!compNombreIntroducido(nombreComp)) {
                    Toast.makeText(ModificarPerfil.this, "El nom és invàlid: " + tvNombreUModifcar.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                } else if (edadComp.isEmpty() || edadComp.equals("") || edadComp.equals(null)) {
                    Toast.makeText(ModificarPerfil.this, "Ingressa a teva edat: " + tvEdadUModifcar.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                } else if (!compIsNumericAndRango(Integer.parseInt(edadComp))) {
                    Toast.makeText(ModificarPerfil.this, "L'edat és incorrecta: " + tvEdadUModifcar.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                } else if (spinnerSelected.equals("Sexe")) {
                    Toast.makeText(ModificarPerfil.this, "Ingressa el teu sexe.",
                            Toast.LENGTH_SHORT).show();
                } else if (direccionComp.isEmpty() || direccionComp.equals("") || direccionComp.equals(null)) {
                    Toast.makeText(ModificarPerfil.this, "L'adreça és incorrecta: " + tvDireccionUModifcar.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    usuarios.setNombre(tvNombreUModifcar.getText().toString());
                    usuarios.setEdad(tvEdadUModifcar.getText().toString());
                    usuarios.setUsuario(currentUser.getEmail());
                    usuarios.setDireccion(tvDireccionUModifcar.getText().toString());
                    usuarios.setSexo(spinnerSelected);
                    //usuarios.setRutas();


                    //myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid());
                    //myRef.setValue(usuarios);

/*
                    Rutas pMain = new Rutas();
                    listContent = new ArrayList<>();
                    listContent.add(pMain.rutasList);*/

                    //myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid());
                    //myRef.setValue(usuarios);



                   /* myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid());
                    //Log.e("Usuario actual: ", "" + currentUser.getUid());

                    myRef.addValueEventListener(new ValueEventListener() {
                        //myRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Usuarios usuarios = dataSnapshot.getValue(Usuarios.class);
                            final String nombre = usuarios.getNombre();
                            final String user = usuarios.getUsuario();
                            final String edad = usuarios.getEdad();
                            final String sexo = usuarios.getSexo();
                            final String direccion = usuarios.getDireccion();
                            //final List<Rutas> rutasUsuario = usuarios.getRutas();


                            //Obtencion de todos los usuarios
                            for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                                myRef.child("rutas").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Usuarios usuarios = ds.getValue(Usuarios.class);
                                        listContent = usuarios.getRutas();



                                        Log.e("Nombre: ", "" + listContent);
                                        Log.e("Datos: ", "" + ds.getValue());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });*/


                    Toast.makeText(ModificarPerfil.this, "Perfil modificat correctament: "
                                    + " \nNom: " + tvNombreUModifcar.getText().toString()
                                    + " \nEdat: " + tvEdadUModifcar.getText().toString()
                                    + " \nDirecció: " + tvDireccionUModifcar.getText().toString()
                                    + " \nSexe: " + spinnerSelected,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public void handleRegresarPerfil(View view) {
        finish();
    }
}
