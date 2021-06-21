package com.example.apptrabajo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.apptrabajo.api.Consultas;
import com.example.apptrabajo.databinding.ActivityMainBinding;
import com.example.apptrabajo.datos.BaseDatosApp;
import com.example.apptrabajo.entidades.Productos;
import com.example.apptrabajo.entidades.Venta;
import com.google.android.material.navigation.NavigationView;

public class
MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
Productos productos;
//    private static final String TABLE_PRODUCTO = "Producto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.apptrabajo.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

  //      Consultas consultas = new Consultas(MainActivity.this);
//        consultas.verificarTabla(productos);

        binding.appBarMain.fab.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, UbicacionActivity.class);
            startActivity(intent);

        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
               R.id.nav_vistasActivity, R.id.nav_mostrarDetalle, R.id.nav_perfilFragment, R.id.nav_perfilEmpleado, R.id.nav_gallery, R.id.nav_registroClienteFragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
            /*   int id_venta = 0;
                Venta venta = null;
                final BaseDatosApp bdLocal = new BaseDatosApp(MainActivity.this);
               venta = bdLocal.ultimaVenta();
                Toast.makeText(this, "ultima venta es" + venta.getId_venta(), Toast.LENGTH_SHORT).show();




               BaseDatosApp bdLocal = new BaseDatosApp(this.getApplicationContext());
                SQLiteDatabase db = bdLocal.getReadableDatabase();
                db.execSQL("delete from " + TABLE_PRODUCTO);
                Toast.makeText(this, "Se ha eliminado, actualiza la vista", Toast.LENGTH_LONG).show();
                onStart();  */
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

