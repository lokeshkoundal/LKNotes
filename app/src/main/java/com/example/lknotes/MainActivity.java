package com.example.lknotes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFrag(new SplashFragment(),false);

        new Handler().postDelayed(() -> {

            SharedPreferences loginCheck = getSharedPreferences("Login",MODE_PRIVATE);
            boolean  flagOfLogin = loginCheck.getBoolean("flag",false);

            if(flagOfLogin) {

                loadFrag(new MainFragment(), true);
            }
            else{
                loadFrag(new LoginFragment(),true);
            }

        },2200);
}

       void loadFrag(Fragment FragmentContext,boolean flag){


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(!flag) {
            fragmentTransaction.add(R.id.container, FragmentContext);
        }
        else{
            fragmentTransaction.replace(R.id.container, FragmentContext);
        }

        fragmentTransaction.commit();

    }

}