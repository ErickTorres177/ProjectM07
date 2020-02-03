package cat.copernic.erick.projectm07.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cat.copernic.erick.projectm07.R;

public class Adapter extends RecyclerView.Adapter<Holder> {

    Context context;
    ArrayList<Model> models;

    public Adapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
    }

    //Metodos predefinidos
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

        holder.mImageView.setImageResource(models.get(i).getImg());
        holder.mTittle.setText(models.get(i).getTitle());
        holder.mDescripcion.setText(models.get(i).getDescription());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
