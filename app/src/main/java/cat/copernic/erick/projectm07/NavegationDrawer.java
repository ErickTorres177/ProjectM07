package cat.copernic.erick.projectm07;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class NavegationDrawer extends AppCompatActivity {

    //private static final int CAMERA_REQUEST = 1888;
    private AppBarConfiguration mAppBarConfiguration;

    private Button btnLogOut;
    private ImageView imgUsuario;
    private TextView tvNombreUsuarioRT, tvUsuarioRT;

    final String TAG = "REALTIMEDATABASE";

    //fire base
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;
    private static  String userU;
    private static String nombreU;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegation_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        final NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //FIRE BASE inicializaciones
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = mFirebaseDatabase.getReference();
        //FirebaseUser user = mAuth.getCurrentUser();
        //userID = user.getUid();
        currentUser = mAuth.getCurrentUser();

        //REAL TIME

        myRef = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(currentUser.getUid());
        //Log.e("Usuario actual: ", "" + currentUser.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            //myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Usuarios usuarios = dataSnapshot.getValue(Usuarios.class);
                final String nombre = usuarios.getNombre();
                final String user = usuarios.getUsuario();
                Log.e("nombre: ", "" + nombre);
                Log.e("Usuario actual: ", "" + currentUser.getUid());
                Log.e("Usuario actual: ", "" + user);

                nombreU = nombre;
                userU = user;
                //Toast.makeText(NavegationDrawer.this, "cpm: "+ ": " + nombreU  + ", " + userU,
                        //Toast.LENGTH_SHORT).show();

                NavigationView navigationView = findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView tvUserCurrent =  headerView.findViewById(R.id.tvUsuarioCurrent);
                tvUserCurrent.setText(userU);
                TextView tvNombreCurrentUser =  headerView.findViewById(R.id.tvNombreUsuarioCurrent);
                tvNombreCurrentUser.setText(nombreU);

                //Obtencion de todos los usuarios
              /*   for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                   myRef.child(mAuth.getCurrentUser().getUid()).child(ds.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Usuarios usuarios = ds.getValue(Usuarios.class);
                            String nombre = usuarios.getNombre();
                            Log.e("Nombre: ", "" + nombre);
                            Log.e("Datos: ", "" + ds.getValue());
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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

    public void cerrarSesion(MenuItem item) {

        String toastMessage = NavegationDrawer.this.getResources().getString(R.string.adiosUsuario);
        Toast.makeText(NavegationDrawer.this, toastMessage + ": " + currentUser.getEmail(),
                Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void anadirRuta(View view) {
        Intent intent = new Intent(this, NuevaRuta.class);
        startActivity(intent);
    }

    public void abrirRutaCompleta(View view) {
        Intent intent = new Intent(this, RutaCompleta.class);
        startActivity(intent);
    }

}
