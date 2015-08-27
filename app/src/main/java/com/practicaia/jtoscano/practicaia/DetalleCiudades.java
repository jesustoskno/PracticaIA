package com.practicaia.jtoscano.practicaia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
                    .add(R.id.container, new FragmentoDetalle())
                    .commit();
        }
    }
}
