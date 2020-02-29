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

public class RecyclerView_RutaCompleta {
    private Context mContext;
    private RutasCompletasAdapter mRutasAdapter;



    public void setConfig(RecyclerView recyclerView, Context context, List<Rutas> rutasCompletas, List<String> keys){
        mContext = context;
        mRutasAdapter = new RutasCompletasAdapter(rutasCompletas, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mRutasAdapter);

    }

    class RutasCompletasItemView extends RecyclerView.ViewHolder{
        //RUTA COMPLETA
        private TextView mRutaCompleta_id;
        private TextView mRutaCompleta_nombre;
        private TextView mRutaCompleta_descripcion;
        private TextView mRutaCompleta_ruta;
        private TextView mRutaCompleta_pais;
        private TextView mRutaCompleta_ciudad;

        private String key;

        public RutasCompletasItemView(ViewGroup parent ){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.activity_ruta_completa,parent, false));


            //RUTA COMPLETA
            //mRutaCompleta_id = itemView.findViewById(R.id.tvRutaCompleta);
            mRutaCompleta_nombre = itemView.findViewById(R.id.tvNombreNuevaRuta);
            mRutaCompleta_descripcion = itemView.findViewById(R.id.tvDescRutaCompleta);
            mRutaCompleta_ruta = itemView.findViewById(R.id.tvRutaRutaCompleta);
            mRutaCompleta_pais = itemView.findViewById(R.id.tvPaisRutaCompleta);
            mRutaCompleta_ciudad = itemView.findViewById(R.id.tvCiudadRutaCompleta);
        }

        public void bind(Rutas rutasCompletas, String key){
            //RUTA COMPLETA
            //mRuta_id.setText(rutas.getIdRuta());
            mRutaCompleta_nombre.setText(rutasCompletas.getNombreRuta());
            mRutaCompleta_descripcion.setText(rutasCompletas.getDescripcionRuta());
            mRutaCompleta_ruta.setText(rutasCompletas.getRuta());
            mRutaCompleta_pais.setText(rutasCompletas.getCiudadRuta());
            mRutaCompleta_ciudad.setText(rutasCompletas.getPaisRuta());

            this.key = key;
        }
    }

    class RutasCompletasAdapter extends RecyclerView.Adapter<RutasCompletasItemView>{
        private List<Rutas> mrutasCompletasList;
        private List<String> mKeys;

        public RutasCompletasAdapter(List<Rutas> mrutasCompletasList, List<String> mKeys) {
            this.mrutasCompletasList = mrutasCompletasList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public RutasCompletasItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RutasCompletasItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RutasCompletasItemView holder, int position) {
            holder.bind(mrutasCompletasList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mrutasCompletasList.size();
        }
    }



}
