package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // Coordenadas

    private final LatLng cidadeNatal = new LatLng(-16.730848476134504, -43.859748612317546);
    private final LatLng vicosa = new LatLng(-20.752922767603394, -42.88204798158467);
    private final LatLng dpiUfv = new LatLng(-20.76479700617575, -42.86846080432306);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Inicializa o mapa
        try{
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        } catch (Exception e) {
            Toast.makeText(this, "ERRO!", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }


        // Configura os botões
        Button btnCidadeNatal = findViewById(R.id.btnCidadeNatal);
        Button btnVicosa = findViewById(R.id.btnVicosa);
        Button btnDpiUfv = findViewById(R.id.btnDpiUfv);

        btnCidadeNatal.setOnClickListener(v -> moverCamera(cidadeNatal, "Minha cidade natal"));
        btnVicosa.setOnClickListener(v -> moverCamera(vicosa, "Minha casa em Viçosa"));
        btnDpiUfv.setOnClickListener(v -> moverCamera(dpiUfv, "DPI/UFV"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Recebe o parâmetro da MainActivity
        Intent intent = getIntent();
        int local = intent.getIntExtra("local", 0);

        // Move a câmera para o local selecionado
        switch (local) {
            case 0:
                moverCamera(cidadeNatal, "Minha cidade natal");
                break;
            case 1:
                moverCamera(vicosa, "Minha casa em Viçosa");
                break;
            case 2:
                moverCamera(dpiUfv, "DPI/UFV");
                break;
        }
    }

    private void moverCamera(LatLng localizacao, String titulo) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(localizacao).title(titulo));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacao, 15));

        Toast.makeText(this, titulo, Toast.LENGTH_SHORT).show();
    }
}
