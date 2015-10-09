package com.practicaia.jtoscano.practicaia;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by jtoscano on 06/10/2015.
 */
public class CiudadesAdapter extends CursorAdapter {

    public CiudadesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.lista_ciudades, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String nombreCiudades = cursor.getString(FragmentoCiudades.CIUDADES_NOMBRE);
        String id = cursor.getString(FragmentoCiudades.CIUDADES_ID);
        TextView nombresView = (TextView) view.findViewById(R.id.lista_ciudades_textview);
        nombresView.setText(nombreCiudades);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
