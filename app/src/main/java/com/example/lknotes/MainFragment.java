package com.example.lknotes;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragment extends Fragment {
    CustomAdapter customAdapter;
    ImageButton addNoteButton;
    Button signOutButton;
    RecyclerView recyclerView;
    NotesDataBase notesDataBase;
    TextView noNotesTv;
    CircleImageView circleImageView;
    ImageView arrowImageView;
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
        circleImageView = view.findViewById(R.id.emplyNotesPic);
        arrowImageView = view.findViewById(R.id.arrowImg);

        notesDataBase = new NotesDataBase(requireContext());

        NoteTitle = new ArrayList<>();
        Note = new ArrayList<>();
        NoteID = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(requireActivity(),NoteTitle,Note,NoteID);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        addNoteButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new AddNoteFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        signOutButton.setOnClickListener(v -> confirmDialog());


        return view;
    }
    void storeDataInArrays(){
        Cursor cursor = notesDataBase.readAllData();
        if(cursor.getCount() == 0){

            circleImageView.setVisibility(View.VISIBLE);
            noNotesTv.setVisibility(View.VISIBLE);
            arrowImageView.setVisibility(View.VISIBLE);

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
        builder.setPositiveButton("Yes", (dialog, which) -> {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(requireActivity(), task -> {

                        SharedPreferences loginCheck = requireContext().getSharedPreferences("Login",MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginCheck.edit();
                        editor.putBoolean("flag",false);
                        editor.apply();

                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new LoginFragment());
                        fragmentTransaction.commit();


                        Toast.makeText(getContext(),"Signed Out",Toast.LENGTH_SHORT).show();
                    });



        });

        builder.setNegativeButton("NO", (dialog, which) -> {});
        builder.create().show();
    }




}