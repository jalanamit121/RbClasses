package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class AllLiveClassAdapter  extends RecyclerView.Adapter<AllLiveClassAdapter.ViewHolder> {
    private Context context;
    private ArrayList<VideoContentArray> list;

    public AllLiveClassAdapter(Context context, ArrayList<VideoContentArray> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllLiveClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_frutorials,parent, false);
        return  new AllLiveClassAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllLiveClassAdapter.ViewHolder holder, final int position) {

        if (list.get(position).getClass_status_dec().equalsIgnoreCase("Live")){
            holder.image_gif.setVisibility(View.VISIBLE);
            holder.date.setVisibility(View.GONE);
            holder.notstarted.setVisibility(View.GONE);
        }else if (list.get(position).getClass_status_dec().equalsIgnoreCase("Completed")){
            holder.image_gif.setVisibility(View.GONE);
            holder.date.setVisibility(View.VISIBLE);
            holder.notstarted.setVisibility(View.GONE);
        }else if (list.get(position).getClass_status_dec().equalsIgnoreCase("Not Started Yet")){
           // holder.live_status.setText("Scheduled");
            holder.notstarted.setVisibility(View.VISIBLE);
            holder.image_gif.setVisibility(View.GONE);

        }

        holder.notstarted.setText("Scheduled");
        holder.title.setText(Html.fromHtml(list.get(position).getTopic()));
        holder.date.setText(Html.fromHtml(list.get(position).getPublished()));
        Picasso.get().load(list.get(position).getThumbnail()).placeholder(R.drawable.wateer_mark_image).fit().into(holder.youtubeThubnail);
        holder.teacher.setText(Html.fromHtml(list.get(position).getFaculty()));
        if (list.get(position).getClassType().equals(2)) { //classtype 2 means- recoded video
           // holder.live_status.setVisibility(View.GONE);
            if (list.get(position).getAccessType().equals(1) ) {//access type 1 means - recroded free video
                    holder.img_lock.setVisibility(View.GONE);
                    holder.img_Unlock.setVisibility(View.VISIBLE);
                    holder.image_gif.setVisibility(View.GONE);
                holder.notstarted.setVisibility(View.GONE);
                    holder.date.setVisibility(View.VISIBLE);
                    holder.card_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LocalData.LiveId = list.get(position).getURL();
                            LocalData.DocumentId = list.get(position).getDocumentId();
                            Intent intent = new Intent(context, YouTubeComplete.class);
                            context.startActivity(intent);
                        }
                    });

            } else if (list.get(position).getAccessType().equals(2)) { // access type 2 means- paid video
                holder.img_lock.setVisibility(View.VISIBLE);
                holder.img_Unlock.setVisibility(View.GONE);
                holder.image_gif.setVisibility(View.GONE);
                holder.notstarted.setVisibility(View.GONE);
                holder.date.setVisibility(View.GONE);
                holder.card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("RB Classes");
                        builder.setMessage("To View Please Purchased Course");
                        builder.setCancelable(false);

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.cancel();

                            }
                        });

                        builder.show();
                    }
                });

            }

        }else if (list.get(position).getClassType().equals(1)){// class type 1 means-live class
            if (list.get(position).getAccessType().equals(1)) { //access type 1 means- free live class
                holder.img_lock.setVisibility(View.GONE);
                holder.img_Unlock.setVisibility(View.VISIBLE);
                holder.image_gif.setVisibility(View.VISIBLE);
                holder.date.setVisibility(View.GONE);
                holder.card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.get(position).getClass_status_dec().equalsIgnoreCase("Live")) {
                            LocalData.LiveId = list.get(position).getURL();
                            LocalData.DocumentId = list.get(position).getDocumentId();
                            Intent intent = new Intent(context, YoutubeLibaray.class);
                            context.startActivity(intent);
                        }else if ( list.get(position).getClass_status_dec().equalsIgnoreCase("Completed")){
                            LocalData.LiveId = list.get(position).getURL();
                            LocalData.DocumentId = list.get(position).getDocumentId();
                            Intent intent = new Intent(context, YouTubeComplete.class);
                            context.startActivity(intent);
                        }
                        else if (list.get(position).getClass_status_dec().equalsIgnoreCase("Not Started Yet")) {
                            Toast.makeText(context, "Class Not Started", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (list.get(position).getAccessType().equals(2)) {// access type 2 means- paid live class
              //  holder.live_status.setVisibility(View.VISIBLE);
                holder.img_lock.setVisibility(View.VISIBLE);
                holder.img_Unlock.setVisibility(View.GONE);
                holder.image_gif.setVisibility(View.VISIBLE);
                holder.date.setVisibility(View.GONE);
                holder.notstarted.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,teacher,notstarted,date,status;
        private CardView card_view;
        private GifImageView image_gif;
        private ImageView img_lock,img_Unlock,youtubeThubnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            youtubeThubnail= itemView.findViewById(R.id.youtubeThubnail);
            image_gif= itemView.findViewById(R.id.image_gif);
            img_lock= itemView.findViewById(R.id.img_lock);
            img_Unlock= itemView.findViewById(R.id.img_Unlock);
            title = itemView.findViewById(R.id.title);
            notstarted = itemView.findViewById(R.id.notstarted);
            teacher = itemView.findViewById(R.id.teacher);
           // live_status = itemView.findViewById(R.id.live_status);
            date = itemView.findViewById(R.id.date);
            //status = itemView.findViewById(R.id.status);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}

