package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cat.copernic.erick.projectm07.ui.miUbicacion.miUbicacionFragment;

public class MapaUbicacionActual extends AppCompatActivity implements OnMapReadyCallback {

    //FIRE BASE
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;
    private DatabaseReference myRefDestino;
    private String direccionMapa;
    private double direccionLong;
    private double direccionLat;

    private GoogleMap mMap;
    //Float para fijar la posicion del ZOOM de la camara
    public static final float INITIAL_ZOOM = 16f;
    //TAG
    private static final String TAG = MapaUbicacionActual.class.getSimpleName();
    //PERMISO REQUERIDO PARA HABILITAR LA UBICACION DE GOOGLEMAPS
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_ubicacion_actual);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Toolbar toolbar = findViewById(R.id.toolbar);
       //setSupportActionBar(toolbar);

        //Habilitar Street View
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mapFragment).commit();


        //FIRE BASE inicializaciones
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.map);

        //Desencadena la carga asincrónica del mapa:
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void ObtenerCoordenadaD(){

        myRefDestino = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid());

        myRefDestino.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuarios usuarios = dataSnapshot.getValue(Usuarios.class);
                final String direccionU = usuarios.getDireccion();
                direccionMapa = direccionU;
                //Log.e("usuario: ", currentUser.getUid() + " DIRECCION: " + direccionMapa);
                try {
                    obtenerCoordenadasDestino(MapaUbicacionActual.this,direccionMapa);

                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    public LatLng obtenerCoordenadasDestino(Context context, String strAddress) {

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
            direccionLong = location.getLongitude();
            direccionLat = location.getLatitude();


        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }



    //GoogleMap map;
    Boolean actualPosicion = true;
    JSONObject jsonObject;
    Double longituI, latitudI;


    //Metodo onMpaReady para lanzar la el mapa cuando el 'map' este listo.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // MARCADOR CON LA LONGITUD Y LA LATITUD DE NICOLAU COPERNIC (41°34'12.3"N 1°59'47.5"E)

        //----,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {

                if (actualPosicion) {
                    latitudI = location.getLatitude();
                    longituI = location.getLongitude();
                    actualPosicion = false;

                    LatLng latLng = new LatLng(latitudI, longituI);

                    String toastAquiEstoy = MapaUbicacionActual.this.getResources().getString(R.string.aquiEstoy);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(toastAquiEstoy)).setDraggable(true);

//41°33'49.1"N 2°00'23.5"E
                    CameraPosition cameraPosition  = new CameraPosition.Builder()
                            .target(new LatLng(latitudI,longituI))
                            .zoom(14)
                            .bearing(30)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }


            }
        });


        //mMap.addMarker(new MarkerOptions().position().title("Aqui estoy"));

        //---,,,,
        LatLng nicolauCopernic = new LatLng(41.570118, 1.996618);
        //Con este metodo se crea el icono rojo ese. MARCADOR
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //Movemos la camara a nuestras coordenadas.
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nicolauCopernic,INITIAL_ZOOM));


        //PARA PONER EL ICONO
        /*GroundOverlayOptions CopernicOverLay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.cerrar_ses))
                //Establecemos la posicion 100metros.
                .position(nicolauCopernic, 20);*/

        //mMap.addGroundOverlay(CopernicOverLay );

        //Cagamos los metodo necesraios.

        setMapLongClick(mMap);
        //
        setPoiClick(mMap);
        //Llamamos al metodo para obtener el Style del Map
        setMapStyle(mMap);
        //LLamamos al metodo para habilitar la localizacion
        //enableMyLocation(mMap);
        enableMyLocation();
        //Llamamos al metodo setInfoWindowClickToPanorama(mMap):
        setInfoWindowClickToPanorama(mMap);

        //NUEVAS OPCIONES

    }


    //Implementacion del Marcador que al tocarlo te muestre las coordenadas
    private void setMapLongClick(final GoogleMap map) {
        //MARCADOR:
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            //Este metodo recive los argumentos de (nicolauCopernic) las LAT/LOG
            @Override
            public void onMapLongClick(LatLng latLng) {
                String snippet = String.format(Locale.getDefault(),

                        //PREGUNTAR SOBRE ESTAS COORDENADAS PARA AÑADIR MI UBICACION ACTUAL
                        "Lat: %1$.5f, Long: %2$.5f",
                        latLng.latitude,
                        latLng.longitude);

                //ESTO ES UN OBJETO CON LA POSICION ESTABLECIDA A PRIORI
                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.dropped_pin))
                        .snippet(snippet)
                        //Para dar color al marcador
                        .icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_BLUE)));
            }
        });

    }

    // Agregar oyente POI:
    private void setPoiClick(final GoogleMap map) {

        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {
                Marker poiMarker = mMap.addMarker(new MarkerOptions()
                        .position(poi.latLng)
                        .title(poi.name));
                poiMarker.showInfoWindow();

                //Etiquetar el marcador de PDI PARA habilitar Street View
                poiMarker.setTag("poi");
            }
        });

    }


    private void setMapStyle(GoogleMap map) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Error del Style Map.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "No se Escuentra: Style Map -> ", e);
        }
    }

    //Metodo para habilitar el seguimiento de ubicación y Street View:
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    //Anular el onRequestPermissionsResult()método. Si se otorga el permiso, llame al enableMyLocation():
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }
    */



    private void setInfoWindowClickToPanorama(GoogleMap map) {

        //Establecer OnInfoWindowClickListener de GoogleMap:
        map.setOnInfoWindowClickListener(
                new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        //vrifico si el marcador contiene la etiqueta de cadena en el metodo onPoiClick():
                        if (marker.getTag() == "poi") {

                            StreetViewPanoramaOptions options =
                                    new StreetViewPanoramaOptions().position(
                                            marker.getPosition());

                            SupportStreetViewPanoramaFragment streetViewFragment
                                    = SupportStreetViewPanoramaFragment
                                    .newInstance(options);

                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container,
                                            streetViewFragment)
                                    .addToBackStack(null).commit();

                        }
                    }
                });

    }

}
