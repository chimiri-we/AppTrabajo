package com.example.apptrabajo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.apptrabajo.R;
import com.example.apptrabajo.entidades.Productos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoAdapter extends ArrayAdapter<Productos> {
    ArrayList<Productos> listProducto;
    ArrayList<Productos> listaBuscarProducto;

    public ProductoAdapter.DataFiltering dataFiltering;

    //  public ItemClickListener listener;

    public ProductoAdapter(Context context, int id, List<Productos> nuevalistaProducto) {
        super(context, id, nuevalistaProducto);
        this.listProducto = new ArrayList<>();

        this.listProducto.addAll(nuevalistaProducto);
        this.listaBuscarProducto = new ArrayList<>();
        this.listaBuscarProducto.addAll(nuevalistaProducto);


    }




    public static class ViewHolder {

        TextView Name;
        TextView Precio;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProductoAdapter.ViewHolder holder=null;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.productos_list,null);
            holder = new ProductoAdapter.ViewHolder();

            holder.Name = convertView.findViewById(R.id.txt_nombre_producto);

            holder.Precio = convertView.findViewById(R.id.txt_precio);

            convertView.setTag(holder);

        } else {

            holder = (ProductoAdapter.ViewHolder) convertView.getTag();
        }

        Productos producto = listProducto.get(position);

        holder.Name.setText(producto.getNombre());

        holder.Precio.setText(producto.getPrecio());

        return convertView;

    }

    public void filtrado(String txtBuscar){

        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listProducto.clear();
            listProducto.addAll(listaBuscarProducto);
            ;
        }else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Productos> collection = listProducto.stream().filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                listProducto.clear();
                listProducto.addAll(collection);

            }else {
                for (Productos c: listaBuscarProducto) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listProducto.add(c);
                    }

                }



            }
        }
        notifyDataSetChanged();
    }
    @Override
    public Filter getFilter() {

        if (dataFiltering == null) {

            dataFiltering = new ProductoAdapter.DataFiltering();
        }
        return dataFiltering;
    }

    private class DataFiltering extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence.toString().length() > 0) {

                ArrayList<Productos> array = new ArrayList<>();

                for (int i = 0, l = listProducto.size(); i < l; i++) {
                    Productos subject = listProducto.get(i);

                    if (subject.toString().toLowerCase().contains(charSequence))

                        array.add(subject);
                }
                filterResults.count = array.size();

                filterResults.values = array;
            } else {
                synchronized (this) {
                    filterResults.values = listProducto;

                    filterResults.count = listProducto.size();
                }
            }
            return filterResults;
        }



        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            listProducto = (ArrayList<Productos>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = listProducto.size(); i < l; i++)
                add(listaBuscarProducto.get(i));

            notifyDataSetInvalidated();
        }
    }/*
    @Override
    public void onBindViewHolder(ProductoHolder holder, int position) {
      //  holder.txtIdProducto.setText(listaProducto.get(position).getIdProducto().toString());
        holder.txtNombreProducto.setText(listaProducto.get(position).getNombreProducto().toString());
        holder.txtPrecio.setText(listaProducto.get(position).getPrecio().toString());
    }

    @Override
    public int getItemCount() {
        return listaProducto.size();
    }

    public class ProductoHolder extends RecyclerView.ViewHolder{

        TextView txtIdProducto,txtNombreProducto,txtPrecio;

        public ProductoHolder(View itemView) {
            super(itemView);
         //   txtIdProducto= (TextView) itemView.findViewById(R.id.txt_id);
            txtNombreProducto= (TextView) itemView.findViewById(R.id.txt_nombre_producto);
            txtPrecio= (TextView) itemView.findViewById(R.id.txt_precio);

        }
    }

    public void filter(final String stringBuscar){
        if (stringBuscar.length() == 0) {
            listaProducto.clear();
            listaProducto.addAll(listaBuscarProducto);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                List<Producto> collect =listaBuscarProducto.stream()
                        .filter(i -> i.getNombreProducto().toLowerCase().contains(stringBuscar))
                        .collect(Collectors.toList());
                listaProducto.clear();
                listaProducto.addAll(collect);

                }
            else{
                listaProducto.clear();
                for (Producto i : listaBuscarProducto) {
                    if (i.getNombreProducto().toLowerCase().contains(stringBuscar)) {
                        listaProducto.add(i);
                    }
                }
            }

            }
    notifyDataSetChanged();
    }
*/
}

