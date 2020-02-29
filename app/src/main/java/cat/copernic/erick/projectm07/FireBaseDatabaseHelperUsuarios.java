package cat.copernic.erick.projectm07;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBaseDatabaseHelperUsuarios {

    private FirebaseDatabase mDatabaseU;
    private DatabaseReference mRefenceUsuarios;
    private List<Usuarios> usuarios= new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Usuarios> usuarios, List<String> keys);
        void DataIsInserted();
        void DataIsUpdate();
        void DataIsDelete();
    }

    public FireBaseDatabaseHelperUsuarios() {
        mDatabaseU = FirebaseDatabase.getInstance();
        mRefenceUsuarios = mDatabaseU.getReference("Usuarios");
    }

    public void leerUsuarios(final FireBaseDatabaseHelperUsuarios.DataStatus dataStatus){
        mRefenceUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarios.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode: dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Usuarios usuario = keyNode.getValue(Usuarios.class);
                    usuarios.add(usuario);
                }
                dataStatus.DataIsLoaded(usuarios,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
