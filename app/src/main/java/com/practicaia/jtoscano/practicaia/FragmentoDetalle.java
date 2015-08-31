package com.practicaia.jtoscano.practicaia;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtoscano on 27/08/2015.
 */
public class FragmentoDetalle extends Fragment {

    List<String> arDireccion = new ArrayList<String>();
    List<String>  arCompra= new ArrayList<String>();
    List<String>  arId= new ArrayList<String>();
    List<String>  arIdCiudad= new ArrayList<String>();
    List<String>  arIdEstado= new ArrayList<String>();
    List<String>  arIdVista= new ArrayList<String>();
    List<String>  arLatitud= new ArrayList<String>();
    List<String>  arLongitud= new ArrayList<String>();
    List<String>  arNombre= new ArrayList<String>();
    List<String>  arTelefono= new ArrayList<String>();
    List<String>  arUrl= new ArrayList<String>();

    private ArrayAdapter<String> detalleAdaptador;
    String idPosicion;
    public FragmentoDetalle() {
    }

    /** @Override
    public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    }**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("posicion")) {
            idPosicion = intent.getStringExtra("posicion");
        }

        detalleAdaptador =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.lista_detalle,
                        R.id.lista_detalle,
                        arNombre
                );

        FetchDetalles fetdet = new FetchDetalles();
        fetdet.execute();

        View rootView = inflater.inflate(R.layout.fragmento_detalleciudades, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_detalle);
        listView.setAdapter(detalleAdaptador);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String latitud = arLatitud.get(position);
                String longitud = arLongitud.get(position);
                String nombre = arNombre.get(position);
                String telefono = arTelefono.get(position);
                String direccion = arDireccion.get(position);
                String url = arUrl.get(position);


                Intent intent = new Intent(getActivity(), MapsActivity.class)
                        .putExtra("latitud", latitud)
                        .putExtra("longitud", longitud)
                        .putExtra("nombre", nombre)
                        .putExtra("telefono", telefono)
                        .putExtra("direccion", direccion)
                        .putExtra("url", url);
                startActivity(intent);

            }
        });

        return rootView;
    }

    public class FetchDetalles extends AsyncTask<String, Void, String[]> {

        private String[] obtenerDetallesJson(String detalleJsonStr)
                throws JSONException {

            final String DIRECCION= "Direccion";
            final String COMPRA= "EsCompraAnticipada";
            final String ID= "Id";
            final String IDCIUDAD= "IdCiudad";
            final String IDESTADO= "IdEstado";
            final String IDVISTA= "IdVista";
            final String LATITUD= "Latitud";
            final String LONGITUD= "Longitud";
            final String NOMBRE= "Nombre";
            final String TELEFONO= "Telefono";
            final String URL= "Url";

            JSONArray arregloJson = new JSONArray(detalleJsonStr);
            String[] resultado = new String[arregloJson.length()];

            for(int i=0 ; i<detalleJsonStr.length() ; i++){
                String direccion;
                String compra;
                String id;
                String idCiudad;
                String idEstado;
                String idVista;
                String latitud;
                String longitud;
                String nombre;
                String telefono;
                String url;

                JSONObject obJson = arregloJson.getJSONObject(i);
                direccion = obJson.getString(DIRECCION);
                compra = obJson.getString(COMPRA);
                id = obJson.getString(ID);
                idCiudad = obJson.getString(IDCIUDAD);
                idEstado = obJson.getString(IDESTADO);
                idVista = obJson.getString(IDVISTA);
                latitud = obJson.getString(LATITUD);
                longitud = obJson.getString(LONGITUD);
                nombre = obJson.getString(NOMBRE);
                telefono = obJson.getString(TELEFONO);
                url = obJson.getString(URL);

                arDireccion.add(direccion);
                arCompra.add(compra);
                arId.add(id);
                arIdCiudad.add(idCiudad);
                arIdEstado.add(idEstado);
                arIdVista.add(idVista);
                arLatitud.add(latitud);
                arLongitud.add(longitud);
                arNombre.add(nombre);
                arTelefono.add(telefono);
                arUrl.add(url);

            }
            return resultado;

        }

        @Override
        protected String[] doInBackground(String... params){

            HttpURLConnection urlConexion = null;
            BufferedReader reader = null;
            String detalleJsonStr = null;

            String base="http://api.cinepolis.com.mx/consumo.svc/json/ObtenerComplejos?idsCiudades=";
            String terminacion="&idsComplejos=0";
            try{
                final String DETALLE_BASE_URL= base;
                final String DETALLE_FINAL_URL=terminacion;

                String uriFinal= DETALLE_BASE_URL+idPosicion+DETALLE_FINAL_URL;
                URL url = new URL(uriFinal.toString());

                urlConexion=(HttpURLConnection) url.openConnection();
                urlConexion.setRequestMethod("GET");
                urlConexion.connect();
                InputStream inputStream = urlConexion.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream==null){
                    return null;
                }
                reader=new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line=reader.readLine())!=null){
                    buffer.append(line+"\n");
                }

                if(buffer.length()==0){
                    return null;
                }

                detalleJsonStr = buffer.toString();

            } catch (IOException e){
                return null;
            }
            finally {
                if(urlConexion!=null){
                    try{
                        reader.close();
                    }catch (final IOException e){
                        Log.e(null, "Error cerrando el flujo", e);
                    }
                }
            }
            try{
                return obtenerDetallesJson(detalleJsonStr);
            }catch(JSONException e){
                Log.e(null, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            detalleAdaptador.notifyDataSetChanged();
        }
    }
}