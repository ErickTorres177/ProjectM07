package cat.copernic.erick.projectm07;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import cat.copernic.erick.projectm07.directionhelpers.FetchURL;
import cat.copernic.erick.projectm07.directionhelpers.TaskLoadedCallback;

public class ActivityMapsNavegacion extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {
    GoogleMap map;

    String direccionFinal;
    LatLng coordDireccFinal;
    String tituloRuta;

    Button btnMostrar;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    MarkerOptions start, end;

    Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_navigation);

        btnMostrar = findViewById(R.id.btn_mostrar);
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMapsNavegacion.this, "Ruta creada", Toast.LENGTH_SHORT).show();
                new FetchURL(ActivityMapsNavegacion.this).execute(getUrl(start.getPosition(), end.getPosition(), "driving"), "driving");
            }
        });

        direccionFinal = getIntent().getStringExtra("direccion_final");
        tituloRuta = getIntent().getStringExtra("titulo");

        GeoLocation geoLocation = new GeoLocation();
        geoLocation.getAdress(direccionFinal, getApplicationContext(), new GeoHandler());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
/*
        start = new MarkerOptions().position(new LatLng(currentLocation.getLatitude()
                , currentLocation.getLongitude())).title(String.valueOf(R.string.localizacionUsuario));
        end = new MarkerOptions().position(coordDireccFinal).title(tituloRuta);

        String url = getUrl(start.getPosition(), end.getPosition(), "walking");
        new FetchURL(ActivityMapsNavegacion.this).execute(url, "walking");

 */
    }

    /**
     * Comprovacion de los permisos de localizacion y obtencion de la localizacion del usuario
     */
    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(ActivityMapsNavegacion.this);
                }
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng posUsuario = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(posUsuario, 15));

        start = new MarkerOptions().position(new LatLng(currentLocation.getLatitude()
                , currentLocation.getLongitude())).title(String.valueOf(R.string.localizacionUsuario));
        end = new MarkerOptions().position(coordDireccFinal).title(tituloRuta);

        map.addMarker(start);
        map.addMarker(end);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }

    }
    public void handleRegresar(View view) {
        finish();
    }

    private class GeoHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String lat, longit;
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    lat = bundle.getString("latitud");
                    longit = bundle.getString("longitud");
                    break;
                default:
                    lat = null;
                    longit = null;
            }
            coordDireccFinal = new LatLng(Double.parseDouble(lat), Double.parseDouble(longit));
        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?"
                + parameters + "&key=" + getString(R.string.map_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }
}
