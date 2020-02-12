package cat.copernic.erick.projectm07.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import cat.copernic.erick.projectm07.R;

public class HomeFragment extends Fragment {

    RecyclerView mRecyclerView;
    Adapter mAdapter;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);


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
        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/



       return root;
    }

    //RecyclerView ARRAYLIST
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
    }

}