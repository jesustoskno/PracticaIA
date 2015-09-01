package com.practicaia.jtoscano.practicaia.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jtoscano on 31/08/2015.
 */
public class PracticaDb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;

    static final String DATABASE_NAME = "practicaia.db";

    public PracticaDb(Context contexto){
        super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        final String SQL_CREA_TABLA_CIUDADES = "CREATE TABLE" + Contrato.ContratoCiudades.TABLE_NAME + " (" +
                Contrato.ContratoCiudades.ID + "INTEGER PRIMARY KEY, " +
                Contrato.ContratoCiudades.ESTADO + "TEXT NOT NULL, " +
                Contrato.ContratoCiudades.ID_ESTADO + "TEXT NOT NULL, " +
                Contrato.ContratoCiudades.ID_PAIS + "TEXT NOT NULL, " +
                Contrato.ContratoCiudades.LATITUD + "TEXT NOT NULL, " +
                Contrato.ContratoCiudades.LONGITUD + "TEXT NOT NULL, " +
                Contrato.ContratoCiudades.NOMBRE + "TEXT NOT NULL, " +
                Contrato.ContratoCiudades.PAIS + "TEXT NOT NULL, " +
                " );";
        final String SQL_CREA_TABLA_COMPLEJOS = "CREATE TABLE" + Contrato.ContratoComplejos.TABLE_NAME + " ("+
                Contrato.ContratoComplejos.ID + "INTEGER PRIMARY KEY, " +
                Contrato.ContratoComplejos.DIRECCION + "TEXT NOT NULL, " +
                Contrato.ContratoComplejos.ES_COMPRA + "TEXT NOT NULL, " +
                Contrato.ContratoComplejos.NOMBRE + "TEXT NOT NULL, " +
                Contrato.ContratoComplejos.TELEFONO + "TEXT NOT NULL, " +
                Contrato.ContratoComplejos.URL + "TEXT NOT NULL, "+
                "FOREIGN KEY (" + Contrato.ContratoComplejos.IDCIUDAD + ") REFERENCES " +
                Contrato.ContratoCiudades.ID +
                " );";

        db.execSQL(SQL_CREA_TABLA_CIUDADES);
        db.execSQL(SQL_CREA_TABLA_COMPLEJOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contrato.ContratoCiudades.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contrato.ContratoComplejos.TABLE_NAME);
    }
}
