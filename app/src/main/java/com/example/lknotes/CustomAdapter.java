package com.example.lknotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    ArrayList NoteTitle,Note,NoteID;


    CustomAdapter(Context context,ArrayList NoteTitle,ArrayList Note,ArrayList NoteID){
        this.context = context;
        this.Note = Note;
        this.NoteTitle = NoteTitle;
        this.NoteID = NoteID;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView TitleView,NoteView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TitleView= itemView.findViewById(R.id.recycler_TitleTv);
            NoteView= itemView.findViewById(R.id.recycler_NoteTv);
        }
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.recycler_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.TitleView.setText(String.valueOf(NoteTitle.get(position)));
        holder.NoteView.setText(String.valueOf(Note.get(position)));

    }

    @Override
    public int getItemCount() {
        return NoteID.size();
    }

}
