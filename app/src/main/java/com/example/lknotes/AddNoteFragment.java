package com.example.lknotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteFragment extends Fragment {


//    Button backButton;
    Button saveButton;

    EditText titleEditText;

    EditText noteEditText;
    private Toast toast;


    public AddNoteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);


        saveButton = view.findViewById(R.id.updateNoteButton);
        titleEditText = view.findViewById(R.id.editTextTitle);
        noteEditText = view.findViewById(R.id.editTextNote);


        saveButton.setOnClickListener(v -> {
           try(NotesDataBase dataBase = new NotesDataBase(requireContext())){
               if(titleEditText.getText().toString().equals("") && noteEditText.getText().toString().equals(""))
                   showToast();
               else {
                   FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                   dataBase.saveNote(titleEditText.getText().toString().trim(), noteEditText.getText().toString().trim(),fragmentManager);
               }
        }});

        return  view;
    }
    private void showToast() {
        // Cancel the existing toast if it is visible
        if (toast != null) {
            toast.cancel();
        }

        toast =  Toast.makeText(getContext(),"insert your note",Toast.LENGTH_SHORT);
        toast.show();
    }
}