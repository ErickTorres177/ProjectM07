package cat.copernic.erick.projectm07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cat.copernic.erick.projectm07.ui.home.HomeFragment;

public class RutaCompleta extends AppCompatActivity {

    private TextView tvNombreR, tvDescripcionR, tvRutaR, tvPaisR, tvCiudadR;
    private ImageView imgIrRuta;

    final String TAG = "REALTIMEDATABASE";

    //FIRE BASE
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;

    private String key;
    private String nombreR;
    private String descripcionR;
    private String rutaR;
    private String paisR;
    private String ciudadR;

    //IMPORTANTE -> LOG Y LAT FINALES A ENVIAR A LA RUTA
    private static Double logintud;
    private static Double latitud;
    private static String finalDireccionRuta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_completa);

        //FIRE BASE inicializaciones
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();

        key = getIntent().getStringExtra("key");
        nombreR = getIntent().getStringExtra("nombreRuta");
        descripcionR = getIntent().getStringExtra("descripcionRuta");
        rutaR = getIntent().getStringExtra("ruta");
        paisR = getIntent().getStringExtra("paisRuta");
        ciudadR = getIntent().getStringExtra("ciudadRuta");

        tvNombreR = findViewById(R.id.tvRuta_rutaCompleta);
        tvNombreR.setText(nombreR);
        tvDescripcionR = findViewById(R.id.tvDescRutaCompleta);
        tvDescripcionR.setText(descripcionR);
        tvRutaR = findViewById(R.id.tvRutaRutaCompleta);
        tvRutaR.setText(rutaR);
        tvPaisR = findViewById(R.id.tvPaisRutaCompleta);
        tvPaisR.setText(paisR);
        tvCiudadR = findViewById(R.id.tvCiudadRutaCompleta);
        tvCiudadR.setText(ciudadR);

        //PREGUNTAR SI QUIERE LA RUTA CON LA DIRECCION O CON LA UBICACION ACTUAL
        imgIrRuta = findViewById(R.id.imgIrGoogleMaps);
        imgIrRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationManager locationManager = (LocationManager) RutaCompleta.this.getSystemService(Context.LOCATION_SERVICE);
                LocationListener locationListener = new Localizacion() {

                    @Override
                    public void onLocationChanged(Location loc) {

                        logintud = loc.getLongitude();
                        latitud = loc.getLatitude();
                        Log.e("usuario: ", currentUser.getUid() + " lon: " + logintud);
                        Log.e("usuario: ", currentUser.getUid() + " lat: " + latitud);

                        //OBTENER LA DIRECCION
                        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
                            try {
                                Geocoder geocoder = new Geocoder(RutaCompleta.this, Locale.getDefault());
                                List<Address> list = geocoder.getFromLocation(
                                        loc.getLatitude(), loc.getLongitude(), 1);
                                if (!list.isEmpty()) {
                                    Address DirCalle = list.get(0);
                                    finalDireccionRuta = DirCalle.getAddressLine(0);
                                    Log.e("usuario: ", currentUser.getUid() + " direcion: " + finalDireccionRuta);
                                    Log.e("usuario: ", currentUser.getUid() + " lon: " + logintud);
                                    Log.e("usuario: ", currentUser.getUid() + " lat: " + latitud);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        Toast.makeText(RutaCompleta.this, "Calculant coordenades.",
                                Toast.LENGTH_SHORT).show();
                    }

                    public void onProviderEnabled(String provider) {
                        Toast.makeText(RutaCompleta.this, "Ubicació GPS activada.",
                                Toast.LENGTH_SHORT).show();
                    }

                    public void onProviderDisabled(String provider) {
                        Toast.makeText(RutaCompleta.this, "La ubicació GPS està desactivat.",
                                Toast.LENGTH_SHORT).show();
                    }
                };

                int permisosCheck = ContextCompat.checkSelfPermission(RutaCompleta.this, Manifest.permission.ACCESS_FINE_LOCATION);

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);


                //int permisosCheck = ContextCompat.checkSelfPermission(RutaCompleta.this, Manifest.permission.ACCESS_FINE_LOCATION);


                //Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(RutaCompleta.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(RutaCompleta.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                    } else {
                        ActivityCompat.requestPermissions(RutaCompleta.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                } else {
                }




                mostrarAlertTipoAdrres();
            }
        });





    }

   /* public void obtenerUbicacionActual() {
        LocationManager locationManager = (LocationManager) RutaCompleta.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new Localizacion() {

            @Override
            public void onLocationChanged(Location loc) {

                logintud = loc.getLongitude();
                latitud = loc.getLatitude();
                Log.e("usuario: ", currentUser.getUid() + " lon: " + logintud);
                Log.e("usuario: ", currentUser.getUid() + " lat: " + latitud);

                //OBTENER LA DIRECCION
                if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
                    try {
                        Geocoder geocoder = new Geocoder(RutaCompleta.this, Locale.getDefault());
                        List<Address> list = geocoder.getFromLocation(
                                loc.getLatitude(), loc.getLongitude(), 1);
                        if (!list.isEmpty()) {
                            Address DirCalle = list.get(0);
                            finalDireccionRuta = DirCalle.getAddressLine(0);
                            Log.e("usuario: ", currentUser.getUid() + " direcion: " + finalDireccionRuta);
                            Log.e("usuario: ", currentUser.getUid() + " lon: " + logintud);
                            Log.e("usuario: ", currentUser.getUid() + " lat: " + latitud);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(RutaCompleta.this, "Calculant coordenades.",
                        Toast.LENGTH_SHORT).show();
            }

            public void onProviderEnabled(String provider) {
                Toast.makeText(RutaCompleta.this, "Ubicació GPS activada.",
                        Toast.LENGTH_SHORT).show();
            }

            public void onProviderDisabled(String provider) {
                Toast.makeText(RutaCompleta.this, "La ubicació GPS està desactivat.",
                        Toast.LENGTH_SHORT).show();
            }

        };

        int permisosCheck = ContextCompat.checkSelfPermission(RutaCompleta.this, Manifest.permission.ACCESS_FINE_LOCATION);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);


        //int permisosCheck = ContextCompat.checkSelfPermission(RutaCompleta.this, Manifest.permission.ACCESS_FINE_LOCATION);


        //Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(RutaCompleta.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RutaCompleta.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(RutaCompleta.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {
        }

    }*/

    public void obtenerDirecionFB() {

        myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuarios usuarios = dataSnapshot.getValue(Usuarios.class);
                final String direccionU = usuarios.getDireccion();
                finalDireccionRuta = direccionU;
                Log.e("usuario: ", currentUser.getUid() + " direcion:: " + finalDireccionRuta);
                try {
                    obtenerCoordenadasFromAdrres(RutaCompleta.this,finalDireccionRuta);

                }catch (Exception e){
                    Log.w(TAG, "------RUTA INVALIDA------");
                    mostrarAlertTipoAdrres();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void mostrarAlertTipoAdrres() {
        final String[] lista_item = {"Utilitza ubicació actual", "Utilitza la meva adreça"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RutaCompleta.this);
        mBuilder.setTitle("Tria l' adreça: ");
        mBuilder.setSingleChoiceItems(lista_item, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    //obtenerUbicacionActual();
                } else if (i == 1) {
                    obtenerDirecionFB();
                }
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }


    public LatLng obtenerCoordenadasFromAdrres(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
            logintud= location.getLongitude();
            latitud = location.getLatitude();
            Log.e("usuario: ", currentUser.getUid() + " lon:: " + logintud);
            Log.e("usuario: ", currentUser.getUid() + " lat:: " + latitud);

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }



    public void handleRegresarRutas(View view) {
        finish();
    }

    /*public void handleAbrirMapa(View view) {
        Intent intent = new Intent(this, ActivityMapsNavegacion.class);
        intent.putExtra("direccion_final",tvRutaR.getText());
        intent.putExtra("titulo", tvNombreR.getText());
        startActivity(intent);
    }*/


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