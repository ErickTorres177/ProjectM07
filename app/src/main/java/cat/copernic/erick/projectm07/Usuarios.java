package cat.copernic.erick.projectm07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Usuarios {
    private String nombre;
    private String edad;
    private String usuario;
    private String direccion;
    private String sexo;
    //private List<Rutas> rutas;

    public Usuarios() {
    }

    public Usuarios(String nombre, String edad, String usuario, String direccion, String sexo) {
        this.nombre = nombre;
        this.edad = edad;
        this.usuario = usuario;
        this.direccion = direccion;
        this.sexo = sexo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public static class RecyclerEliminarRuta_Config {
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
            private TextView mId_ruta;

            private String key;

            public RutasItemView(ViewGroup parent) {
                super(LayoutInflater.from(mContext).
                        inflate(R.layout.eliminar_rutas_list_item, parent, false));

                //RUTA
                //mRuta_id = itemView.findViewById(R.id.tvRuta_id);
                mRuta_nombre = itemView.findViewById(R.id.tvEliminarRuta_nombre);
                mRuta_descripcion = itemView.findViewById(R.id.tvEliminarRuta_descripcion);
                mRuta_ruta = itemView.findViewById(R.id.tvEliminarRuta_ruta);
                mPais_ruta = itemView.findViewById(R.id.tvEliminarRuta_pais);
                mCiudad_ruta = itemView.findViewById(R.id.tvEliminarRuta_ciudad);
                mId_ruta = itemView.findViewById(R.id.tvEliminarRuta_idRuta);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new FireBaseDatabaHelper().deleteRuta(key, new FireBaseDatabaHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Rutas> rutas, List<String> keys) {

                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdate() {

                            }

                            @Override
                            public void DataIsDelete() {


                                return;
                            }
                        });
                    }
                });



            }

            public void bind(Rutas rutas, String key) {
                //RUTA
                //mRuta_id.setText(rutas.getIdRuta());
                mRuta_nombre.setText(rutas.getNombreRuta());
                mRuta_descripcion.setText(rutas.getDescripcionRuta());
                mRuta_ruta.setText(rutas.getRuta());
                mId_ruta.setText(rutas.getIdRuta());
                mCiudad_ruta.setText(rutas.getCiudadRuta());
                mPais_ruta.setText(rutas.getPaisRuta());

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
}

