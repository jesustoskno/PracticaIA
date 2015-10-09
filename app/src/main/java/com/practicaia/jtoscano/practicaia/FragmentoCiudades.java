package com.practicaia.jtoscano.practicaia;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.practicaia.jtoscano.practicaia.data.Contrato;

import java.io.FileDescriptor;
import java.io.PrintWriter;


/**
 * Created by jtoscano on 27/08/2015.
 */
public class FragmentoCiudades extends Fragment{

    private static final int CIUDADES_LOADER = 0;

    private static final String[] CIUDADES_COLUMNAS = {
            "1 _id",
            Contrato.ContratoCiudades.ID,
            Contrato.ContratoCiudades.ESTADO,
            Contrato.ContratoCiudades.ID_ESTADO,
            Contrato.ContratoCiudades.ID_PAIS,
            Contrato.ContratoCiudades.LATITUD,
            Contrato.ContratoCiudades.LONGITUD,
            Contrato.ContratoCiudades.NOMBRE,
            Contrato.ContratoCiudades.PAIS,
    };

    static final int CIUDADES_ID = 1;
    static final int CIUDADES_NOMBRE = 7;

    private CiudadesAdapter mCiudadesAdapter;

    public FragmentoCiudades() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mCiudadesAdapter = new CiudadesAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_ciudades);
        listView.setAdapter(mCiudadesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                String idCiudad = cursor.getString(cursor.getColumnIndex(Contrato.ContratoCiudades.ID));
                if (cursor != null) {
                    Intent intent = new Intent(getActivity(), DetalleCiudades.class)
                            .putExtra("posicion", idCiudad);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //mostrarDatos();
        //updateCiudades();
        super.onActivityCreated(savedInstanceState);
    }

    private void updateCiudades() {
        FetchCiudades ciudadesTastk = new FetchCiudades(getActivity(), new FetchCiudades.mostrarListener() {
            @Override
            public void mostrar() {
                Log.d("TAG", "termino de ejecucion");
                mostrarDatos();
            }
        });
        ciudadesTastk.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        //loading

        updateCiudades();
    }

    private void mostrarDatos(){

        getLoaderManager().initLoader(CIUDADES_LOADER, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                String sortOrder = Contrato.ContratoCiudades.NOMBRE + " ASC";

                return new CursorLoader(getActivity(),
                        Contrato.ContratoCiudades.CONTENT_URI,
                        CIUDADES_COLUMNAS,
                        null,
                        null,
                        sortOrder);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                mCiudadesAdapter.swapCursor(data);
                mCiudadesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mCiudadesAdapter.swapCursor(null);
            }
        });
    }
}
