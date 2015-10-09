package com.practicaia.jtoscano.practicaia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import com.practicaia.jtoscano.practicaia.data.Contrato;
import com.practicaia.jtoscano.practicaia.data.PracticaDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Created by jtoscano on 07/10/2015.
 */
public class FetchCiudades extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchCiudades.class.getSimpleName();

    private final Context mContext;

    private static mostrarListener escucha;

    public FetchCiudades(Context context, mostrarListener escucha) {
        mContext = context;
        this.escucha=escucha;
    }

    private void getCiudadesDataJson(String ciudadesJsonStr)

            throws JSONException {

        SQLiteDatabase db = new PracticaDb(mContext).getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(Contrato.ContratoCiudades.TABLE_NAME, null, null);

        final String ESTADO = "Estado";
        final String ID = "Id";
        final String ID_ESTADO = "IdEstado";
        final String ID_PAIS = "IdPais";
        final String LATITUD = "Latitud";
        final String LONGITUD = "Longitud";
        final String NOMBRE = "Nombre";
        final String PAIS = "Pais";

        try {
            JSONArray arregloJson = new JSONArray(ciudadesJsonStr);
            Vector<ContentValues> cVVector = new Vector<>(arregloJson.length());

            for (int i = 0; i < arregloJson.length(); i++) {

                String estado;
                String id;
                String idestado;
                String idpais;
                String latitud;
                String longitud;
                String nombre;
                String pais;

                JSONObject objetoJson = arregloJson.getJSONObject(i);

                estado = objetoJson.getString(ESTADO);
                id = objetoJson.getString(ID);
                idestado = objetoJson.getString(ID_ESTADO);
                idpais = objetoJson.getString(ID_PAIS);
                latitud = objetoJson.getString(LATITUD);
                longitud = objetoJson.getString(LONGITUD);
                nombre = objetoJson.getString(NOMBRE);
                pais = objetoJson.getString(PAIS);

                ContentValues ciudadesValues = new ContentValues();

                ciudadesValues.put(Contrato.ContratoCiudades.ESTADO, estado);
                ciudadesValues.put(Contrato.ContratoCiudades.ID, id);
                ciudadesValues.put(Contrato.ContratoCiudades.ID_ESTADO, idestado);
                ciudadesValues.put(Contrato.ContratoCiudades.ID_PAIS, idpais);
                ciudadesValues.put(Contrato.ContratoCiudades.LATITUD, latitud);
                ciudadesValues.put(Contrato.ContratoCiudades.LONGITUD, longitud);
                ciudadesValues.put(Contrato.ContratoCiudades.NOMBRE, nombre);
                ciudadesValues.put(Contrato.ContratoCiudades.PAIS, pais);

                cVVector.add(ciudadesValues);

            }

            int inserted = 0;

            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                inserted = mContext.getContentResolver().bulkInsert(Contrato.ContratoCiudades.CONTENT_URI, cvArray);
            }

            Log.d(LOG_TAG, "Se completo la extracción de datos para ciudades. " + inserted + " Insertado");
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {

        HttpURLConnection conexionURL = null;
        BufferedReader reader = null;

        String ciudadesJsonStr = null;

        try {
            URL url = new URL("http://api.cinepolis.com.mx/Consumo.svc/json/ObtenerCiudades");

            conexionURL = (HttpURLConnection) url.openConnection();
            conexionURL.setRequestMethod("GET");
            conexionURL.connect();

            InputStream inputStream = conexionURL.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null){
                return null;
            }
            reader= new BufferedReader(new InputStreamReader(inputStream));
            
            String line;
            while ((line = reader.readLine()) !=null){
                buffer.append(line + "\n");
            }

            ciudadesJsonStr = buffer.toString();
            getCiudadesDataJson(ciudadesJsonStr);
        } catch (IOException e){
            Log.e(LOG_TAG, "Error ", e);
        }catch (JSONException e){
            Log.e(LOG_TAG, e.getMessage(), e);
        }finally {
            if(conexionURL !=null){
                conexionURL.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                }catch (final IOException e ){
                    Log.e(LOG_TAG, "Error cerrando el flujo ", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //super.onPostExecute(aVoid);
        escucha.mostrar();
    }

    public interface mostrarListener {
        void mostrar();
    }
}
