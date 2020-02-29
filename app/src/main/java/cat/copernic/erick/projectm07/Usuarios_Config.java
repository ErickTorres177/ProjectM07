package cat.copernic.erick.projectm07;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Usuarios_Config {
    private Context mContextUsuario;
    private UsuariosAdapter mUsuariosAdapter;

    public void setConfig(RecyclerView recyclerViewUsuarios, Context contextU, List<Usuarios> usuarios, List<String> keys) {
        mContextUsuario = contextU;
        mUsuariosAdapter = new UsuariosAdapter(usuarios, keys);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(contextU));
        recyclerViewUsuarios.setAdapter(mUsuariosAdapter);

    }

    class UsuariosItemView extends RecyclerView.ViewHolder {
        private TextView mUsuario_id;
        private TextView mUsuario_user;
        private TextView mUsuario_nombre;
        private TextView mUsuario_edad;
        private TextView mUsuario_direccion;

        private String key;

        public UsuariosItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContextUsuario).
                    inflate(R.layout.usuario_list_item, parent, false));

            //mUsuario_id = itemView.findViewById(R.id.);
            mUsuario_nombre = itemView.findViewById(R.id.tvUsuario_nombre);
            mUsuario_user = itemView.findViewById(R.id.tvUsuario_user);
            //mUsuario_edad = itemView.findViewById(R.id.);
            //mUsuario_direccion = itemView.findViewById(R.id.);
        }

        public void bindU(Usuarios usuarios, String key) {
            /*mUsuario_id.setText(usuarios.getUserId());
            mUsuario_user.setText(usuarios.getUser());
            mUsuario_nombre.setText(usuarios.getNombreUsuario());
            mUsuario_edad.setText(usuarios.getEdad());
            mUsuario_direccion.setText(usuarios.getDireccion());*/
            this.key = key;
        }
    }

    class UsuariosAdapter extends RecyclerView.Adapter<UsuariosItemView> {

        private List<Usuarios> mUsuariosList;
        private List<String> mKeys;

        public UsuariosAdapter(List<Usuarios> mUsuariosList, List<String> mKeys) {
            this.mUsuariosList = mUsuariosList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public UsuariosItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UsuariosItemView(parent);

        }

        @Override
        public void onBindViewHolder(@NonNull UsuariosItemView holder, int position) {
            holder.bindU(mUsuariosList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}



