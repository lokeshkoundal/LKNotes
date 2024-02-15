package com.example.lknotes;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    CustomAdapter customAdapter;
    ImageButton addNoteButton;
    RecyclerView recyclerView;
    NotesDataBase notesDataBase;
    ArrayList<String> NoteTitle,Note,NoteID;

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        addNoteButton = view.findViewById(R.id.addNoteBtn);
        recyclerView = view.findViewById(R.id.recycler);

        notesDataBase = new NotesDataBase(requireContext());

        NoteTitle = new ArrayList<>();
        Note = new ArrayList<>();
        NoteID = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(requireActivity(),NoteTitle,Note,NoteID);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new AddNoteFragment());
                fragmentTransaction.commit();
            }
        });


        return view;
    }
    void storeDataInArrays(){
        Cursor cursor = notesDataBase.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(requireContext(),"Database is Empty",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                NoteID.add(cursor.getString(0));
                NoteTitle.add(cursor.getString(1));
                Note.add(cursor.getString(2));

            }
        }
    }




}