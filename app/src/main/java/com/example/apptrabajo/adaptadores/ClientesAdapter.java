package com.example.apptrabajo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.apptrabajo.R;
import com.example.apptrabajo.entidades.Clientes;

import java.util.ArrayList;
import java.util.List;

public class ClientesAdapter extends ArrayAdapter<Clientes> {

    ArrayList<Clientes> listaClientes;
    ArrayList<Clientes> nuevaListaCliente;
  //  List<Clientes> listaClientes;
    //  List<Usuario> usuarioList;
    //  public ItemClickListener listener;

    public ClientesAdapter(Context context, int id, List<Clientes> listCliente) {
        super(context, id, listCliente);
        this.listaClientes = new ArrayList<>();
        this.listaClientes.addAll(listCliente);
        this.nuevaListaCliente = new ArrayList<>();
        this.nuevaListaCliente.addAll(listCliente);

    }
    public static class ViewHolder {

        TextView tvNombre;
        TextView tvTelefono;
        //  TextView txtDireccion;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        ClientesAdapter.ViewHolder holder;
        if (convertview == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = layoutInflater.inflate(R.layout.usuarios_list,null);
            holder = new  ClientesAdapter.ViewHolder();

           holder.tvNombre = convertview.findViewById(R.id.txtNombre);
             // holder.txtDireccion = convertView.findViewById(R.id.txtId);
            holder.tvTelefono = convertview.findViewById(R.id.txtProfesion);

            convertview.setTag(holder);

        } else {

            holder = ( ClientesAdapter.ViewHolder) convertview.getTag();
        }

        Clientes clientes = nuevaListaCliente.get(position);

        holder.tvNombre.setText(clientes.getNombre());
        // holder.txtDireccion.setText(usuario.getDireccionCliente());
        holder.tvTelefono.setText(clientes.getTelefono());

        return convertview;


    }

    //public void deletCliente(id_)
/*
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(UsuariosHolder holder, int position) {
        holder.txtDocumento.setText(listaUsuarios.get(position).getIdCliente().toString());
        holder.txtNombre.setText(listaUsuarios.get(position).getNombreCliente().toString());
        holder.txtTelefono.setText(listaUsuarios.get(position).getTelefonoCliente().toString());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuariosHolder extends RecyclerView.ViewHolder{

        TextView txtDocumento,txtNombre,txtTelefono;

        public UsuariosHolder(View itemView) {
            super(itemView);
            txtDocumento= (TextView) itemView.findViewById(R.id.txtDocumento);
            txtNombre= (TextView) itemView.findViewById(R.id.txtNombre);
            txtTelefono= (TextView) itemView.findViewById(R.id.txtProfesion);

        }
    }
*/
}
