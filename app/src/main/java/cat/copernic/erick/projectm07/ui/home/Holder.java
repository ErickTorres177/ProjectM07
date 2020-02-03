package cat.copernic.erick.projectm07.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cat.copernic.erick.projectm07.R;

public class Holder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mTittle, mDescripcion;

    public Holder(@NonNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.imageIv);
        this.mTittle= itemView.findViewById(R.id.tittleT);
        this.mDescripcion = itemView.findViewById(R.id.descripcion);
    }
}

