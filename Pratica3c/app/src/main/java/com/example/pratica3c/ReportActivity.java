package com.example.pratica3c;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ReportActivity extends ListActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this);

        // Buscar logs do banco
        Cursor cursor = dbHelper.getAllLogs();

        // Montar array de strings no formato "msg - timestamp"
        int count = cursor.getCount();
        String[] logs = new String[count];

        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                String msg = cursor.getString(cursor.getColumnIndexOrThrow("msg"));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));
                logs[i++] = msg + " - " + timestamp;
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Criar adapter para mostrar a lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                logs
        );

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        // Extrai o msg (antes do " - ")
        String msg = item.split(" - ")[0];

        // Buscar latitude e longitude do último log com esse msg
        Cursor cursor = dbHelper.getLatLngPorLog(msg);

        if (cursor != null && cursor.moveToFirst()) {
            double lat = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
            double lon = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
            cursor.close();

            String texto = "Lat: " + lat + ", Lon: " + lon;
            Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Localização não encontrada.", Toast.LENGTH_SHORT).show();
        }
    }
}
