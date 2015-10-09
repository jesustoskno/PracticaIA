package com.practicaia.jtoscano.practicaia;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.practicaia.jtoscano.practicaia.data.Contrato;

/**
 * Created by jtoscano on 26/08/2015.
 */
public class DetalleCiudades extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalleciudades);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_ciudades, new FragmentoDetalle())
                    .commit();
        }
    }
}
