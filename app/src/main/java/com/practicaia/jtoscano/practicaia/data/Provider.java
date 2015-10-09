package com.practicaia.jtoscano.practicaia.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;

/**
 * Created by jtoscano on 06/10/2015.
 */
public class Provider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private PracticaDb mHelper;

    static final int CIUDADES = 1;
    static final int COMPLEJOS = 2;

    @Override
    public boolean onCreate() {
        mHelper = new PracticaDb(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case CIUDADES: {
                long _id = db.insert(Contrato.ContratoCiudades.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = Contrato.ContratoCiudades.buildCiudadesUri(_id);
                else
                    throw new android.database.SQLException("Fallo al insertar fila en: " + uri);
                break;
            }
            case COMPLEJOS: {
                long _id = db.insert(Contrato.ContratoComplejos.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = Contrato.ContratoComplejos.buildComplejosUri(_id);
                else
                    throw new android.database.SQLException("Fallo al insertar fila en: " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Uri desconocida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contrato.CONTENT_AUTHORITY;

        matcher.addURI(authority, Contrato.PATH_CIUDADES, CIUDADES);
        matcher.addURI(authority, Contrato.PATH_COMPLEJOS, COMPLEJOS);

        return matcher;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor returnCursor = null;
        switch (sUriMatcher.match(uri)) {
            case CIUDADES: {
                returnCursor = mHelper.getReadableDatabase().query(Contrato.ContratoCiudades.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case COMPLEJOS: {
                returnCursor = mHelper.getReadableDatabase().query(Contrato.ContratoComplejos.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            }
        }
        return returnCursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        if (null == selection) selection = "1";
        switch (match) {
            case CIUDADES:
                rowsDeleted = db.delete(
                        Contrato.ContratoCiudades.TABLE_NAME, selection, selectionArgs);
                break;
            case COMPLEJOS:
                rowsDeleted = db.delete(
                        Contrato.ContratoComplejos.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Uri desconocida: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case CIUDADES:
                rowsUpdated = db.update(Contrato.ContratoCiudades.TABLE_NAME, values, selection, selectionArgs);
                break;

            case COMPLEJOS:
                rowsUpdated = db.update(Contrato.ContratoComplejos.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("uri desconocida: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case CIUDADES:
                return Contrato.ContratoCiudades.CONTENT_TYPE;
            case COMPLEJOS:
                return Contrato.ContratoComplejos.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("uri desconocida: " + uri);
        }
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CIUDADES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(Contrato.ContratoCiudades.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public void shutdown() {
        mHelper.close();
        super.shutdown();
    }
}


