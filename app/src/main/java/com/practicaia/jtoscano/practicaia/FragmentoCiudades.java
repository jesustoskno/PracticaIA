package com.practicaia.jtoscano.practicaia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;

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
 * A placeholder fragment containing a simple view.
 */
public class FragmentoCiudades extends Fragment {

    List<String> arrayEstado = new ArrayList<String>();
    List<String> arrayId = new ArrayList<String>();
    List<String> arrayIdEstado = new ArrayList<String>();
    List<String> arrayIdPais = new ArrayList<String>();
    List<String> arrayLatitud = new ArrayList<String>();
    List<String> arrayLongitud = new ArrayList<String>();
    List<String> arrayNombre = new ArrayList<String>();
    List<String> arrayPais = new ArrayList<String>();
    List<String> arrayUris = new ArrayList<String>();


    private ArrayAdapter<String> CiudadesAdaptador;

    public FragmentoCiudades() {
    }
/**
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
**/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        CiudadesAdaptador =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.lista_ciudades,
                        R.id.lista_ciudades_textview,
                        arrayNombre
                );

        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_ciudades);
        listView.setAdapter(CiudadesAdaptador);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String posicionCiudad = CiudadesAdaptador.getItem(position);
                String idPosicion=arrayId.get(position);
                Intent intent = new Intent(getActivity(), DetalleCiudades.class)
                        .putExtra(Intent.EXTRA_TEXT, posicionCiudad).putExtra("posicion",idPosicion);
                startActivity(intent);

            }
        });

       return rootView;
    }

    private void actualizarCiudades(){
        FetchCiudades actualizar = new FetchCiudades();
        actualizar.execute();
    }

    public void onResume(){
        super.onStart();
        actualizarCiudades();
    }

    public class FetchCiudades extends AsyncTask<String, Void, String[]>{


        private String[] obtenerCiudadesJson(String ciudadesJsonStr)
            throws JSONException {

            final String ESTADO = "Estado";
            final String ID = "Id";
            final String IDESTADO = "IdEstado";
            final String IDPAIS = "IdPais";
            final String LATITUD = "Latitud";
            final String LONGITUD = "Longitud";
            final String NOMBRE = "Nombre";
            final String PAIS = "Pais";
            final String URIS = "Uris";

            JSONArray arregloJson = new JSONArray(ciudadesJsonStr);
            String[] resultado = new String[arregloJson.length()];

                for (int i=0 ; i<arregloJson.length(); i++){
                    String estado;
                    String id;
                    String idestado;
                    String idpais;
                    String latitud;
                    String longitud;
                    String nombre;
                    String pais;
                    String uris;


                    JSONObject objetoJson = arregloJson.getJSONObject(i);
                    estado = objetoJson.getString(ESTADO);
                    id = objetoJson.getString(ID);
                    idestado = objetoJson.getString(IDESTADO);
                    idpais = objetoJson.getString(IDPAIS);
                    latitud = objetoJson.getString(LATITUD);
                    longitud = objetoJson.getString(LONGITUD);
                    nombre = objetoJson.getString(NOMBRE);
                    pais = objetoJson.getString(PAIS);
                    uris = objetoJson.getString(URIS);

                    arrayEstado.add(estado);
                    arrayId.add(id);
                    arrayIdEstado.add(idestado);
                    arrayIdPais.add(idpais);
                    arrayLatitud.add(latitud);
                    arrayLongitud.add(longitud);
                    arrayNombre.add(nombre);
                    arrayPais.add(pais);
                    arrayUris.add(uris);
                }

            return resultado;

        }

        @Override
        protected String[] doInBackground(String... params){

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String ciudadesJsonStr = null;

            try {
                URL url = new URL("http://api.cinepolis.com.mx/Consumo.svc/json/ObtenerCiudades");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                ciudadesJsonStr = buffer.toString();

            } catch (IOException e) {


                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {

                    }
                }
            }
            try{
                return obtenerCiudadesJson(ciudadesJsonStr);
            }
            catch(JSONException e){

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            CiudadesAdaptador.notifyDataSetChanged();
        }
    }
}
