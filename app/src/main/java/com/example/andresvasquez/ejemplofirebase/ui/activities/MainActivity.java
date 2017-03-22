package com.example.andresvasquez.ejemplofirebase.ui.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andresvasquez.ejemplofirebase.R;
import com.example.andresvasquez.ejemplofirebase.controller.FirebaseController;
import com.example.andresvasquez.ejemplofirebase.model.Participantes;
import com.example.andresvasquez.ejemplofirebase.ui.adapters.ParticipantesAdapter;
import com.example.andresvasquez.ejemplofirebase.ui.dialog.NuevoDialog;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private FirebaseAuth firebaseAuth;
    private ListView lvParticipantes;
    private TextView txtFirebaseUrl;

    private FloatingActionButton fab;
    private FloatingActionButton fabCerrar;

    private List<Participantes> lstParticipantes;
    private ParticipantesAdapter adapter;

    //Request Code
    public static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos Firebase
        context = this;
        init();

        lstParticipantes=new ArrayList<Participantes>();
        adapter=new ParticipantesAdapter(context,lstParticipantes);
        lvParticipantes.setAdapter(adapter);

        //TODO Agregar auteNticacion
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
        {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            mostrarUsuario(currentUser);
            iniciarAcciones();
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setLogo(R.drawable.icon)
                            .setProviders(
                                    AuthUI.FACEBOOK_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER,
                                    AuthUI.EMAIL_PROVIDER)
                            .setTheme(R.style.tema)
                            .build(), RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                try {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    mostrarUsuario(currentUser);
                    iniciarAcciones();
                }
                catch (Exception ex){
                    Log.e("LoginError",""+ex.getMessage());
                }
            } else {
                Log.e("MainActivity", "Ocurrió un error durante el login");
            }
        }
    }

    /*
    Inicializamos las vistas
     */
    private void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        fab= (FloatingActionButton) findViewById(R.id.fab);
        fabCerrar=(FloatingActionButton) findViewById(R.id.fabCerrar);

        lvParticipantes=(ListView)findViewById(R.id.lvParticipantes);
        txtFirebaseUrl=(TextView)findViewById(R.id.txtFirebaseUrl);

        //Ocultamos el titulo
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapser);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    /**
     * Iniciamos las acciones de Firebase
     */
    private void iniciarAcciones(){
        //Nuevo participante
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = NuevoDialog.newInstance();
                dialog.show(MainActivity.this.getFragmentManager(), "participante");
            }
        });

        //Cerrar sesion
        fabCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


        //Creamos un Listener para buscar nuevos participantes
        new FirebaseController(new FirebaseController.DataChanges() {
            @Override
            public void onDataChanged(List<Participantes> lstParticipantesRecibidos){
                lstParticipantes.clear();
                lstParticipantes.addAll(lstParticipantesRecibidos);
                adapter.notifyDataSetChanged();
            }
        }).recibirParticipantes();
    }

    /**
     * Mostramos datos de usuario en Logcat
     * @param currentUser
     */
    private void mostrarUsuario(FirebaseUser currentUser) {
        Log.e("User",currentUser.getUid());
        Log.e("User",currentUser.getDisplayName());
        Log.e("User",currentUser.getEmail());
        Log.e("User",""+currentUser.getPhotoUrl());
    }

    /**
     * Cerramos sesion
     */
    private void signOut() {
        //Todo cerrar sesion
        AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
