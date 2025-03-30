package com.example.pratica1;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputValor1, inputValor2;
    private TextView resultLabel;
    private Button btnSomar, btnSubtrair, btnMultiplicar, btnDividir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        inputValor1 = findViewById(R.id.input_valor1);
        inputValor2 = findViewById(R.id.input_valor2);
        resultLabel = findViewById(R.id.result_label);
//        resultLabel.setText(getString(R.string.result_text, 0.00));  // Valor inicial como 0.00
        resultLabel.setText("");
        btnSomar = findViewById(R.id.btn_somar);
        btnSubtrair = findViewById(R.id.btn_subtrair);
        btnMultiplicar = findViewById(R.id.btn_multiplicar);
        btnDividir = findViewById(R.id.btn_dividir);

        // Adicionar listeners de clique para os botões
        btnSomar.setOnClickListener(v -> realizarOperacao("+"));

        btnSubtrair.setOnClickListener(v -> realizarOperacao("-"));

        btnMultiplicar.setOnClickListener(v -> realizarOperacao("*"));

        btnDividir.setOnClickListener(v -> realizarOperacao("/"));
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }


    private void realizarOperacao(String operador) {
        try {
            // Obter os valores dos campos de entrada
            double valor1 = Double.parseDouble(inputValor1.getText().toString());
            double valor2 = Double.parseDouble(inputValor2.getText().toString());
            double resultado = 0;

            switch (operador) {
                case "+":
                    resultado = valor1 + valor2;
                    break;
                case "-":
                    resultado = valor1 - valor2;
                    break;
                case "*":
                    resultado = valor1 * valor2;
                    break;
                case "/":
                    // Verificar divisão por zero
                    if (valor2 == 0) {
                        Toast.makeText(this, "Não é possível dividir por zero", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    resultado = valor1 / valor2;
                    break;
            }

            // Exibir o resultado na TextView

            resultLabel.setText(getString(R.string.result_text,  resultado));


        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, insira valores válidos", Toast.LENGTH_SHORT).show();
        }
    }
}