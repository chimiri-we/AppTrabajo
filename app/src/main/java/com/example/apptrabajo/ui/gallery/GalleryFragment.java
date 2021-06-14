package com.example.apptrabajo.ui.gallery;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.apptrabajo.R;
import com.example.apptrabajo.databinding.FragmentGalleryBinding;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Clientes;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    private static final String DATABASE_NAME = "Trabajo";
    private static final String TABLE_CLIENTE = "Cliente";
    ArrayList<String> datosProducto;
    ArrayList<Clientes> listaProducto;
    BaseDatosApp dbProducto;
    //SQLiteOpenHelper sqProducto;
    ListView listView;


    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        listView = root.findViewById(R.id.produclistt);


        dbProducto = new BaseDatosApp(getContext());
        consultarProducto();

        ArrayAdapter<CharSequence> adapterlistaProducto = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, datosProducto);
        listView.setAdapter(adapterlistaProducto);

        //   ArrayAdapter<CharSequence> adapterPro = new ArrayAdapter(this, android.R.layout.simple_spinner_item, datosProducto);
        // newSpiner.setAdapter(adapterPro);
        return root;

    }

    private void consultarProducto() {
        SQLiteDatabase db=dbProducto.getReadableDatabase();

      Clientes producto=null;
        listaProducto = new ArrayList<Clientes>();
        Cursor cursor = db.rawQuery("select * from Cliente", null);
        while (cursor.moveToNext()){
            producto=new Clientes();
            producto.setId_cliente(cursor.getInt(0));
            producto.setNombre(cursor.getString(1));
            producto.setTelefono(cursor.getString(2));
            listaProducto.add(producto);
        }
        obtenerLista();


    }

    public void obtenerLista() {

        datosProducto = new ArrayList<String>();
      //  datosProducto.add("selecciona");

        for (int i=0;i<listaProducto.size();i++) {
            datosProducto.add(listaProducto.get(i).getNombre()+" $ "+listaProducto.get(i).getTelefono());
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}