package com.example.lknotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateNoteFragment extends Fragment {

    EditText editTextTitle,editTextNote;
    ImageButton saveUpdateBtn,updateBackBtn;

    String title,note;
    int id;


    public UpdateNoteFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_update_note, container, false);

        editTextTitle = view.findViewById(R.id.updateTextTitle);
        editTextNote = view.findViewById(R.id.updateTextNote);
        saveUpdateBtn = view.findViewById(R.id.updateNoteButton);
        updateBackBtn = view.findViewById(R.id.updateBackBtn);

        getBundleData();

        updateBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }
    void getBundleData(){

        Bundle bundle = getArguments();

        if(bundle != null) {
            title = bundle.getString("TITLE");
            note = bundle.getString("NOTE");
            id = bundle.getInt("ID");

            editTextTitle.setText(title);
            editTextNote.setText(note);
        }
        else{
            Toast.makeText(getContext(),"No data",Toast.LENGTH_SHORT).show();
        }

    }
}