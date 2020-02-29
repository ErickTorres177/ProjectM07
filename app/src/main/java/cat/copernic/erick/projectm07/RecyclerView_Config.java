package cat.copernic.erick.projectm07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private RutasAdapter mRutasAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Rutas> rutas, List<String> keys){
        mContext = context;
        mRutasAdapter = new RutasAdapter(rutas, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mRutasAdapter);

    }

    class RutasItemView extends RecyclerView.ViewHolder{
        //RUTA
        private TextView mRuta_nombre;
        private TextView mRuta_descripcion;
        private TextView mRuta_ruta;
        //RUTA COMPLETA
        private TextView mRutaCompleta_id;
        private TextView mRutaCompleta_nombre;
        private TextView mRutaCompleta_descripcion;
        private TextView mRutaCompleta_ruta;
        private TextView mRutaCompleta_pais;
        private TextView mRutaCompleta_ciudad;

        private String key;

        public RutasItemView(ViewGroup parent ){
            super(LayoutInflater.from(mContext).
            inflate(R.layout.rutas_list_item,parent, false));

            //RUTA
            //mRuta_id = itemView.findViewById(R.id.tvRuta_id);
            mRuta_nombre = itemView.findViewById(R.id.tvRuta_nombre);
            mRuta_descripcion = itemView.findViewById(R.id.tvRuta_descripcion);
            mRuta_ruta = itemView.findViewById(R.id.tvRuta_ruta);
            //mRuta_ciudad = itemView.findViewById(R.id.tvRuta_ciudad);
            //mRuta_pais = itemView.findViewById(R.id.tvRuta_pais);
            //---------------------------------------
            //RUTA COMPLETA
            //mRutaCompleta_id = itemView.findViewById(R.id.tvRutaCompleta);
            mRutaCompleta_nombre = itemView.findViewById(R.id.tvNombreNuevaRuta);
            mRutaCompleta_descripcion = itemView.findViewById(R.id.tvDescRutaCompleta);
            mRutaCompleta_ruta = itemView.findViewById(R.id.tvRutaRutaCompleta);
            mRutaCompleta_pais = itemView.findViewById(R.id.tvPaisRutaCompleta);
            mRutaCompleta_ciudad = itemView.findViewById(R.id.tvCiudadRutaCompleta);
        }

        public void bind(Rutas rutas, String key){
            //RUTA
            //mRuta_id.setText(rutas.getIdRuta());
            mRuta_nombre.setText(rutas.getNombreRuta());
            mRuta_descripcion.setText(rutas.getDescripcionRuta());
            mRuta_ruta.setText(rutas.getRuta());
            //mRuta_ciudad.setText(rutas.getCiudadRuta());
            //mRuta_pais.setText(rutas.getPaisRuta());
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

    class RutasAdapter extends RecyclerView.Adapter<RutasItemView>{
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









