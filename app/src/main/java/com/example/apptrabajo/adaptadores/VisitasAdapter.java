package com.example.apptrabajo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.apptrabajo.R;
import com.example.apptrabajo.entidades.Visitas;

import java.util.ArrayList;
import java.util.List;

public class VisitasAdapter extends ArrayAdapter<Visitas> {

    ArrayList<Visitas> visitasArrayList;
    ArrayList<Visitas> nuevaVisitaList;

    public VisitasAdapter(Context context, int id, List<Visitas> listVisitas) {
        super(context, id, listVisitas );
        this.visitasArrayList = new ArrayList<>();
        this.visitasArrayList.addAll(listVisitas);
        this.nuevaVisitaList = new ArrayList<>();
        this.nuevaVisitaList.addAll(listVisitas);

    }

    public static class ViewHolder {

        TextView tvFechaVisita, tvIdVisita;
        TextView tvEstado;
        //  TextView txtDireccion;
    }
    @Override
    public View getView(int position, View v, ViewGroup parent) {

        VisitasAdapter.ViewHolder holder;
        if (v == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.item_visitas,null);
            holder = new VisitasAdapter.ViewHolder();

            holder.tvFechaVisita = v.findViewById(R.id.tv_fecha_visita);
            holder.tvIdVisita = v.findViewById(R.id.id_visita);
            holder.tvEstado = v.findViewById(R.id.tv_estado);

           v.setTag(holder);

        } else {

            holder = (VisitasAdapter.ViewHolder) v.getTag();
        }

        Visitas visitas = nuevaVisitaList.get(position);

        holder.tvFechaVisita.setText(visitas.getFecha_visita());
         holder.tvIdVisita.setText(String.valueOf(visitas.getId_visitas()));
        holder.tvEstado.setText(String.valueOf(visitas.getEstado()));

        return v;


    }
}
