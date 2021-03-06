package cat.copernic.erick.projectm07.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cat.copernic.erick.projectm07.FireBaseDatabaHelper;
import cat.copernic.erick.projectm07.LoginActivity;
import cat.copernic.erick.projectm07.R;
import cat.copernic.erick.projectm07.RecyclerView_Config;
import cat.copernic.erick.projectm07.Rutas;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HomeViewModel homeViewModel;
    private TextView tvSinrutas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        tvSinrutas = root.findViewById(R.id.tvSinRutasHomeFragment);


        mRecyclerView = root.findViewById(R.id.recyclerView);
        new FireBaseDatabaHelper().leerRutas(new FireBaseDatabaHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Rutas> rutas, List<String> keys) {
                //new RecyclerView_Config().setConfig(mRecyclerView,HomeFragment.this(),rutas,keys);

                if (rutas.size() <= 0) {

                    tvSinrutas.setVisibility(View.VISIBLE);
                    String toastSinRutas = getActivity().getResources().getString(R.string.sinRutas);
                    tvSinrutas.setText(toastSinRutas);
                    Toast.makeText(getActivity(), toastSinRutas,
                            Toast.LENGTH_SHORT).show();
                } else {
                    tvSinrutas.setVisibility(View.GONE);
                    new RecyclerView_Config().setConfig(mRecyclerView, getActivity(), rutas, keys);
                }
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