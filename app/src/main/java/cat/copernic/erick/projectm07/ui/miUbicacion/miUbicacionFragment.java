package cat.copernic.erick.projectm07.ui.miUbicacion;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cat.copernic.erick.projectm07.Localizacion;
import cat.copernic.erick.projectm07.NavegationDrawer;
import cat.copernic.erick.projectm07.NuevaRuta;
import cat.copernic.erick.projectm07.R;
import cat.copernic.erick.projectm07.RutaCompleta;

public class miUbicacionFragment extends Fragment {

    private miUbicacionViewModel toolsViewModel;

    private TextView tvDireccion, tvLongitud, tvLatitud;
    private Button bntGPS;

    private static final String SenseDenifir = "Sense denifir";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(miUbicacionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mi_ubicacion, container, false);

        //INICIALIZACIONES
        tvDireccion = root.findViewById(R.id.tvDireccionActual);
        tvDireccion.setText(SenseDenifir);
        tvLongitud = root.findViewById(R.id.tvLongitudActual);
        tvLongitud.setText(SenseDenifir);
        tvLatitud = root.findViewById(R.id.tvLatitudActual);
        tvLatitud.setText(SenseDenifir);


        bntGPS = root.findViewById(R.id.btnMostrarGPS);


        //Preubas



        bntGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                LocationListener locationListener = new Localizacion() {

                    @Override
                    public void onLocationChanged(Location loc) {
                        tvLatitud.setText("" + loc.getLatitude());
                        tvLongitud.setText("" + loc.getLongitude());

                        //OBTENER LA DIRECCION
                        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
                            try {
                                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                List<Address> list = geocoder.getFromLocation(
                                        loc.getLatitude(), loc.getLongitude(), 1);
                                if (!list.isEmpty()) {
                                    Address DirCalle = list.get(0);
                                    tvDireccion.setText("" + DirCalle.getAddressLine(0));
                                    String toastCalculandoCoo = miUbicacionFragment.this.getResources().getString(R.string.calculandoGSPRutaCompleta);
                                    Toast.makeText(getContext(), toastCalculandoCoo,
                                            Toast.LENGTH_SHORT).show();
                                    bntGPS.setEnabled(false);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        String toastCalculandoCoo = miUbicacionFragment.this.getResources().getString(R.string.calculandoGSPRutaCompleta);
                        Toast.makeText(getContext(), toastCalculandoCoo,
                                Toast.LENGTH_SHORT).show();
                    }

                    public void onProviderEnabled(String provider) {
                        String toastGpsActivado = miUbicacionFragment.this.getResources().getString(R.string.ubicacionActivadaRutaCompleta);
                        Toast.makeText(getContext(), toastGpsActivado,
                                Toast.LENGTH_SHORT).show();
                    }

                    public void onProviderDisabled(String provider) {
                        String toastGpsDesactivado = miUbicacionFragment.this.getResources().getString(R.string.ubicacionDesactivadaRutaCompleta);
                        Toast.makeText(getContext(), toastGpsDesactivado,
                                Toast.LENGTH_SHORT).show();
                    }

                };

                int permisosCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);

            }
        });

        int permisosCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);


        //Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {
        }

        return root;
    }
}






