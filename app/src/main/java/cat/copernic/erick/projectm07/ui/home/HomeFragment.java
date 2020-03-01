package cat.copernic.erick.projectm07.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cat.copernic.erick.projectm07.FireBaseDatabaHelper;
import cat.copernic.erick.projectm07.R;
import cat.copernic.erick.projectm07.RecyclerView_Config;
import cat.copernic.erick.projectm07.Rutas;
import cat.copernic.erick.projectm07.Usuarios;
import cat.copernic.erick.projectm07.ui.Rutas.RutasFragment;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HomeViewModel homeViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //OLD

        mRecyclerView = root.findViewById(R.id.recyclerView);
        new FireBaseDatabaHelper().leerRutas(new FireBaseDatabaHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Rutas> rutas, List<String> keys) {
                //new RecyclerView_Config().setConfig(mRecyclerView,HomeFragment.this(),rutas,keys);
                new RecyclerView_Config().setConfig(mRecyclerView,getActivity(),rutas,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdate() {

            }

            @Override
            public void DataIsDelete() {

            }
        });



        return root;
    }

}