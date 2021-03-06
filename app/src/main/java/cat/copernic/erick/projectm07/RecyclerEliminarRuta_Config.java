package cat.copernic.erick.projectm07;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class RecyclerEliminarRuta_Config{
    private Context mContext;
    private RutasAdapter mRutasAdapter;
    private static final String TAG = "MainActivity";


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


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String opcion1 = RecyclerEliminarRuta_Config.this.mContext.getResources().getString(R.string.opcion1EliminarRuta);
                    String opcion2 = RecyclerEliminarRuta_Config.this.mContext.getResources().getString(R.string.opcion2EliminarRuta);

                    final String[] respuesta = {opcion1, opcion2};
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                    String toastEliminaralert = RecyclerEliminarRuta_Config.this.mContext.getResources()
                            .getString(R.string.eliminarRutaElert);
                    String toastCancelar = RecyclerEliminarRuta_Config.this.mContext.getResources().getString(R.string.cacenlaGeneral);
                    mBuilder.setTitle(toastEliminaralert);
                    mBuilder.setIcon(R.drawable.cerrar_ses);

                    mBuilder.setNeutralButton(toastCancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String toastSinModificaciones = RecyclerEliminarRuta_Config.this.mContext.getResources().getString(R.string.sinModificaciones);
                            Toast.makeText(RecyclerEliminarRuta_Config.this.mContext, toastSinModificaciones,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    mBuilder.setSingleChoiceItems(respuesta, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            if (i == 0) {

                                ///----
                                new FireBaseDHEliminarRuta().deleteRutaEliminar(key, new FireBaseDHEliminarRuta.DataStatus() {
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
                           /* Toast.makeText(, "Ruta elimidad correctament: ",
                                    Toast.LENGTH_SHORT).show();*/
                                        return;

                                    }
                                });
                                ///----

                                String toastRutaEliminadaC = RecyclerEliminarRuta_Config.this.mContext.getResources().getString(R.string.cacenlaGeneral);
                                Toast.makeText(mContext, toastRutaEliminadaC,
                                        Toast.LENGTH_SHORT).show();
                            } else if (i == 1) {
                                dialog.dismiss();
                            }
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = mBuilder.create();
                    alertDialog.show();

                }
            });

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

