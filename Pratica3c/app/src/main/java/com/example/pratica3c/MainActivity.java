package com.example.pratica3c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    String[] opcoes = {
            "Minha casa na cidade natal",
            "Minha casa em Viçosa",
            "Meu departamento",
            "Relatório",
            "Fechar aplicação"

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                opcoes
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            switch (position) {
                case 0:
                case 1:
                case 2:
                    Intent intent = new Intent(this, MapActivity.class);
                    intent.putExtra("local", position);
                    startActivity(intent);
                    break;

                case 3:
                    Intent reportIntent = new Intent(this, ReportActivity.class);
                    startActivity(reportIntent);
                    break;


                case 4:
                    finish(); // Fecha o app
                    break;
            }
        });

    }



}