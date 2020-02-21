package cat.copernic.erick.projectm07;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import cat.copernic.erick.projectm07.ui.Rutas.RutasFragment;

public class NavegationDrawer extends AppCompatActivity {

    private SharedPreferences pPreferences;
    private AppBarConfiguration mAppBarConfiguration;
    private AppBarConfiguration mAppBarConfiguration2;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegation_drawer);

       /* pPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        recuperarDatos(); // Recuperamos datos de las SharedPreferences*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        /*
        ESTE FAB (BOTON), NOS PODRIA SERVIR EN UN FUTURO -> NO BORRAR
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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

        //btnLogOut = findViewById(R.id.bntLogOut);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegation_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Método en el que podemos ver como recuperar los datos que se encuentran en las SharedPreferences
     */
    public void recuperarDatos() {
        String userName = pPreferences.getString("NuevoUser", "vacio");
        String userPass = pPreferences.getString("NuevaPasswd", "vacio");
        String userAge = pPreferences.getString("NuevaEdad", "vacio");
    }

    //public void cerrarSesion(View view) {
      //  this.finish();
    //}

    public void anadirRuta(View view) {
        Intent intent = new Intent(this, RutasFragment.class);
        startActivity(intent);
    }

    public void cerrarSesion(MenuItem item) {
        Toast.makeText(NavegationDrawer.this, "Adeu usuari: " + currentUser.getEmail(),
                Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
