package com.example.andresvasquez.ejemplofirebase.controller;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.andresvasquez.ejemplofirebase.model.Participantes;
import com.example.andresvasquez.ejemplofirebase.utils.Constantes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresvasquez on 10/6/16.
 */
public class FirebaseController {

    private static final String TAG="FirebaseController";

    private DataChanges response;
    public FirebaseController(DataChanges response) {
        this.response = response;
    }


    public static void enviarParticipantes(Participantes participante){
        //TODO implementar Firebase aca
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Constantes.URL_LISTA).push().setValue(participante, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.e(TAG,"Agregado");
            }
        });
    }

    public void recibirParticipantes(){
        //TODO implementar Firebase aca
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Constantes.URL_LISTA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Participantes> lstParticipantes=new ArrayList<Participantes>();
                for(DataSnapshot dataParticipante : dataSnapshot.getChildren()){
                    Participantes objParticipante=dataParticipante.getValue(Participantes.class);
                    lstParticipantes.add(objParticipante);
                }
                response.onDataChanged(lstParticipantes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,"Error "+databaseError);
            }
        });
    }

    public interface DataChanges{
        public void onDataChanged(List<Participantes> lstParticipantes);
    }
}
