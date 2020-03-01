package cat.copernic.erick.projectm07;

import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBaseDatabaHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefenceRutas;
    private List<Rutas> rutas = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Rutas> rutas, List<String> keys);
        void DataIsInserted();
        void DataIsUpdate();
        void DataIsDelete();
    }

    public FireBaseDatabaHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mRefenceRutas = mDatabase.getReference("rutas");
    }

    public void leerRutas(final DataStatus dataStatus){
        mRefenceRutas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rutas.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode: dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Rutas ruta = keyNode.getValue(Rutas.class);
                    rutas.add(ruta);
                }
                dataStatus.DataIsLoaded(rutas,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*public void updateUser(String key,Usuarios usuario, final DataStatus dataStatus){
        mRefenceRutas.child(key).setValue(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdate();
            }
        });
    }

    public void deleteUser(String key, final DataStatus dataStatus){
        mRefenceRutas.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdate();
            }
        });
    }*/
}
