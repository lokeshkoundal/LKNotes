package com.example.lknotes;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    CustomAdapter customAdapter;
    ImageButton addNoteButton;
    Button signOutButton;
    RecyclerView recyclerView;
    NotesDataBase notesDataBase;
    TextView noNotesTv;
    ArrayList<String> NoteTitle,Note,NoteID;

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        addNoteButton = view.findViewById(R.id.addNoteBtn);
        recyclerView = view.findViewById(R.id.recycler);
        noNotesTv = view.findViewById(R.id.NoNotesTv);
        signOutButton = view.findViewById(R.id.SignOutBtn);

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
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });


        return view;
    }
    void storeDataInArrays(){
        Cursor cursor = notesDataBase.readAllData();
        if(cursor.getCount() == 0){

            noNotesTv.setVisibility(View.VISIBLE);

        }
        else{
            while (cursor.moveToNext()){
                NoteID.add(cursor.getString(0));
                NoteTitle.add(cursor.getString(1));
                Note.add(cursor.getString(2));

            }
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sign Out?");
        builder.setMessage("Are you sure you want to Sign Out? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                SharedPreferences loginCheck = requireContext().getSharedPreferences("Login",MODE_PRIVATE);
                                SharedPreferences.Editor editor = loginCheck.edit();
                                editor.putBoolean("flag",false);
                                editor.apply();

                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.container, new LoginFragment());
                                fragmentTransaction.commit();


                                Toast.makeText(getContext(),"Signed Out",Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.create().show();
    }




}