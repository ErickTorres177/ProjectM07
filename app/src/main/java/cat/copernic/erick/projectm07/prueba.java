package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class prueba extends AppCompatActivity {

    private EditText busqueda;
    private TextView a, b;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        busqueda = findViewById(R.id.etAddres);
        a = findViewById(R.id.tvLat);
        b = findViewById(R.id.tvLong);
        btn = findViewById(R.id.btnGet);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationFromAddress(getApplicationContext(), "Terrassa");
            }
        });

    }


    public LatLng getLocationFromAddress(Context context, String strAddress) {

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
            a.setText(String.valueOf(location.getLongitude()));
            b.setText(String.valueOf(location.getLatitude()));

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


}
