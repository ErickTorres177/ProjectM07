package cat.copernic.erick.projectm07.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

import cat.copernic.erick.projectm07.R;
/*
public class Adapter extends RecyclerView.Adapter<Adapter.WordViewHolder>{

    private final LinkedList<String> mListaRutas;
    private LayoutInflater mInflater;


    class WordViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final TextView etNombreRuta;
        final Adapter mAdapter;


        public WordViewHolder(View itemView, Adapter adapter) {
            super(itemView);
            etNombreRuta = itemView.findViewById(R.id.etNombreNuevaRuta);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            String element = mListaRutas.get(mPosition);
            // Change the word in the mWordList.
            mListaRutas.set(mPosition, "Has Pulsado el " + element);
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
        }

    }


    public Adapter(Context context, LinkedList<String> rutasList) {
        mInflater = LayoutInflater.from(context);
        this.mListaRutas = rutasList;
    }



    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.rutaslist_items,
                parent, false);
        return new WordViewHolder(mItemView, this);
    }


    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        String mCurrent = mListaRutas.get(position);
        holder.etNombreRuta.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mListaRutas.size();
    }



}*/

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
