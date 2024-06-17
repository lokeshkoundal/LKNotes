package com.example.lknotes;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class LoginFragment extends Fragment {

    SignInButton googleBtn;
    TextView errorTextView;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 123;
    String userID;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        googleBtn = view.findViewById(R.id.googleBtn);
        //googleBtn.setSize(SignInButton.SIZE_WIDE);

        googleBtn.setOnClickListener(v -> signIn());

        return view;

    }

    void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        if (getContext() != null) {
            mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        } else {
            errorTextView.setText(R.string.error_process_failed);
        }

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            // Signed in successfully
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            userID = account.getId();


                SharedPreferences loggedInUser = requireActivity().getSharedPreferences("LoggedInUser",MODE_PRIVATE);
                SharedPreferences.Editor editor1 = loggedInUser.edit();
                editor1.putString("currentUser",("T_"+userID));
                editor1.apply();



            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new MainFragment());
            fragmentTransaction.commit();

            Toast.makeText(getContext(),"Login Success !!",Toast.LENGTH_SHORT).show();

            SharedPreferences loginCheck = requireContext().getSharedPreferences("Login",MODE_PRIVATE);
            SharedPreferences.Editor editor2 = loginCheck.edit();
            editor2.putBoolean("flag",true);
            editor2.apply();


        } catch (ApiException e) {

            Toast.makeText(getContext(),"Login Failed !! "+ e.getClass(),Toast.LENGTH_SHORT).show();
        }
    }

}