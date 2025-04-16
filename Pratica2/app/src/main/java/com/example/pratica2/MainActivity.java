package com.example.pratica2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome, editTextIdade, editTextPeso, editTextAltura;
    private Button buttonRelatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CICLO", "MainActivity - onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        editTextNome = findViewById(R.id.editTextNome);
        editTextIdade = findViewById(R.id.editTextIdade);
        editTextPeso = findViewById(R.id.editTextPeso);
        editTextAltura = findViewById(R.id.editTextAltura);
        buttonRelatorio = findViewById(R.id.buttonRelatorio);

        // Ao clicar no botão
        buttonRelatorio.setOnClickListener(v -> {
            String nome = editTextNome.getText().toString();
            String idade = editTextIdade.getText().toString();
            String peso = editTextPeso.getText().toString();
            String altura = editTextAltura.getText().toString();

            Intent intent = new Intent(MainActivity.this, RelatorioActivity.class);

            intent.putExtra("nome", nome);
            intent.putExtra("idade", idade);
            intent.putExtra("peso", peso);
            intent.putExtra("altura", altura);
            startActivity(intent);
            finish(); // Encerra a MainActivity
            String mensagem = "Nome: " + nome + "\nIdade: " + idade + "\nPeso: " + peso + "\nAltura: " + altura;
            Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();

        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CICLO", "MainActivity - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CICLO", "MainActivity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CICLO", "MainActivity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CICLO", "MainActivity - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CICLO", "MainActivity - onDestroy");
    }

}
