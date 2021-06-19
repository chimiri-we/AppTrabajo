package com.example.apptrabajo.ui.gallery;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.apptrabajo.R;
import com.example.apptrabajo.adaptadores.ProductoAdapter;
import com.example.apptrabajo.databinding.FragmentGalleryBinding;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Clientes;
import com.example.apptrabajo.entidades.Productos;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    private static final String DATABASE_NAME = "Trabajo";
    private static final String TABLE_CLIENTE = "Cliente";
    ArrayList<String> datosProducto;
    ArrayList<Productos> listaProducto;
    BaseDatosApp dbProducto;
    //SQLiteOpenHelper sqProducto;
    ListView listView;
    ProductoAdapter adapter;
    GridView gridViewJoyas;


    ImageView imageView;
    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        gridViewJoyas = binding.gvJoyas;


        dbProducto = new BaseDatosApp(getContext());
        consultarProducto();

//        ArrayAdapter<CharSequence> adapterlistaProducto = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, datosProducto);
  //      listView.setAdapter(adapterlistaProducto);

        //   ArrayAdapter<CharSequence> adapterPro = new ArrayAdapter(this, android.R.layout.simple_spinner_item, datosProducto);
        // newSpiner.setAdapter(adapterPro);
        return root;

    }

    private void consultarProducto() {
        SQLiteDatabase db=dbProducto.getReadableDatabase();

      Productos producto=null;
        listaProducto = new ArrayList<Productos>();
        Cursor cursor = db.rawQuery("select * from Producto", null);
        while (cursor.moveToNext()){
            producto=new Productos();
            producto.setId_remoto(cursor.getInt(1));
            producto.setNombre(cursor.getString(2));
            producto.setPrecio(cursor.getString(3));
            listaProducto.add(producto);
        }
        adapter = new ProductoAdapter(getContext(), R.layout.layout_item_joya, listaProducto);

        gridViewJoyas.setAdapter(adapter);
        //obtenerLista();


    }

    public void obtenerLista() {

        datosProducto = new ArrayList<String>();
      //  datosProducto.add("selecciona");

        for (int i=0;i<listaProducto.size();i++) {
            datosProducto.add(listaProducto.get(i).getNombre());
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}