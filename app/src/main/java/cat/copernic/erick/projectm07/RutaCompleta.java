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
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static cat.copernic.erick.projectm07.espera.espera;

public class RutaCompleta extends AppCompatActivity {

    private TextView tvRitaId, tvNombreR, tvDescripcionR, tvRutaR, tvPaisR, tvCiudadR;
    private ImageView imgIrRuta;

    final String TAG = "REALTIMEDATABASE";

    //FIRE BASE
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;
    private DatabaseReference myRefDestino;

    private String key;
    private String idRutaPasar;
    private String nombreR;
    private String descripcionR;
    private String rutaR;
    private String paisR;
    private String ciudadR;

    //IMPORTANTE -> LOG Y LAT FINALES A ENVIAR A LA RUTA
    //ORIGEN
    private static Double logintud;
    private static Double latitud;
    private static String finalDireccionRuta;
    private static String finalDestinoRutaFB;
    private static Double logintudDestino;
    private static Double latitudDestino;

    private static Double logintudUbicacionActual;
    private static Double latitudUbicacionActual;

    //JSON
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_completa);

        //FIRE BASE inicializaciones
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();

        key = getIntent().getStringExtra("key");
        idRutaPasar = getIntent().getStringExtra("idRuta");
        nombreR = getIntent().getStringExtra("nombreRuta");
        descripcionR = getIntent().getStringExtra("descripcionRuta");
        rutaR = getIntent().getStringExtra("ruta");
        paisR = getIntent().getStringExtra("paisRuta");
        ciudadR = getIntent().getStringExtra("ciudadRuta");


        tvRitaId = findViewById(R.id.tvIdRuta_completa);
        tvRitaId.setText(idRutaPasar);

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

        //INICIALIZACION REQUEST VOLLEY
        request = Volley.newRequestQueue(getApplicationContext());

        //----------------------------------------------------------------------------------------------------------------------------------------
        //OBTENER LAS COORDENADAS DE DESTINO -> RUTA DE FIRE BASE
        ObtenerCoordenadaD();
        //----------------------------------------------------------------------------------------------------------------------------------------
        imgIrRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarAlertTipoAdrres();
            }
        });
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    //OBTENER COORDENADAS DESTINO -> FIRE BASE RUTA -> RUTA
    public void ObtenerCoordenadaD() {

        myRefDestino = FirebaseDatabase.getInstance().getReference().child("Usuarios")
                .child(currentUser.getUid()).child("rutas").child(idRutaPasar);

        myRefDestino.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Usuarios usuarios = dataSnapshot.getValue(Usuarios.class);
                //final String direccionU = usuarios.getDireccion();
                //finalDireccionRuta = direccionU;
                //Log.e("usuario: ", currentUser.getUid() + " direcion DESTINO FINAL: " + finalDireccionRuta);

                Rutas rutas = dataSnapshot.getValue(Rutas.class);
                final String direccionDestino = rutas.getRuta();
                finalDestinoRutaFB = direccionDestino;
                //Log.e("usuario RUTAAA DESTINO:", currentUser.getUid() + " direcion DESTINO FINAL: " + finalDestinoRutaFB);
                try {
                    obtenerCoordenadasDestino(RutaCompleta.this, finalDestinoRutaFB);

                } catch (Exception e) {
                    //Log.w(TAG, "------RUTA INVALIDA------");
                    mostrarAlertTipoAdrres();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    //OBTENER COORDENADAS DESTINO -> RUTA RUTA -> FIRE BASE
    public LatLng   obtenerCoordenadasDestino(Context context, String strAddress) {

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
            logintudDestino = location.getLongitude();
            latitudDestino = location.getLatitude();

            //----------------------

            Utilidades.coordenadas.setLatitudFinal(latitudDestino);
            Utilidades.coordenadas.setLongitudFinal(logintudDestino);

            //---------------------

            //longitud y latitud ORIGEN
           // Log.e("usuario DESTINO: ", currentUser.getUid() + " lon:: " + logintudDestino);
           // Log.e("usuario DESTINO: ", currentUser.getUid() + " lat:: " + latitudDestino);

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    public void mostrarAlertTipoAdrres() {
        final String[] lista_item = {"Utilitza ubicació actual", "Utilitza la meva adreça"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RutaCompleta.this);
        mBuilder.setTitle("Tria l' adreça: ");
        mBuilder.setSingleChoiceItems(lista_item, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    goToRutaUbicacionActual();
                } else if (i == 1) {
                    goToRuta();
                }
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = mBuilder.create();
        try {
            alertDialog.show();
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    public void goToRutaUbicacionActual() {
        //Utilidades.coordenadas.getLatitudFinal();
        //Utilidades.coordenadas.getLongitudFinal();

        obtenerUbicacionActual();

        String stringLatInicialUbicacionActual = String.valueOf(Utilidades.coordenadas.getLatitudInicial());
        String stringLongInicialUbicacionActual = String.valueOf(Utilidades.coordenadas.getLongitudInicial());

        String stringLatDestino = String.valueOf(Utilidades.coordenadas.getLatitudFinal());
        String stringLongDestino = String.valueOf(Utilidades.coordenadas.getLongitudFinal());

        webServiceObtenerRuta(stringLatInicialUbicacionActual, stringLongInicialUbicacionActual, stringLatDestino, stringLongDestino);
        // webServiceObtenerRuta(String.valueOf(latitud),String.valueOf(logintud),
        //String.valueOf(latitudDestino),String.valueOf(logintudDestino));
        //webServiceObtenerRuta(latitud.toString(),logintud.toString(),
        //latitudDestino.toString(),logintudDestino.toString());

        espera(3000);
        Intent miIntent = new Intent(RutaCompleta.this, MapsActivity.class);
        startActivity(miIntent);
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    public void obtenerUbicacionActual() {
        LocationManager locationManager = (LocationManager) RutaCompleta.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new Localizacion() {

            @Override
            public void onLocationChanged(Location loc) {

                logintudUbicacionActual = loc.getLongitude();
                latitudUbicacionActual = loc.getLatitude();
                //Log.e("ubicacion actual coo: ", "LON: " + logintudUbicacionActual);
                //Log.e("ubicacion actual coo: ", " LAT: "+ latitudUbicacionActual);

                Utilidades.coordenadas.setLatitudInicial(latitudUbicacionActual);
                Utilidades.coordenadas.setLongitudInicial(logintudUbicacionActual);

                double lat = Utilidades.coordenadas.getLatitudInicial();
                double lot = Utilidades.coordenadas.getLongitudInicial();

                //Log.e("set LONG", "LON: " + lot);
                //Log.e("SET LAT  ", " LAT: "+ lat);
            }
           /* public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            public void onProviderEnabled(String provider) {
            }
            public void onProviderDisabled(String provider) {
            }*/
        };
        int permisosCheck = ContextCompat.checkSelfPermission(RutaCompleta.this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    public void goToRuta() {
        obtenerDirecionFB();
        String stringLatInicialUbicacionActual = String.valueOf(Utilidades.coordenadas.getLatitudInicial());
        String stringLongInicialUbicacionActual = String.valueOf(Utilidades.coordenadas.getLongitudInicial());

        String stringLatDestino = String.valueOf(Utilidades.coordenadas.getLatitudFinal());
        String stringLongDestino = String.valueOf(Utilidades.coordenadas.getLongitudFinal());

        webServiceObtenerRuta(stringLatInicialUbicacionActual, stringLongInicialUbicacionActual, stringLatDestino, stringLongDestino);
        // webServiceObtenerRuta(String.valueOf(latitud),String.valueOf(logintud),
        //String.valueOf(latitudDestino),String.valueOf(logintudDestino));
        //webServiceObtenerRuta(latitud.toString(),logintud.toString(),
        //latitudDestino.toString(),logintudDestino.toString());


        Intent miIntent = new Intent(RutaCompleta.this, MapsActivity.class);
        startActivity(miIntent);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    //OBTENER COORDENADAS ORIGEN -> DIRECCION USUARIO
    public void obtenerDirecionFB() {
        myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuarios usuarios = dataSnapshot.getValue(Usuarios.class);
                final String direccionU = usuarios.getDireccion();
                finalDireccionRuta = direccionU;
                //Log.e("usuario: ", currentUser.getUid() + " direcion: " + finalDireccionRuta);
                try {
                    obtenerCoordenadasFromAdrres(RutaCompleta.this, finalDireccionRuta);
                } catch (Exception e) {
                    //Log.w(TAG, "------RUTA INVALIDA------");
                    mostrarAlertTipoAdrres();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    //OBTENER COORDENADAS ORIGEN
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
            logintud = location.getLongitude();
            latitud = location.getLatitude();
            //----------------------
            Utilidades.coordenadas.setLatitudInicial(latitud);
            Utilidades.coordenadas.setLongitudInicial(logintud);
            //------------------
            //longitud y latitud ORIGEN
            //Log.e("usuario: ", currentUser.getUid() + " lon:: " + logintud);
           // Log.e("usuario: ", currentUser.getUid() + " lat:: " + latitud);

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return p1;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------
    private void webServiceObtenerRuta(String latitudInicial, String longitudInicial, String latitudFinal, String longitudFinal) {

        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + latitudInicial + "," + longitudInicial
                + "&destination=" + latitudFinal + "," + longitudFinal;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Este método PARSEA el JSONObject que retorna del API de Rutas de Google devolviendo
                //una lista del lista de HashMap Strings con el listado de Coordenadas de Lat y Long,
                //con la cual se podrá dibujar pollinas que describan la ruta entre 2 puntos.
                JSONArray jRoutes = null;
                JSONArray jLegs = null;
                JSONArray jSteps = null;

                try {

                    jRoutes = response.getJSONArray("routes");

                    for (int i = 0; i < jRoutes.length(); i++) {
                        jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                        List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

                        for (int j = 0; j < jLegs.length(); j++) {
                            jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                            for (int k = 0; k < jSteps.length(); k++) {
                                String polyline = "";
                                polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                                List<LatLng> list = decodePoly(polyline);

                                for (int l = 0; l < list.size(); l++) {
                                    HashMap<String, String> hm = new HashMap<String, String>();
                                    hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                                    hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                                    path.add(hm);
                                }
                            }
                            Utilidades.routes.add(path);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String toastErrorConn = RutaCompleta.this.getResources().getString(R.string.errorConeccionRutaCompleta);
                Toast.makeText(getApplicationContext(), toastErrorConn + error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                //Log.d("ERROR: ", error.toString());
            }
        }
        );

        request.add(jsonObjectRequest);
    }

    public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
        //Este método PARSEA el JSONObject que retorna del API de Rutas de Google devolviendo
        //una lista del lista de HashMap Strings con el listado de Coordenadas de Lat y Long,
        //con la cual se podrá dibujar pollinas que describan la ruta entre 2 puntos.
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

                /** Traversing all legs */
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline = "";
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        /** Traversing all points */
                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                            hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                            path.add(hm);
                        }
                    }
                    Utilidades.routes.add(path);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return Utilidades.routes;
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------

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

    public void handleRegresarRutas(View view) {
        finish();
    }
}
