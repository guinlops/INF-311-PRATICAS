package com.example.pratica2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RelatorioActivity extends AppCompatActivity {

    private TextView textViewNome, textViewIdade, textViewPeso, textViewAltura, textViewIMC, textViewClassificacao;
    private Button buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_relatorio);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scrollViewRelatorio), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        textViewNome = findViewById(R.id.textViewNome);
        textViewIdade = findViewById(R.id.textViewIdade);
        textViewPeso = findViewById(R.id.textViewPeso);
        textViewAltura = findViewById(R.id.textViewAltura);
        textViewIMC = findViewById(R.id.textViewIMC);
        textViewClassificacao = findViewById(R.id.textViewClassificacao);
        buttonVoltar = findViewById(R.id.buttonVoltar);

        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        String idade = intent.getStringExtra("idade");
        String pesoStr = intent.getStringExtra("peso");
        String alturaStr = intent.getStringExtra("altura");

        // Define valores padrão se nulos ou vazios
        if (nome == null || nome.trim().isEmpty()) nome = "Nome não informado";
        if (idade == null || idade.trim().isEmpty()) idade = "Não informada";
        if (pesoStr == null || pesoStr.trim().isEmpty()) pesoStr = "0.0";
        if (alturaStr == null || alturaStr.trim().isEmpty()) alturaStr = "1.0";

        float peso = Float.parseFloat(pesoStr);
        float altura = Float.parseFloat(alturaStr);
        float imc = peso / (altura * altura);


        String classificacao;
        if (imc < 18.5) {
            classificacao = "Abaixo do Peso";
        } else if (imc < 25) {
            classificacao = "Saudável";
        } else if (imc < 30) {
            classificacao = "Sobrepeso";
        } else if (imc < 35) {
            classificacao = "Obesidade Grau I";
        } else if (imc < 40) {
            classificacao = "Obesidade Grau II (severa)";
        } else {
            classificacao = "Obesidade Grau III (mórbida)";
        }

        textViewNome.setText("Nome: " + nome);
        textViewIdade.setText("Idade: " + idade);
        textViewPeso.setText("Peso: " + pesoStr + " kg");
        textViewAltura.setText("Altura: " + alturaStr + " m");
        textViewIMC.setText(String.format("IMC: %.2f", imc));
        textViewClassificacao.setText("Classificação: " + classificacao);

        buttonVoltar.setOnClickListener(v -> {
            Intent it = new Intent(RelatorioActivity.this, MainActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(it);
            finish(); // Encerra a RelatorioActivity
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CICLO", "RelatorioActivity - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CICLO", "RelatorioActivity  - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CICLO", "RelatorioActivity  - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CICLO", "RelatorioActivity - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CICLO", "RelatorioActivity  - onDestroy");
    }
}
