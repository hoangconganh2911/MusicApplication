package com.example.musicapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    List<Song> songList;
    Context context;
    OnCLickListener onCLickListener;

    public void setOnCLickListener(OnCLickListener onCLickListener) {
        this.onCLickListener = onCLickListener;
    }

    public SongAdapter(Context context, List<Song> songList)
    {
        this.context=context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item,parent,false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, final int position) {
        holder.txtSongName.setText(songList.get(position).getSongName());
        holder.txtSingle.setText(songList.get(position).getSingle());
        Glide.with(context).load(songList.get(position).getImageUri()).override(180,180).into(holder.img);
        if(onCLickListener!=null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCLickListener.onClickListener(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        TextView txtSongName,txtSingle;
        ImageView  img;
        public SongHolder(@NonNull View itemView) {
            super(itemView);
            txtSongName=(TextView)itemView.findViewById(R.id.txtName);
            txtSingle=(TextView)itemView.findViewById(R.id.txtSingle);
            img=(ImageView)itemView.findViewById(R.id.img);
        }
    }
}
