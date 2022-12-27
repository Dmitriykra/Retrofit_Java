package dmytro.app.retrofitstudy;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<MainData> dataList;
    private Activity context;
    private RoomData database;
    Animation trans_anim;


    public MainAdapter(Activity context, List<MainData> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row,parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        MainData data = dataList.get(position);
        database = RoomData.getInstance(context);
        holder.textView.setText(data.getText());
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView mainLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.card_tv);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            trans_anim = AnimationUtils.loadAnimation(context, R.anim.trans_anim);
            mainLayout.setAnimation(trans_anim);

            itemView.setOnClickListener(v -> {
                MainData data =  dataList.get(getAdapterPosition());
                Intent intent = new Intent(context, NumberInfoActivity.class);
                intent.putExtra("data", data);
                Log.d("TAG", "ViewHolder: "+data);
                context.startActivity(intent);
            });
        }
    }
}
