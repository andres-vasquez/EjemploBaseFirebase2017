package com.example.andresvasquez.ejemplofirebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private FirebaseAuth firebaseAuth;

    //Request Code
    public static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos Firebase
        context = this;
        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        }
        else
        {
            AuthUI.IdpConfig auth = new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER)
                    .build();

            // generamos la pantalla de logueo
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setLogo(R.drawable.icon)
                            .setProviders(Arrays.asList(auth))
                            .build(), RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Log.e("LoginError","aca");
                try
                {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                }
                catch (Exception ex){
                    Log.e("LoginError",""+ex.getMessage());
                }
            } else {
                Log.e("MainActivity", "Ocurrió un error durante el login");
            }
        }
    }

    private void signOut() {
        AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
