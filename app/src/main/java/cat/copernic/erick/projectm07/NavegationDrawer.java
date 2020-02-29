package cat.copernic.erick.projectm07;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class NavegationDrawer extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;

    private AppBarConfiguration mAppBarConfiguration;


    private Button btnLogOut;
    private ImageView imgUsuario;
    private TextView tvNombreUsuarioRT, tvUsuarioRT;

    final String TAG = "REALTIMEDATABASE";

    private ListView mListView;

    //fire base
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegation_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // tvNombreUsuarioRT = findViewById(R.id.tvNombreUsuarioRealTime);
        //tvUsuarioRT = findViewById(R.id.tvUsuarioRealTime);
        //mListView =

        //
        imgUsuario = findViewById(R.id.imgUsuario);

        /*imgUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                if(intent.resolveActivity(getPackageManager()) != null){
                        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen de perfil."),
                                CAMERA_REQUEST);
                }
            }
        });*/


        //FIRE BASE inicializaciones
        /*mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        currentUser = mAuth.getCurrentUser();*/

        //REAL TIME




        //DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference myRefGeneral = myRef.child("Usuarios");
        //DatabaseReference myRefObtencion = myRefGeneral.child("nombre");


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

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //REAL TIME

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //myRef = database.getReference("Usuarios");



       /*
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String nombre = dataSnapshot.child("").getValue(String.class);

                tvNombreUsuarioRT.setText(nombre);
                Log.d(TAG, "Value is: " + nombre);


                //valor = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + valor);
                etDatos.setText(valor);

                String = dataSnapshot.getValue(String.class);

                tvNombreUsuarioRT.setText(usuarioInformacion.getUser());

                showDatabaseSnapshot(dataSnapshot);

                valor = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + valor);
                Log.d(TAG, "Value 2 is: " + valor2);
                tvNombreUsuarioRT.setText(valor);
                tvUsuarioRT.setText(valor2);

                Usuario usuarioInformacion = new Usuario();
                //showDatabaseSnapshot(dataSnapshot);
                valor = dataSnapshot.child("usuario").getValue(String.class);
                //dataSnapshot.child("")
                Log.d(TAG, "Value is: " + valor);
                tvNombreUsuarioRT.setText(valor);
                //mUsuario = dataSnapshot.getValue(Usuario.class);
                //showDatabaseSnapshot(mUsuario);
                String valor = dataSnapshot.child("nombre").getValue(String.class);
                //Toast.makeText(NavegationDrawer.this, "usuario" + valor,
                        //Toast.LENGTH_SHORT).show();
                //tvNombreUsuarioRT.setText(valor);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
    }



    /*public void showDatabaseSnapshot(Usuario dataSnapshot) {
        //for (DataSnapshot ds : dataSnapshot.getChildren()) {

            Usuario usuarioInformacion = new Usuario();
            usuarioInformacion.setUser(ds.child(userID).getValue(Usuario.class).getUser());
            usuarioInformacion.setNombreUsuario(ds.child(userID).getValue(Usuario.class).getNombreUsuario());
            usuarioInformacion.setEdad(ds.child(userID).getValue(Usuario.class).getEdad());

            Log.w(TAG, "usuario." +  usuarioInformacion.getUser());
            Log.w(TAG, "nombre." + usuarioInformacion.getNombreUsuario());
            Log.w(TAG, "edad." + usuarioInformacion.getEdad());

            ArrayList<String> listaU = new ArrayList<>();
            listaU.add(usuarioInformacion.getUser());
            listaU.add(usuarioInformacion.getNombreUsuario());
            listaU.add(String.valueOf(usuarioInformacion.getEdad()));

            //String z = dataSnapshot.getValue(String.class);
            //tvNombreUsuarioRT.setText(dataSnapshot.getNombreUsuario());

        //}
    }*/

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
}
