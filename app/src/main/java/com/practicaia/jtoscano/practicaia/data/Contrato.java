package com.practicaia.jtoscano.practicaia.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jtoscano on 31/08/2015.
 */
public class Contrato {

    public static final String CONTENT_AUTHORITY = "com.practicaia.jtoscano.practicaia";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CIUDADES = "ciudades";
    public static final String PATH_COMPLEJOS = "complejos";

    public static final class ContratoCiudades implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CIUDADES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CIUDADES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CIUDADES;

        public static final String TABLE_NAME="ciudades";
        public static final String ESTADO="Estado";
        public static final String ID="Id";
        public static final String ID_ESTADO="IdEstado";
        public static final String ID_PAIS="IdPais";
        public static final String LATITUD="Latitud";
        public static final String LONGITUD="Longitud";
        public static final String NOMBRE="Nombre";
        public static final String PAIS="Pais";

        public static Uri buildCiudadesUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ContratoComplejos implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMPLEJOS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMPLEJOS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMPLEJOS;

        public static final String TABLE_NAME = "complejos";
        public static final String ID="Id";
        public static final String IDCIUDAD="IdCiudad";
        public static final String DIRECCION = "Direccion";
        public static final String ES_COMPRA = "EsCompraAnticipada";
        public static final String LATITUD = "Latitud";
        public static final String LONGITUD = "Longitud";
        public static final String NOMBRE = "Nombre";
        public static final String TELEFONO = "Telefono";
        public static final String URL = "Url";
        public static final String IDESTADO = "IdEstado";
        public static final String IDVISTA = "IdVista";


        public static Uri buildComplejosUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


}
