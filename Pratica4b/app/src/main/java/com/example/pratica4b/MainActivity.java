package com.example.pratica4b;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private float valorLuminosidade;
    private float valorProximidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnDevolver = findViewById(R.id.btnDevolver);

        // Recupera os dados da Intent que iniciou esta Activity
        Intent intentRecebida = getIntent();
        if (intentRecebida != null) {
            valorLuminosidade = intentRecebida.getFloatExtra("valor_luminosidade", -1f);
            valorProximidade = intentRecebida.getFloatExtra("valor_proximidade", -1f);
        }

        btnDevolver.setOnClickListener(view -> {
            // Classifica os valores recebidos e prepara a Intent de resultado
            Intent intentResultado = new Intent();

            // Parâmetro de classificação da luz
            if (valorLuminosidade < 20.0f) {
                intentResultado.putExtra("classificacao_luz", "baixa");
            } else {
                intentResultado.putExtra("classificacao_luz", "alta");
            }

            // Parâmetro de classificação de proximidade
            if (valorProximidade > 3.0f) {
                intentResultado.putExtra("classificacao_proximidade", "distante");
            } else {
                intentResultado.putExtra("classificacao_proximidade", "proximo");
            }

            // Define o resultado como OK e anexa a Intent com as classificações
            setResult(RESULT_OK, intentResultado);

            // Fecha a activity e retorna para o App A
            finish();
        });
    }
}