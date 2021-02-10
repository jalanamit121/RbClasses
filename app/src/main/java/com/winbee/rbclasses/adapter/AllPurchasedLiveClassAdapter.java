package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import com.winbee.rbclasses.NewModels.VideoContentArray;
import com.winbee.rbclasses.R;
import com.winbee.rbclasses.YouTubeComplete;
import com.winbee.rbclasses.YoutubeLibaray;
import com.winbee.rbclasses.model.CourseContentModel;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class AllPurchasedLiveClassAdapter extends RecyclerView.Adapter<AllPurchasedLiveClassAdapter.ViewHolder> {
    private Context context;
    private ArrayList<VideoContentArray> list;

    public AllPurchasedLiveClassAdapter(Context context, ArrayList<VideoContentArray> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_live,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (list.get(position).getClass_status_dec().equalsIgnoreCase("Live")){
            holder.date.setVisibility(View.GONE);
            holder.image_gif.setVisibility(View.VISIBLE);
            holder.notstarted.setVisibility(View.GONE);
            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.Topic=list.get(position).getTopic();
                    LocalData.Subject=list.get(position).getSubject();

                    LocalData.LiveId = list.get(position).getURL();
                    LocalData.DocumentId = list.get(position).getDocumentId();
                    Intent intent = new Intent(context, YoutubeLibaray.class);
                    context.startActivity(intent);

                   //ek or jgh tha na
                }
            });
        }else if (list.get(position).getClass_status_dec().equalsIgnoreCase("Completed")){
            holder.date.setVisibility(View.VISIBLE);
            holder.image_gif.setVisibility(View.GONE);
            holder.notstarted.setVisibility(View.GONE);
            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.Topic=list.get(position).getTopic();
                    LocalData.Subject=list.get(position).getSubject();

                    LocalData.LiveId = list.get(position).getURL();
                    LocalData.DocumentId = list.get(position).getDocumentId();
                    Intent intent = new Intent(context, YouTubeComplete.class);
                    context.startActivity(intent);
                }
            });
            LocalData.Topic=list.get(position).getTopic();
            LocalData.Subject=list.get(position).getSubject();
        }else if (list.get(position).getClass_status_dec().equalsIgnoreCase("NA")){
            holder.image_gif.setVisibility(View.GONE);
            holder.notstarted.setVisibility(View.GONE);
            holder.date.setVisibility(View.VISIBLE);
                holder.card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.Topic=list.get(position).getTopic();
                        LocalData.Subject=list.get(position).getSubject();

                        LocalData.LiveId = list.get(position).getURL();
                        LocalData.DocumentId = list.get(position).getDocumentId();
                        Intent intent = new Intent(context, YouTubeComplete.class);
                        context.startActivity(intent);
                    }
                });
            LocalData.Topic=list.get(position).getTopic();
            LocalData.Subject=list.get(position).getSubject();
        }else if (list.get(position).getClass_status_dec().equalsIgnoreCase("Not Started Yet")){
            holder.image_gif.setVisibility(View.GONE);
            holder.notstarted.setVisibility(View.VISIBLE);
            holder.date.setVisibility(View.VISIBLE);

        }
        holder.title.setText(Html.fromHtml(list.get(position).getTopic()));
        holder.date.setText(Html.fromHtml(list.get(position).getPublished()));

// subject and course same aarha he sab me
        //alag to hai
// same hi aarha course name change he but subject same he har course me change ni ho rha dekho
        Picasso.get().load(list.get(position).getThumbnail()).placeholder(R.drawable.wateer_mark_image).fit().into(holder.youtubeThubnail);
        holder.teacher.setText(Html.fromHtml(list.get(position).getFaculty()));
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,teacher,date,notstarted;
        private CardView card_view;
        private GifImageView image_gif;
        private ImageView img_lock,youtubeThubnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            youtubeThubnail= itemView.findViewById(R.id.youtubeThubnail);
            image_gif= itemView.findViewById(R.id.image_gif);
            img_lock= itemView.findViewById(R.id.img_lock);
            title = itemView.findViewById(R.id.title);
            teacher = itemView.findViewById(R.id.teacher);
            date = itemView.findViewById(R.id.date);
            card_view = itemView.findViewById(R.id.card_view);
            notstarted = itemView.findViewById(R.id.notstarted);
        }
    }
}

