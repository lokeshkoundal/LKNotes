package com.example.lknotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateNoteFragment extends Fragment {

    EditText editTextTitle,editTextNote;
    ImageButton saveUpdateBtn,updateDeleteBtn;

    String title,note,id;


    public UpdateNoteFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_update_note, container, false);

        editTextTitle = view.findViewById(R.id.updateTextTitle);
        editTextNote = view.findViewById(R.id.updateTextNote);
        saveUpdateBtn = view.findViewById(R.id.updateNoteButton);
        updateDeleteBtn = view.findViewById(R.id.updateDeleteBtn);


        getBundleData();


        saveUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   NotesDataBase dataBase = new NotesDataBase(getContext());
                    String updatedTitle = editTextTitle.getText().toString();
                    String updatedNote = editTextNote.getText().toString();

                    dataBase.updateNote(id,updatedTitle,updatedNote);
                    changeToMainFragment();
                }

        });

        updateDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
        return view;
    }
    void getBundleData(){

        Bundle bundle = getArguments();

        if(bundle != null) {
            title = bundle.getString("TITLE");
            note = bundle.getString("NOTE");
            id = bundle.getString("ID");

            editTextTitle.setText(title);
            editTextNote.setText(note);
        }
        else{
            Toast.makeText(getContext(),"No data",Toast.LENGTH_SHORT).show();
        }

    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete this note?");
        builder.setMessage("Are you sure you want to delete this Note? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NotesDataBase dataBase = new NotesDataBase(getContext());
                dataBase.deleteNote(id);
                changeToMainFragment();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
    void changeToMainFragment(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new MainFragment());
        fragmentTransaction.commit();
    }
}