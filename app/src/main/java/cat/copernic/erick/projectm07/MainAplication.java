package cat.copernic.erick.projectm07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainAplication extends AppCompatActivity {
    private TextView tvMostrar;
    private SharedPreferences pPreferences;



    // Menu Desplegable:
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aplication);

        //tvMostrar = findViewById(R.id.tvMuestraUser);

        pPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        recuperarDatos(); // Recuperamos datos de las SharedPreferences

        //MENU DESPLEGABLE
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab); // Boton flotante
        // Clicker de F.A.B
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    /**
     * Método en el que podemos ver como recuperar los datos que se encuentran en las SharedPreferences
     */
    public void recuperarDatos(){
        String userName = pPreferences.getString("NuevoUser", "vacio");
        String userPass = pPreferences.getString("NuevaPasswd", "vacio");
        String userAge = pPreferences.getString("NuevaEdad", "vacio");
        /*
        String datos = "Usuario: " + userP2 +
                        "\nPassword: " + passP2 +
                        "\nEdad: " + edadP2;
        tvMostrar.setText(datos);
        */
    }

    public void cerrarSesion(View view) {
        this.finish();
    }
}
