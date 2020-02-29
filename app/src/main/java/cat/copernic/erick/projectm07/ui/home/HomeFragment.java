package cat.copernic.erick.projectm07.ui.home;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cat.copernic.erick.projectm07.FireBaseDatabaHelper;
import cat.copernic.erick.projectm07.R;
import cat.copernic.erick.projectm07.RecyclerView_Config;
import cat.copernic.erick.projectm07.Rutas;
import cat.copernic.erick.projectm07.ui.Rutas.RutasFragment;

public class HomeFragment extends Fragment {

   /* RecyclerView mRecyclerView;
    Adapter mAdapter;


    //----
    private final LinkedList<String> mListaRutas = new LinkedList<>();

    //private RecyclerView mRecyclerView;
    //private Adapter mAdapter;
    */

   //NUEVA PRUEBA

   private RecyclerView mRecyclerView;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

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
/*
        //RECYCLERVIEW

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new Adapter(getActivity(), getMyList());
        //mAdapter = new Adapter(this, getMyList());
        mRecyclerView.setAdapter(mAdapter);

        //DEFAULT
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        //BOTON PARA AÑADIR UN NUEVO CARDVIEW

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        //.----


        return root;
    }

    //RecyclerView ARRAYLIST
/*
    private ArrayList<Model> getMyList(){
        ArrayList<Model> models = new ArrayList<>();


        Model m = new Model();
        m.setTitle("Ubicación 1");
        m.setDescription("Esta es la descipción de la ubicación 1");
        m.setImg(R.drawable.p1);
        models.add(m);

        //---
        m = new Model();
        m.setTitle("Ubicación 2");
        m.setDescription("Esta es la descipción de la ubicación 2");
        m.setImg(R.drawable.p2);
        models.add(m);

        //---
        m = new Model();
        m.setTitle("Ubicación 3");
        m.setDescription("Esta es la descipción de la ubicación 3");
        m.setImg(R.drawable.p3);
        models.add(m);

        //---
        m = new Model();
        m.setTitle("Ubicación 4");
        m.setDescription("Esta es la descipción de la ubicación 4");
        m.setImg(R.drawable.p4);
        models.add(m);

        //---
        m = new Model();
        m.setTitle("Ubicación 5");
        m.setDescription("Esta es la descipción de la ubicación 5");
        m.setImg(R.drawable.p5);
        models.add(m);

        return models;
    }*/

}