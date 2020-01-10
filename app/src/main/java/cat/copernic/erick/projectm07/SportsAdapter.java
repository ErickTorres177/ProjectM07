package cat.copernic.erick.projectm07;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder>  {

    //Le pasamos al SportAdapter la arrayList de Sport.
    private ArrayList<Sport> mSportsData;
    private Context mContext;
    SportsAdapter(Context context, ArrayList<Sport> sportsData) {
        this.mSportsData = sportsData;
        this.mContext = context;
    }

    @Override
    //ViewHolder es cada elemento de mi recyclerView
    public SportsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }


    @Override
    public void onBindViewHolder(SportsAdapter.ViewHolder holder,
                                 int position) {
        //la posicion es la posicion del recyclerView y tambien de cada elemento del recyclerView
        Sport currentSport = mSportsData.get(position);

        //con este metdo enlazamos cada posision del recyclerView
        holder.bindTo(currentSport);
    }
    @Override
    public int getItemCount() {

        return mSportsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mSportsImage;


       /* public ViewHolder(@NonNull View itemView, TextView mTitleText, TextView mInfoText, ImageView mSportsImage) {
            super(itemView);
            this.mTitleText = mTitleText;
            this.mInfoText = mInfoText;
            this.mSportsImage = mSportsImage;
        }
        */

       ViewHolder(View itemView) {
           super(itemView);

           // Initialize the views.
           mTitleText = itemView.findViewById(R.id.title);
           mInfoText = itemView.findViewById(R.id.subTitle);
           mSportsImage = itemView.findViewById(R.id.sportsImage);

           // Set the OnClickListener to the entire view.
           itemView.setOnClickListener(this);
       }


        //Con este  'bindTo' le de decimos que pille el titulo y la informacion
        void bindTo(Sport currentSport){
            mTitleText.setText(currentSport.getTitle());
            mInfoText.setText(currentSport.getInfo());

            Glide.with(mContext).load(
                    currentSport.getImageResource()).into(mSportsImage);
        }


        @Override
        public void onClick(View view) {
            Sport currentSport = mSportsData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", currentSport.getTitle());
            detailIntent.putExtra("image_resource",
                    currentSport.getImageResource());
            mContext.startActivity(detailIntent);
        }
    }
}
