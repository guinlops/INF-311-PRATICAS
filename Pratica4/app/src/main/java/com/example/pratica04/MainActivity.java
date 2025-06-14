package com.example.pratica04;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final int REQUEST_CODE_CLASSIFICACAO = 1;
    public static final String ACTION_CLASSIFICAR_SENSORES = "com.example.CLASSIFICAR_SENSORES";

    private SensorManager sensorManager;
    private Sensor sensorProximidade;
    private Sensor sensorLuminosidade;

    private float valorProximidade;
    private float valorLuminosidade;

    private LanternaHelper lanternaHelper;
    private MotorHelper motorHelper;

    private Switch switchLanterna;
    private Switch switchVibracao;
    private Button btnClassificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa helpers
        lanternaHelper = new LanternaHelper(this);
        motorHelper = new MotorHelper(this);

        // Referências da UI
        switchLanterna = findViewById(R.id.switchLanterna);
        switchVibracao = findViewById(R.id.switchVibracao);
        btnClassificar = findViewById(R.id.btnClassificar);

        // Configuração dos sensores
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorProximidade = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorLuminosidade = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (sensorProximidade == null || sensorLuminosidade == null) {
            Toast.makeText(this, "Sensores de proximidade ou luminosidade não disponíveis.", Toast.LENGTH_LONG).show();
            finish();
        }

        // Ação do botão
        btnClassificar.setOnClickListener(view -> {
            Intent intent = new Intent(ACTION_CLASSIFICAR_SENSORES);
            intent.putExtra("valor_luminosidade", valorLuminosidade);
            intent.putExtra("valor_proximidade", valorProximidade);

            // Verifica se há um app para responder a Intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_CODE_CLASSIFICACAO);
            } else {
                Toast.makeText(this, "Aplicativo de classificação não encontrado.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CLASSIFICACAO && resultCode == RESULT_OK && data != null) {
            String classificacaoLuz = data.getStringExtra("classificacao_luz");
            String classificacaoProximidade = data.getStringExtra("classificacao_proximidade");

            // Trata classificação da luz
            if ("baixa".equals(classificacaoLuz)) {
                lanternaHelper.ligar();
                switchLanterna.setChecked(true);
            } else { // "alta"
                lanternaHelper.desligar();
                switchLanterna.setChecked(false);
            }

            // Trata classificação da proximidade
            if ("distante".equals(classificacaoProximidade)) {
                motorHelper.iniciarVibracao();
                switchVibracao.setChecked(true);
            } else { // "proximo"
                motorHelper.pararVibracao();
                switchVibracao.setChecked(false);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            valorLuminosidade = event.values[0];
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            valorProximidade = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Não necessário para esta atividade
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registra os listeners dos sensores
        sensorManager.registerListener(this, sensorLuminosidade, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorProximidade, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistra os listeners para economizar bateria
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Garante que a lanterna e o vibrador sejam desligados ao fechar o app
        lanternaHelper.desligar();
        motorHelper.pararVibracao();
    }
}
