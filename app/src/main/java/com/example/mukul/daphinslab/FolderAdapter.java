package com.example.mukul.daphinslab;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by mukul on 12/14/17.
 */

public class FolderAdapter  extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> implements View.OnClickListener {
    private ArrayList<Bucket> Images;

    private OnItemClickListener listener;
    Context context;


    public FolderAdapter(Context context, ArrayList<Bucket> images, OnItemClickListener listener) {
        super();
        this.Images = images;
        this.listener = listener;
        this.context = context;

        //songInf=LayoutInflater.from(c);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imagewithdetails, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(Images.get(position), listener);
        Bucket bucket = Images.get(position);

        Glide.with(context).load(bucket.getImagepath()).asBitmap().centerCrop().into(holder.imageView);
        File file = new File(bucket.getImagepath());
        holder.imageName.setText(file.getName());
        GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]  {Color.parseColor("#000000"),Color.argb(0,0,0,0)});
        gradient.setShape(GradientDrawable.RECTANGLE);
        holder.view.setBackground(gradient);
        holder.imageView.setTransitionName(file.getName());



    }

    @Override
    public int getItemCount() {
        return Images.size();
    }

//    public void swapCursor(ArrayList<Songs> SongList) {
//        this.songs = SongList;
//        notifyDataSetChanged();
//    }

    @Override
    public void onClick(View view) {

    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // TODO Auto-generated method stub
//        ConstraintLayout songLay = (ConstraintLayout) songInf.inflate
//                (R.layout.songs, parent, false);
//        //get title and artist views
//        TextView songView = (TextView)songLay.findViewById(R.id.song_title);
//        TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
//        ImageView albumart = (ImageView)songLay.findViewById(R.id.imageView2);
//        //get song using position
//        Songs currSong = songs.get(position);
//        //get title and artist strings
//        songView.setText(currSong.getTitle());
//        artistView.setText(currSong.getArtist());
//        albumart.setImageURI(currSong.getAlbumarturi());
//        //set position as tag
//        songLay.setTag(position);
//        return songLay;
//    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView imageName;
        View view;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageDisplay);
            imageName = (TextView) itemView.findViewById(R.id.imageName);
            view = (View) itemView.findViewById(R.id.imageBackground);


        }

        public void bind(final Bucket item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item,imageView);
                }
            });
        }

        @Override
        public void onClick(View view) {


        }

        //     @Override
//        public boolean onLongClick(View view) {
//            cursor.moveToPosition(getAdapterPosition());
//            int name = cursor.getInt(cursor.getColumnIndex(DatabasePanorbit.ID));
//            onMedicineItemClicked.onMedicineLongClicked(name);
//            return false;
//        }
    }

    public interface OnItemClickListener {
        void onItemClick(Bucket item,ImageView imageView);
    }
}