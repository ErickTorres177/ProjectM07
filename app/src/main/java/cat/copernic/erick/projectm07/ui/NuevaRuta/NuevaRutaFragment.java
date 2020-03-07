package cat.copernic.erick.projectm07.ui.NuevaRuta;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cat.copernic.erick.projectm07.R;
import cat.copernic.erick.projectm07.Rutas;
import cat.copernic.erick.projectm07.Usuarios;

public class NuevaRutaFragment extends Fragment {

    private NuevaRutaViewModel rutasViewModel;
    private EditText etNombreR, etDescripcionR, etRutaR, etPaisR, etCiudadR;
    private Button btnAñadirNuevaRuta;
    private static int idRutaGeneral = 0;

    final String TAG = "REALTIMEDATABASE";

    //FIRE BASE
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private DatabaseReference myRefObtencionRutasID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rutasViewModel =
                ViewModelProviders.of(this).get(NuevaRutaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rutas, container, false);

        //inicializacion de las variables
        View view = inflater.inflate(R.layout.fragment_rutas, container, false);


        etNombreR = root.findViewById(R.id.etNombreNuevaRuta);
        etDescripcionR = root.findViewById(R.id.etDescripcionRutaNueva);
        etRutaR = root.findViewById(R.id.etRutaNuevaRuta);
        etPaisR = root.findViewById(R.id.etPaisRutaNueva);
        etCiudadR = root.findViewById(R.id.etCiudadRutaNueva);
        btnAñadirNuevaRuta = root.findViewById(R.id.btnAñadirNuevaRuta);


        //REAL TIME -> MODIFICACION PERFIL
        //FIRE BASE inicializaciones
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();


        //OBTENER TODAS LAS ID DE LAS RUTAS DEL USUARIO
        //REAL TIME

       /* myRefObtencionRutasID = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid()).child("rutas");
        //Log.e("Usuario actual: ", "" + currentUser.getUid());

        myRefObtencionRutasID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                //Obtencion de todos los usuarios
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                    myRefObtencionRutasID.child(ds.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Rutas usuarios = ds.getValue(Rutas.class);
                            //String nombre = usuarios.getNombreRuta();
                           // String id = usuarios.getIdRuta();
                            //Log.e("Nombre: ", "" + nombre);
                            //Log.e("Nombre: ", "" + id);
                            Log.e("Datos: ", "" + ds.getValue());
                            ds.getChildren().iterator().hasNext();
                            ds.getChildren().iterator().hasNext();
                            ds.getValue();
                            Log.e("Datos222: ", "" + ds.getValue());
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


        //AÑADIR NUEVA RUTA -> REAL TIME
        btnAñadirNuevaRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarRutaNuevaRT();
            }
        });


        return view;
    }

    //Obtencion de todos los usuarios
              /*   for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                   myRef.child(mAuth.getCurrentUser().getUid()).child(ds.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Usuarios usuarios = ds.getValue(Usuarios.class);
                            String nombre = usuarios.getNombre();
                            Log.e("Nombre: ", "" + nombre);
                            Log.e("Datos: ", "" + ds.getValue());
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }*/

    private void leerIdRutasDisponibles() {

    }

    private void guardarRutaNuevaRT() {

        String nombreComp = (etNombreR.getText().toString());
        String descripcionR = (etDescripcionR.getText().toString());
        String rutaR = (etRutaR.getText().toString());
        String paisR = (etPaisR.getText().toString());
        String ciudadR = (etCiudadR.getText().toString());


        if (nombreComp.isEmpty() || nombreComp.equals(" ")) {
            Toast.makeText(getContext(), "Tens que ingressa el nom de la ruta: " + etNombreR.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        } else if (!compTipoDatosString(nombreComp)) {
            Toast.makeText(getContext(), "El nom és invàlid: " + etNombreR.getText().toString() + ", tens d'introduir només lletres.",
                    Toast.LENGTH_SHORT).show();
        } else if (rutaR.isEmpty() || rutaR.equals(" ")) {
            Toast.makeText(getContext(), "La ruta és invàlida: " + etRutaR.getText().toString(),
                    Toast.LENGTH_SHORT).show();
        } else if (!paisR.isEmpty()) {
            if (!compTipoDatosString(paisR)) {
                Toast.makeText(getContext(), "El país es invàlid: " + etPaisR.getText().toString() + ", tens d'introduir només lletres.",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (!ciudadR.isEmpty()) {
            if (!compTipoDatosString(ciudadR)) {
                Toast.makeText(getContext(), "La ciutat es invàlida: " + etCiudadR.getText().toString() + ", tens d'introduir només lletres.",
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            //DatabaseReference currentUserDB = myRef.child(mAuth.getCurrentUser().getUid()).child("rutas");
            //idRutaGeneral++;
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
            Toast.makeText(getContext(), "Ruta agregada correctament: " + etNombreR.getText().toString(),
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
}