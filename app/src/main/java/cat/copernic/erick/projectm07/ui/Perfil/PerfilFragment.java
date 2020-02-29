package cat.copernic.erick.projectm07.ui.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import cat.copernic.erick.projectm07.ModificarPerfil;
import cat.copernic.erick.projectm07.NuevaRuta;
import cat.copernic.erick.projectm07.R;
import cat.copernic.erick.projectm07.Usuarios;

public class PerfilFragment extends Fragment {

    private PerfilViewModel shareViewModel;

    private TextView tvNombreU, tvEdadU, tvSexoU, tvDireccionU;
    private Button btnIrModificarPerfil;

    final String TAG = "REALTIMEDATABASE";

    //FIRE BASE
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(PerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        //final TextView textView = root.findViewById(R.id.text_share);
        /*shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        //INICIALIZACIONES
        btnIrModificarPerfil = root.findViewById(R.id.btnModificar_pefil);
        tvNombreU = root.findViewById(R.id.tvNombreUsuario_perfil);
        tvEdadU = root.findViewById(R.id.tvEdadUsuario_perfil);
        tvSexoU = root.findViewById(R.id.tvSexoUsuario_perfil);
        tvDireccionU = root.findViewById(R.id.tvDireccionUsuario_perfil);

        //ON CLICK LISTENER
        btnIrModificarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ModificarPerfil.class);
                startActivity(intent);
            }
        });

        //REAL TIME -> PERFIL
        //FIRE BASE inicializaciones
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();

        //REAL TIME
        myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid());
        //Log.e("Usuario actual: ", "" + currentUser.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuarios usuarios = dataSnapshot.getValue(Usuarios.class);
                final String nombreU = usuarios.getNombre();
                final String edadU = usuarios.getEdad();
                final String sexoU = usuarios.getSexo();
                final String direccionU = usuarios.getDireccion();
                //Log.e("nombre: ", "" + nombre);
                //Log.e("Usuario actual: ", "" + currentUser.getUid());
                //Log.e("Usuario actual: ", "" + user);
                btnIrModificarPerfil.setText(nombreU);
                tvNombreU.setText(nombreU);
                tvEdadU.setText(edadU);
                tvSexoU.setText(sexoU);
                tvDireccionU.setText(direccionU);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return root;
}

    public void abrirEditarPerfil() {

    }

}