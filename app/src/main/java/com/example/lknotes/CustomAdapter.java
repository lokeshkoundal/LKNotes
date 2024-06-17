package com.example.lknotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> NoteTitle,Note,NoteID;


    CustomAdapter(Context context,ArrayList<String> NoteTitle,ArrayList<String > Note,ArrayList<String> NoteID){
        this.context = context;
        this.Note = Note;
        this.NoteTitle = NoteTitle;
        this.NoteID = NoteID;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView TitleView,NoteView;
        CardView NoteCardView;
        final Context CONTEXT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TitleView= itemView.findViewById(R.id.recycler_TitleTv);
            NoteView= itemView.findViewById(R.id.recycler_NoteTv);
            NoteCardView = itemView.findViewById(R.id.NoteCardView);
            CONTEXT = itemView.getContext();

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

        holder.NoteCardView.setOnClickListener(v -> {
            //Bundle Passing
            Bundle bundle = new Bundle();
            bundle.putString("ID",String.valueOf(NoteID.get(position)));
            bundle.putString("TITLE",String.valueOf(NoteTitle.get(position)));
            bundle.putString("NOTE",String.valueOf(Note.get(position)));

            Fragment updateNoteFragment = new UpdateNoteFragment();
            updateNoteFragment.setArguments(bundle);

            FragmentManager fragmentManager = ((AppCompatActivity) holder.CONTEXT).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, updateNoteFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        });

    }

    @Override
    public int getItemCount() {
        return NoteID.size();
    }


}
