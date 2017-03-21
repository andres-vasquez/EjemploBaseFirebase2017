package com.example.andresvasquez.ejemplofirebase.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.andresvasquez.ejemplofirebase.R;
import com.example.andresvasquez.ejemplofirebase.model.Participantes;

import java.util.List;

/**
 * Created by andresvasquez on 10/6/16.
 */
public class ParticipantesAdapter extends BaseAdapter {
    private Context context;
    private List<Participantes> items;

    public ParticipantesAdapter(Context context, List<Participantes> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {

        return items.size();
    }

    @Override
    public Participantes getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getCi();
    }

    static class ViewHolder{

        TextView txtCi;
        TextView txtEmail;
        TextView txtNombre;
        TextView txtEdad;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            holder=new ViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_participante, null);

            holder.txtCi = (TextView)convertView.findViewById(R.id.txtCi);
            holder.txtEmail = (TextView)convertView.findViewById(R.id.txtEmail);
            holder.txtNombre = (TextView)convertView.findViewById(R.id.txtNombre);
            holder.txtEdad = (TextView)convertView.findViewById(R.id.txtEdad);
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }

        Participantes dev=items.get(position);
        holder.txtCi.setText(String.valueOf(dev.getCi()));
        holder.txtEmail.setText(dev.getEmail());
        holder.txtNombre.setText(dev.getNombre());
        holder.txtEdad.setText(dev.getEdad()+" años");
        return convertView;
    }
}
