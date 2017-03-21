package com.example.andresvasquez.ejemplofirebase.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andresvasquez.ejemplofirebase.R;
import com.example.andresvasquez.ejemplofirebase.controller.FirebaseController;
import com.example.andresvasquez.ejemplofirebase.model.Participantes;


/**
 * Created by andresvasquez on 4/6/16.
 */
public class NuevoDialog extends DialogFragment {
    private Dialog dialog;
    private Context context;
    private EditText txtNombre, txtEdad, txtEmail, txtCi;
    private Button btnEnviar;

    public static NuevoDialog newInstance() {
        NuevoDialog aboutDialog = new NuevoDialog();
        Bundle bundle = new Bundle();
        aboutDialog.setArguments(bundle);
        return aboutDialog;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        context=getActivity();

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_participante, null);

        txtCi=(EditText)rootView.findViewById(R.id.txtCi);
        txtNombre=(EditText)rootView.findViewById(R.id.txtNombre);
        txtEmail=(EditText)rootView.findViewById(R.id.txtEmail);
        txtEdad=(EditText)rootView.findViewById(R.id.txtEdad);
        btnEnviar=(Button)rootView.findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strCi=txtCi.getText().toString().trim();
                String strNombre=txtNombre.getText().toString().trim();
                String strEmail=txtEmail.getText().toString().trim();
                String strEdad=txtEdad.getText().toString().trim();

                if(strCi.isEmpty()){
                    Toast.makeText(context,"Ingrese el CI", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strNombre.isEmpty()){
                    Toast.makeText(context,"Ingrese el Nombre", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strEmail.isEmpty()){
                    Toast.makeText(context,"Ingrese el Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strEdad.isEmpty()){
                    Toast.makeText(context,"Ingrese la edad", Toast.LENGTH_SHORT).show();
                    return;
                }

                Participantes participante=new Participantes();
                participante.setCi(Integer.parseInt(strCi));
                participante.setNombre(strNombre);
                participante.setEmail(strEmail);
                participante.setEdad(Integer.parseInt(strEdad));
                FirebaseController.enviarParticipantes(participante);

                if(dialog.isShowing())
                    dialog.dismiss();
            }
        });

        builder.setView(rootView);
        dialog=builder.create();
        return dialog;
    }
}
