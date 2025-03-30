package com.example.pratica1b;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pratica1b.R;

public class MainActivity extends AppCompatActivity {

    private EditText inputValor1, inputValor2;
    private TextView resultLabel;
    private Button btnSomar, btnSubtrair, btnMultiplicar, btnDividir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar os componentes
        inputValor1 = findViewById(R.id.input_valor1);
        inputValor2 = findViewById(R.id.input_valor2);
        resultLabel = findViewById(R.id.result_label);

        btnSomar = findViewById(R.id.btn_somar);
        btnSubtrair = findViewById(R.id.btn_subtrair);
        btnMultiplicar = findViewById(R.id.btn_multiplicar);
        btnDividir = findViewById(R.id.btn_dividir);

        // Adicionar listeners de clique para os botões
        btnSomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacao("+");
            }
        });

        btnSubtrair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacao("-");
            }
        });

        btnMultiplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacao("*");
            }
        });

        btnDividir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarOperacao("/");
            }
        });
    }

    // Função para realizar a operação com base no operador
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
            resultLabel.setText("O Resultado é: " + resultado);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, insira valores válidos", Toast.LENGTH_SHORT).show();
        }
    }
}
