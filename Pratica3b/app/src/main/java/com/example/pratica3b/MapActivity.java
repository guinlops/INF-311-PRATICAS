package com.example.pratica3b;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Marker markerAtual;
    private FusedLocationProviderClient fusedLocationClient;

    // Coordenadas

//    private final LatLng cidadeNatal = new LatLng(-16.730848476134504, -43.859748612317546);
//    private final LatLng vicosa = new LatLng(-20.752922767603394, -42.88204798158467);
//    private final LatLng dpiUfv = new LatLng(-20.76479700617575, -42.86846080432306);



    LatLng casaNatal = new LatLng(-16.730848476134504, -43.859748612317546);
    LatLng casaVicosa = new LatLng(-20.752922767603394, -42.88204798158467);
    LatLng departamento = new LatLng(-20.76479700617575, -42.86846080432306);

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
        Button btnLocalizacao = findViewById(R.id.btnLocalizacao);
        btnLocalizacao.setOnClickListener(v -> localizarUsuario());
        fusedLocationClient = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(this);
        btnCidadeNatal.setOnClickListener(v -> moverCamera(casaNatal, "Minha cidade natal"));
        btnVicosa.setOnClickListener(v -> moverCamera(casaVicosa, "Minha casa em Viçosa"));
        btnDpiUfv.setOnClickListener(v -> moverCamera(departamento, "DPI/UFV"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Recebe o parâmetro da MainActivity
        Intent intent = getIntent();
        int local = intent.getIntExtra("local", 0);

        // Move a câmera para o local selecionado
        switch (local) {
            case 0:
                moverCamera(casaNatal, "Minha cidade natal");
                break;
            case 1:
                moverCamera(casaVicosa, "Minha casa em Viçosa");
                break;
            case 2:
                moverCamera(departamento, "DPI/UFV");
                break;
        }
    }

    private void moverCamera(LatLng localizacao, String titulo) {
        map.clear();
        map.addMarker(new MarkerOptions().position(localizacao).title(titulo));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacao, 15));

        Toast.makeText(this, titulo, Toast.LENGTH_SHORT).show();
    }


    private void localizarUsuario() {
        // Obtém a posição central da câmera
        LatLng centro = map.getCameraPosition().target;

        // Remove marcador anterior, se existir
        if (markerAtual != null) markerAtual.remove();

        // Adiciona novo marcador azul
        markerAtual = map.addMarker(new MarkerOptions()
                .position(centro)
                .title("Minha Localização Atual")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(centro, 17));

        // Calcula a distância até Viçosa
        float[] resultado = new float[1];
        Location.distanceBetween(centro.latitude, centro.longitude,
                casaVicosa.latitude, casaVicosa.longitude, resultado);

        Toast.makeText(this, "Distância até minha casa em Viçosa: "
                + (int) resultado[0] + " metros", Toast.LENGTH_LONG).show();
    }



}
