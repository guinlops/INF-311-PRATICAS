package com.example.pratica3c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "appdb";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cria tabela Location
        db.execSQL("CREATE TABLE Location (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT UNIQUE," +
                "latitude REAL," +
                "longitude REAL" +
                ");");

        // Cria tabela Logs
        db.execSQL("CREATE TABLE Logs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "msg TEXT," +
                "timestamp TEXT," +
                "id_location INTEGER," +
                "FOREIGN KEY(id_location) REFERENCES Location(id)" +
                ");");

        // Popula Location
        db.execSQL("INSERT INTO Location (name, latitude, longitude) VALUES ('Cidade Natal', -16.730848476134504, -43.859748612317546);");
        db.execSQL("INSERT INTO Location (name, latitude, longitude) VALUES ('Viçosa', -20.752922767603394, -42.88204798158467);");
        db.execSQL("INSERT INTO Location (name, latitude, longitude) VALUES ('DPI', -20.76479700617575, -42.86846080432306);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Logs;");
        db.execSQL("DROP TABLE IF EXISTS Location;");
        onCreate(db);
    }

    // 🔍 Buscar localização por nome
    public Localizacao buscarLocalizacaoPorNome(String nome) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id, latitude, longitude FROM Location WHERE name = ?",
                new String[]{nome}
        );

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            double latitude = cursor.getDouble(1);
            double longitude = cursor.getDouble(2);
            cursor.close();
            return new Localizacao(id, latitude, longitude);
        }
        return null;
    }

    // 📝 Inserir log
    public void inserirLog(String msg, String timestamp, int idLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("msg", msg);
        values.put("timestamp", timestamp);
        values.put("id_location", idLocation);
        db.insert("Logs", null, values);
    }

    // 📦 Classe para representar uma localização
    public static class Localizacao {
        public int id;
        public double latitude;
        public double longitude;

        public Localizacao(int id, double latitude, double longitude) {
            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    public Cursor getAllLogs() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT msg, timestamp FROM Logs", null);
    }

    public Cursor getLatLngPorLog(String msg) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT L.latitude, L.longitude " +
                        "FROM Logs AS G INNER JOIN Location AS L ON G.id_location = L.id " +
                        "WHERE G.msg = ? " +
                        "ORDER BY G.id DESC LIMIT 1",
                new String[]{msg}
        );
    }


}
