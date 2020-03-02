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

public class RecyclerConfiguracionRuta_Config {
    private Context mContext;
    private RutasAdapter mRutasAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Rutas> rutas, List<String> keys) {
        mContext = context;
        mRutasAdapter = new RutasAdapter(rutas, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mRutasAdapter);
    }

    class RutasItemView extends RecyclerView.ViewHolder {

        //RUTA COMPLETA
        private TextView mRutaConfiguracion_id;
        private TextView mRutaConfiguracion_usuario;
        private TextView mRutaConfiguracion_nombre;
        private TextView mRutaConfiguracion_descipcion;
        private TextView mRutaConfiguracion_ruta;
        private TextView mRutaConfiguracion_pais;
        private TextView mRutaConfiguracion_ciudad;

        private String key;

        public RutasItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.configurar_ruta_list_item, parent, false));
                            //inflate(R.layout.activity_configuracion_completa, parent, false));

            //RUTA COMPLETA -> configuracion
            mRutaConfiguracion_id = itemView.findViewById(R.id.tvConfigurarRuta_idRuta);
            mRutaConfiguracion_usuario = itemView.findViewById(R.id.tvConfigurarRuta_usuario);
            mRutaConfiguracion_nombre = itemView.findViewById(R.id.tvConfigurarRuta_nombre);
            mRutaConfiguracion_descipcion = itemView.findViewById(R.id.tvConfigurarRuta_descripcion);
            mRutaConfiguracion_ruta = itemView.findViewById(R.id.tvConfigurarRuta_ruta);
            mRutaConfiguracion_pais = itemView.findViewById(R.id.tvConfigurarRuta_pais);
            mRutaConfiguracion_ciudad = itemView.findViewById(R.id.tvConfigurarRuta_ciudad);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ConfiguracionCompleta.class);
                    intent.putExtra("key", key);
                    intent.putExtra("idRuta:", mRutaConfiguracion_id.getText().toString());
                    intent.putExtra("usuarioRuta:", mRutaConfiguracion_usuario.getText().toString());
                    intent.putExtra("nombreRuta", mRutaConfiguracion_nombre.getText().toString());
                    intent.putExtra("descripcionRuta", mRutaConfiguracion_descipcion.getText().toString());
                    intent.putExtra("ruta", mRutaConfiguracion_ruta.getText().toString());
                    intent.putExtra("paisRuta", mRutaConfiguracion_pais.getText().toString());
                    intent.putExtra("ciudadRuta", mRutaConfiguracion_ciudad.getText().toString());

                    mContext.startActivity(intent);
                }
            });


        }

        public void bind(Rutas rutas, String key) {
            //RUTA
            mRutaConfiguracion_id.setText(rutas.getIdRuta());
            mRutaConfiguracion_usuario.setText(rutas.getUsuarioRuta());
            mRutaConfiguracion_nombre.setText(rutas.getNombreRuta());
            mRutaConfiguracion_descipcion.setText(rutas.getDescripcionRuta());
            mRutaConfiguracion_ruta.setText(rutas.getRuta());
            mRutaConfiguracion_pais.setText(rutas.getPaisRuta());
            mRutaConfiguracion_ciudad.setText(rutas.getCiudadRuta());

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
