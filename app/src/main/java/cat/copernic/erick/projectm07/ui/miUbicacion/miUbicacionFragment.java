package cat.copernic.erick.projectm07.ui.miUbicacion;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.drm.ProcessedData;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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

import static cat.copernic.erick.projectm07.espera.espera;

public class miUbicacionFragment extends Fragment{

    private miUbicacionViewModel toolsViewModel;

    private TextView tvDireccion, tvLongitud, tvLatitud, tv1, tv2 ,tv3 ,tv4;
    private Button bntGPS;
    private ProgressDialog progressBar;

    private Geocoder geocoder;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private List<Address> list;


    private static final String SenseDenifir = "Sense denifir";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
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


        tv1 = root.findViewById(R.id.tvMiUbicacion1);
        tv1.setText(SenseDenifir);
        tv2 = root.findViewById(R.id.tvMiUbicacion2);
        tv2.setText(SenseDenifir);
        tv3 = root.findViewById(R.id.tvMiUbicacion3);
        tv3.setText(SenseDenifir);


        geocoder = null;
        locationManager = null;
        locationListener = null;
        list = null;

        espera(2000);

        bntGPS = root.findViewById(R.id.btnMostrarGPS);

        bntGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar = new ProgressDialog(miUbicacionFragment.this.getContext());
                progressBar.setCancelable(false);


                String toastCalculandoC = miUbicacionFragment.this.getResources().getString(R.string.progress1);
                String toastIniciando = miUbicacionFragment.this.getResources().getString(R.string.progress2);


                progressBar.setTitle(toastCalculandoC);
                progressBar.setMessage(toastIniciando);
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setMax(100);
                progressBar.setProgress(0);
                progressBar.show();

                //------------------------------------------------------------------------------------------------------------

                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                locationListener = new Localizacion() {

                    @Override
                    public void onLocationChanged(Location loc) {

                        espera(2000);
                        tvLatitud.setText("" + loc.getLatitude());
                        tvLongitud.setText("" + loc.getLongitude());


                        //
                        //OBTENER LA DIRECCION
                        //if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
                            try {
                                //geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                geocoder = new Geocoder(getContext(), Locale.getDefault());
                                list = geocoder.getFromLocation(
                                        loc.getLatitude(), loc.getLongitude(), 1);
                                if (!list.isEmpty()) {

                                    Address DirCalle = list.get(0);
                                    tvDireccion.setText("" + DirCalle.getAddressLine(0));

                                    tv1.setText(DirCalle.getAdminArea());
                                    tv2.setText(DirCalle.getCountryName());
                                    tv3.setText(DirCalle.getPostalCode());


                                    String toastCalculandoCoo = miUbicacionFragment.this.getResources().getString(R.string.calculandoGSPRutaCompleta);
                                    Toast.makeText(getContext(), toastCalculandoCoo,
                                            Toast.LENGTH_SHORT).show();
                                    //bntGPS.setEnabled(false);
                                    geocoder = null;
                                   /* try {
                                        geocoder.wait();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }*/
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                   // }

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
                //------------------------------------------------------------------------------------------------------------
                ProcessedData processedData = new ProcessedData();
                processedData.execute(10);

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


    public class ProcessedData extends AsyncTask<Integer, String, String>{

        @Override
        protected String doInBackground(Integer... integers) {
            int progreso = 0;
            int total = integers[0];

            while (progreso <= total){
                try {

                    Thread.sleep(450); //4.5 segundos

                }catch (InterruptedException e){

                }

                String toastCalculando = miUbicacionFragment.this.getResources().getString(R.string.progress3);
                String toastTeminando = miUbicacionFragment.this.getResources().getString(R.string.progress4);

                String  m = progreso % 2 == 0 ? toastCalculando:toastTeminando;

                this.publishProgress(String.valueOf(progreso), String.valueOf(total), m);

                progreso++;
            }
            return  "Done";
        }

        @Override
        protected void onProgressUpdate(String... values){

            super.onProgressUpdate(values);

            Float progress = Float.valueOf(values[0]);
            Float total = Float.valueOf(values[1]);

            String msg = values[2];

            progressBar.setProgress((int) ((progress / total) * 100));
            progressBar.setMessage(msg);

            if (values[0].equals(values[1])){
                progressBar.cancel();
            }
        }



    }

}






