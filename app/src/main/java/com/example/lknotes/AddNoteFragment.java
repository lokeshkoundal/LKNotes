package com.example.lknotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddNoteFragment extends Fragment {

    ImageButton backButton;
    ImageButton saveButton;

    EditText titleEditText;

    EditText noteEditText;


    public AddNoteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        backButton = view.findViewById(R.id.backBtn);
        saveButton = view.findViewById(R.id.saveNoteButton);
        titleEditText = view.findViewById(R.id.editTextTitle);
        noteEditText = view.findViewById(R.id.editTextNote);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new MainFragment());
                fragmentTransaction.commit();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try(NotesDataBase dataBase = new NotesDataBase(requireContext())){
                dataBase.saveNote(titleEditText.getText().toString().trim(),noteEditText.getText().toString().trim());
            }}
        });

        return  view;
    }
}