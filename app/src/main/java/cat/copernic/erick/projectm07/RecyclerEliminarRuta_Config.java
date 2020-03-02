package cat.copernic.erick.projectm07;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerEliminarRuta_Config {
    private Context mContext;
    private RutasAdapter mRutasAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Rutas> rutas, List<String> keys) {
        mContext = context;
        mRutasAdapter = new RutasAdapter(rutas, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mRutasAdapter);

    }

    class RutasItemView extends RecyclerView.ViewHolder {

        //ELIMINAR RUTA
        private TextView mRuta_nombre_eliminar;
        private TextView mRuta_descripcion_eliminar;
        private TextView mRuta_ruta_eliminar;
        private TextView mPais_ruta_eliminar;
        private TextView mCiudad_ruta_eliminar;
        private TextView idRutaCompleta_eliminar;

        private String key;

        public RutasItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.eliminar_rutas_list_item, parent, false));


            //ELIMINAR RUTA
            //mRuta_id = itemView.findViewById(R.id.tvRuta_id);
            mRuta_nombre_eliminar = itemView.findViewById(R.id.tvEliminarRuta_nombre);
            mRuta_descripcion_eliminar = itemView.findViewById(R.id.tvEliminarRuta_descripcion);
            mRuta_ruta_eliminar = itemView.findViewById(R.id.tvEliminarRuta_ruta);
            mPais_ruta_eliminar = itemView.findViewById(R.id.tvEliminarRuta_pais);
            mCiudad_ruta_eliminar = itemView.findViewById(R.id.tvEliminarRuta_ciudad);
            idRutaCompleta_eliminar = itemView.findViewById(R.id.tvEliminarRuta_idRuta);





        }

        public void bind(Rutas rutas, String key) {



            //ELIMINAR RUTA
            //mRuta_id = itemView.findViewById(R.id.tvRuta_id);
            mRuta_nombre_eliminar.setText(rutas.getNombreRuta());
            mRuta_descripcion_eliminar.setText(rutas.getDescripcionRuta());
            mRuta_ruta_eliminar.setText(rutas.getRuta());
            mPais_ruta_eliminar.setText(rutas.getPaisRuta());
            mCiudad_ruta_eliminar.setText(rutas.getCiudadRuta());
            idRutaCompleta_eliminar.setText(rutas.getIdRuta());

            this.key = key;
        }
    }

    class RutasAdapter extends RecyclerView.Adapter<RutasItemView> {
        private List<Rutas> mRutasList;
        private List<String> mKeys;

        public RutasAdapter(List<Rutas> mRutasList, List<String> mKeys) {
            this.mRutasList = mRutasList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public RutasItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RutasItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RutasItemView holder, int position) {
            holder.bind(mRutasList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mRutasList.size();
        }
    }


}

