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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerView_Config {
    private Context mContext;
    private RutasAdapter mRutasAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Rutas> rutas, List<String> keys) {
        mContext = context;
        mRutasAdapter = new RutasAdapter(rutas, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mRutasAdapter);

    }

    class RutasItemView extends RecyclerView.ViewHolder {
        //RUTA
        private TextView mRuta_nombre;
        private TextView mRuta_descripcion;
        private TextView mRuta_ruta;
        private TextView mPais_ruta;
        private TextView mCiudad_ruta;

        //RUTA COMPLETA
        private TextView mRutaCompleta_id;
        private TextView mRutaCompleta_nombre;
        private TextView mRutaCompleta_descripcion;
        private TextView mRutaCompleta_ruta;
        private TextView mRutaCompleta_pais;
        private TextView mRutaCompleta_ciudad;
        private TextView idRutaCompleta;

        private String key;

        public RutasItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.rutas_list_item, parent, false));

            //RUTA
            //mRuta_id = itemView.findViewById(R.id.tvRuta_id);
            mRuta_nombre = itemView.findViewById(R.id.tvRuta_nombre);
            mRuta_descripcion = itemView.findViewById(R.id.tvRuta_descripcion);
            mRuta_ruta = itemView.findViewById(R.id.tvRuta_ruta);
            mPais_ruta = itemView.findViewById(R.id.tvPaisRuta);
            mCiudad_ruta = itemView.findViewById(R.id.tvCiudadRuta);
            idRutaCompleta = itemView.findViewById(R.id.tvIdRutaCompleta);


            //RUTA COMPLETA
            //mRutaCompleta_id = itemView.findViewById(R.id.tvRutaCompleta);
            mRutaCompleta_nombre = itemView.findViewById(R.id.tvRuta_nombre);
            mRutaCompleta_descripcion = itemView.findViewById(R.id.tvRuta_descripcion);
            mRutaCompleta_ruta = itemView.findViewById(R.id.tvRuta_ruta);
            mRutaCompleta_pais = itemView.findViewById(R.id.tvPaisRuta);
            mRutaCompleta_ciudad = itemView.findViewById(R.id.tvCiudadRuta);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RutaCompleta.class);
                    intent.putExtra("key", key);
                    //intent.putExtra("idRuta:", key);
                    intent.putExtra("nombreRuta", mRutaCompleta_nombre.getText().toString());
                    intent.putExtra("descripcionRuta", mRutaCompleta_descripcion.getText().toString());
                    intent.putExtra("ruta", mRutaCompleta_ruta.getText().toString());
                    intent.putExtra("paisRuta", mRutaCompleta_pais.getText().toString());
                    intent.putExtra("ciudadRuta", mRutaCompleta_ciudad.getText().toString());

                    mContext.startActivity(intent);
                }
            });

        }

        public void bind(Rutas rutas, String key) {
            //RUTA
            //mRuta_id.setText(rutas.getIdRuta());
            mRuta_nombre.setText(rutas.getNombreRuta());
            mRuta_descripcion.setText(rutas.getDescripcionRuta());
            mRuta_ruta.setText(rutas.getRuta());
            idRutaCompleta.setText(rutas.getIdRuta());
            mCiudad_ruta.setText(rutas.getCiudadRuta());
            mPais_ruta.setText(rutas.getPaisRuta());
            //---------------------------------------
            //RUTA COMPLETA
            //mRuta_id.setText(rutas.getIdRuta());
            /*mRutaCompleta_nombre.setText(rutas.getNombreRuta());
            mRutaCompleta_descripcion.setText(rutas.getDescripcionRuta());
            mRutaCompleta_ruta.setText(rutas.getRuta());
            mRutaCompleta_pais.setText(rutas.getCiudadRuta());
            mRutaCompleta_ciudad.setText(rutas.getPaisRuta());*/

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









