package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.rbclasses.LocalData;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.VimeoActivity;
import com.winbee.rbclasses.YouTubeComplete;
import com.winbee.rbclasses.YoutubeLibaray;
import com.winbee.rbclasses.YoutubePlayer;
import com.winbee.rbclasses.model.CourseContentModel;

import java.util.ArrayList;

public class AllPurchasedLiveClassAdapter extends RecyclerView.Adapter<AllPurchasedLiveClassAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CourseContentModel> list;

    public AllPurchasedLiveClassAdapter(Context context, ArrayList<CourseContentModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllPurchasedLiveClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_live,parent, false);
        return  new AllPurchasedLiveClassAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllPurchasedLiveClassAdapter.ViewHolder holder, final int position) {

        if (list.get(position).getClass_status_dec().equalsIgnoreCase("Live")){
            holder.live_status.setText("Live");
            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.LiveId = list.get(position).getURL();
                    LocalData.DocumentId = list.get(position).getDocumentId();
                    Intent intent = new Intent(context, YoutubeLibaray.class);
                    context.startActivity(intent);
                }
            });
        }else if (list.get(position).getClass_status_dec().equalsIgnoreCase("Completed")){
            holder.live_status.setText("");
            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.LiveId = list.get(position).getURL();
                    LocalData.DocumentId = list.get(position).getDocumentId();
                    Intent intent = new Intent(context, YouTubeComplete.class);
                    context.startActivity(intent);
                }
            });
        }else if (list.get(position).getClass_status_dec().equalsIgnoreCase("NA")){
            if (list.get(position).getType().equalsIgnoreCase("Vimeo")){
                holder.live_status.setText("");
                holder.card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.LiveId = list.get(position).getURL();
                        LocalData.DocumentId = list.get(position).getDocumentId();
                        Intent intent = new Intent(context, VimeoActivity.class);
                        context.startActivity(intent);
                    }
                });
            }else if (list.get(position).getType().equals("YouTube")){
                holder.live_status.setText("");
                holder.card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.LiveId = list.get(position).getURL();
                        LocalData.DocumentId = list.get(position).getDocumentId();
                        Intent intent = new Intent(context, YouTubeComplete.class);
                        context.startActivity(intent);
                    }
                });
            }

        }else if (list.get(position).getClass_status_dec().equalsIgnoreCase("Not Started Yet")){
            holder.live_status.setText("Scheduled");

        }
        holder.title.setText(list.get(position).getTopic());
        Picasso.get().load(list.get(position).getThumbnail()).fit().into(holder.youtubeThubnail);
        holder.teacher.setText("By-" + list.get(position).getFaculty());
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,teacher,live_status,time,status;
        private CardView card_view;
        private ImageView img_lock,youtubeThubnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            youtubeThubnail= itemView.findViewById(R.id.youtubeThubnail);
            img_lock= itemView.findViewById(R.id.img_lock);
            title = itemView.findViewById(R.id.title);
            teacher = itemView.findViewById(R.id.teacher);
            live_status = itemView.findViewById(R.id.live_status);
            time = itemView.findViewById(R.id.time);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}

