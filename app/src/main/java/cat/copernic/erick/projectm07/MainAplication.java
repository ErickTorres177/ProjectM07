package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainAplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aplication);
        SharedPreferences preferences= getSharedPreferences("misPreferencias", MODE_PRIVATE);
        String display = preferences.getString("display", "");

        TextView displayInfo = findViewById(R.id.tvMuestraUser);
        displayInfo.setText(display);



    }
}
