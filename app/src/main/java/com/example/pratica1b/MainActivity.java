package com.example.pratica1b;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView visor;
    private String operador = "";
    private double valorAnterior = 0;
    private boolean novoNumero = true;
    private boolean erro = false;
    private Button btnBspc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        visor = findViewById(R.id.input_text);
        visor.setInputType(0); // Impede entrada manual
        visor.setKeyListener(null);

        configurarBotoes();
    }

    private void configurarBotoes() {
        int[] botoesNumericos = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};

        btnBspc = findViewById(R.id.btn_bspc);
        btnBspc.setText("<<");


        for (int id : botoesNumericos) {
            findViewById(id).setOnClickListener(this::onNumeroClicado);
        }


        int[] botoesOperadores = {R.id.btn_plus, R.id.btn_minus, R.id.btn_times, R.id.btn_divided};
        for (int id : botoesOperadores) {
            findViewById(id).setOnClickListener(this::onOperadorClicado);
        }

        // Configura outros botões
        findViewById(R.id.btn_equal).setOnClickListener(v -> calcularResultado());
        findViewById(R.id.btn_c).setOnClickListener(v -> limparVisor());
        findViewById(R.id.btn_bspc).setOnClickListener(v -> removerUltimoDigito());
        findViewById(R.id.btn_dot).setOnClickListener(v -> adicionarPonto());
    }

    private void onNumeroClicado(View view) {
        if (erro) {
            limparVisor();
        }

        Button botao = (Button) view;
        String textoBotao = botao.getText().toString();

        if (novoNumero && operador.isEmpty()) {
            visor.setText(textoBotao); // Substitui apenas se não houver operador
        } else {
            visor.append(textoBotao); // Continua a operação no visor
        }

        novoNumero = false;
    }

    private void onOperadorClicado(View view) {
        if (erro) {
            limparVisor();
        }

        Button botao = (Button) view;
        String novoOperador = botao.getText().toString();

        if (!operador.isEmpty()) {
            return; // Evita adicionar múltiplos operadores seguidos
        }

        operador = novoOperador;
        visor.append(" " + operador + " "); // Exibe operação no visor
        novoNumero = true;
    }



    private void calcularResultado() {
        if (erro || operador.isEmpty()) {
            return;
        }

        String expressao = visor.getText().toString().trim(); // Obtém a expressão completa
        String[] partes = expressao.split(" "); // Divide pelo espaço ("5 + 3")

        if (partes.length < 3) {
            return;
        }

        try {
            double valor1 = Double.parseDouble(partes[0]); // Primeiro número
            double valor2 = Double.parseDouble(partes[2]); // Segundo número
            String operadorAtual = partes[1];

            double resultado = 0;
            switch (operadorAtual) {
                case "+":
                    resultado = valor1 + valor2;
                    break;
                case "-":
                    resultado = valor1 - valor2;
                    break;
                case "x":
                    resultado = valor1 * valor2;
                    break;
                case "/":
                    if (valor2 == 0) {
                        visor.setText("ERROR");
                        erro = true;
                        return;
                    }
                    resultado = valor1 / valor2;
                    break;
            }

            visor.setText(String.valueOf(resultado)); // Exibe o resultado
            operador = "";
            novoNumero = true;
        } catch (NumberFormatException e) {
            erro = true;
            visor.setText("ERROR");
        }
    }


    private void limparVisor() {
        visor.setText("0");
        operador = "";
        valorAnterior = 0;
        novoNumero = true;
        erro = false;
    }



    private void removerUltimoDigito() {
        if (erro || novoNumero) {
            return;
        }

        String texto = visor.getText().toString().trim();

        if (texto.isEmpty() || texto.equals("0")) {
            return;
        }

        String[] partes = texto.split(" ");

        if (texto.endsWith(" ")) {
            texto = texto.substring(0, texto.length() - 3); // Remove " X " (operador com espaços)
        } else if (partes.length == 2) {

            texto = partes[0];
        } else {
            texto = texto.substring(0, texto.length() - 1);
        }

        if (texto.isEmpty()) {
            texto = "0";
            novoNumero = true;
        }

        visor.setText(texto);
    }

    private void adicionarPonto() {
        if (erro || novoNumero) {
            visor.setText("0.");
            novoNumero = false;
            return;
        }

        String texto = visor.getText().toString();
        String[] partes = texto.split(" ");

        if (partes.length == 1) {

            if (!partes[0].contains(".")) {
                visor.append(".");
            }
        } else if (partes.length == 3) {
            if (!partes[2].contains(".")) {
                visor.append(".");
            }
        }
    }

}
