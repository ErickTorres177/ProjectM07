package cat.copernic.erick.projectm07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import cat.copernic.erick.projectm07.ui.home.HomeFragment;

public class RutaCompleta extends AppCompatActivity {

    private TextView tvNombreR, tvDescripcionR, tvRutaR, tvPaisR, tvCiudadR;

    final String TAG = "REALTIMEDATABASE";

    //FIRE BASE
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_completa);

        tvNombreR = findViewById(R.id.tvRuta_nombre);
        tvDescripcionR = findViewById(R.id.tvDescRutaCompleta);
        tvRutaR = findViewById(R.id.tvRutaRutaCompleta);
        tvPaisR = findViewById(R.id.tvPaisRutaCompleta);
        tvCiudadR = findViewById(R.id.tvCiudadRutaCompleta);

        //Recuperar Ruta completa
      /*  String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("idRutaC");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("idRutaC");
        }

        //REAL TIME -> leer card viw -> usuario actual
        //FIRE BASE inicializaciones
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();


        //REAL TIME
        myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid())
                .child("rutas").child(newString);
        //Log.e("Usuario actual: ", "" + currentUser.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Rutas rutas = dataSnapshot.getValue(Rutas.class);
                final String nombreR = rutas.getNombreRuta();
                final String descripcionR = rutas.getDescripcionRuta();
                final String rutaR = rutas.getRuta();
                final String paisR = rutas.getPaisRuta();
                final String ciudadR = rutas.getCiudadRuta();

                tvNombreR.setText(nombreR);
                tvDescripcionR.setText(descripcionR);
                tvRutaR.setText(rutaR);
                tvPaisR.setText(paisR);
                tvCiudadR.setText(ciudadR);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/


    }

    public void handleRegresarRutas(View view) {
        //Intent intent = new Intent(this, HomeFragment.class);
        //startActivity(intent);
        finish();
    }


    private Context mContext;
    private RutasCompletasAdapter mRutasAdapter;


    public void setConfig(RecyclerView recyclerView, Context context, List<Rutas> rutasCompletas, List<String> keys) {
        mContext = context;
        mRutasAdapter = new RutasCompletasAdapter(rutasCompletas, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mRutasAdapter);

    }

    class RutasCompletasItemView extends RecyclerView.ViewHolder {
        //RUTA COMPLETA
        private TextView mRutaCompleta_id;
        private TextView mRutaCompleta_nombre;
        private TextView mRutaCompleta_descripcion;
        private TextView mRutaCompleta_ruta;
        private TextView mRutaCompleta_pais;
        private TextView mRutaCompleta_ciudad;

        private String key;

        public RutasCompletasItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.activity_ruta_completa, parent, false));


            //RUTA COMPLETA
            //mRutaCompleta_id = itemView.findViewById(R.id.tvRutaCompleta);
            mRutaCompleta_nombre = itemView.findViewById(R.id.tvNombreNuevaRuta);
            mRutaCompleta_descripcion = itemView.findViewById(R.id.tvDescRutaCompleta);
            mRutaCompleta_ruta = itemView.findViewById(R.id.tvRutaRutaCompleta);
            mRutaCompleta_pais = itemView.findViewById(R.id.tvPaisRutaCompleta);
            mRutaCompleta_ciudad = itemView.findViewById(R.id.tvCiudadRutaCompleta);
        }

        public void bind(Rutas rutasCompletas, String key) {
            //RUTA COMPLETA
            //mRuta_id.setText(rutas.getIdRuta());
            mRutaCompleta_nombre.setText(rutasCompletas.getNombreRuta());
            mRutaCompleta_descripcion.setText(rutasCompletas.getDescripcionRuta());
            mRutaCompleta_ruta.setText(rutasCompletas.getRuta());
            mRutaCompleta_pais.setText(rutasCompletas.getCiudadRuta());
            mRutaCompleta_ciudad.setText(rutasCompletas.getPaisRuta());

            this.key = key;
        }
    }

    class RutasCompletasAdapter extends RecyclerView.Adapter<RutasCompletasItemView> {
        private List<Rutas> mrutasCompletasList;
        private List<String> mKeys;

        public RutasCompletasAdapter(List<Rutas> mrutasCompletasList, List<String> mKeys) {
            this.mrutasCompletasList = mrutasCompletasList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public RutasCompletasItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RutasCompletasItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RutasCompletasItemView holder, int position) {
            holder.bind(mrutasCompletasList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mrutasCompletasList.size();
        }
    }


}
