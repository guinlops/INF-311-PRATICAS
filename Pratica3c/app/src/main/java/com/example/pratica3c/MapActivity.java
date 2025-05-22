package com.example.pratica3c;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Marker markerAtual;
    private DBHelper dbHelper;

    LatLng casaNatal, casaVicosa, departamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        try{
            dbHelper = new DBHelper(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // Inicializa o mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Carrega as localizações do banco
        casaNatal = buscarLocalizacaoPorNome("Cidade Natal");
        casaVicosa = buscarLocalizacaoPorNome("Viçosa");
        departamento = buscarLocalizacaoPorNome("DPI");

        // Configura botões
        Button btnCidadeNatal = findViewById(R.id.btnCidadeNatal);
        Button btnVicosa = findViewById(R.id.btnVicosa);
        Button btnDpiUfv = findViewById(R.id.btnDpiUfv);
        Button btnLocalizacao = findViewById(R.id.btnLocalizacao);

        btnCidadeNatal.setOnClickListener(v -> moverCamera(casaNatal, "Minha cidade natal"));
        btnVicosa.setOnClickListener(v -> moverCamera(casaVicosa, "Minha casa em Viçosa"));
        btnDpiUfv.setOnClickListener(v -> moverCamera(departamento, "DPI/UFV"));
        btnLocalizacao.setOnClickListener(v -> localizarUsuario());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        Intent intent = getIntent();
        int local = intent.getIntExtra("local", 0);

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
        if (localizacao == null) {
            Toast.makeText(this, "Localização não encontrada!", Toast.LENGTH_SHORT).show();
            return;
        }

        map.clear();
        map.addMarker(new MarkerOptions().position(localizacao).title(titulo));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacao, 15));
        Toast.makeText(this, titulo, Toast.LENGTH_SHORT).show();
    }

    private void localizarUsuario() {
        LatLng centro = map.getCameraPosition().target;

        if (markerAtual != null) markerAtual.remove();

        markerAtual = map.addMarker(new MarkerOptions()
                .position(centro)
                .title("Minha Localização Atual")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(centro, 17));

        float[] resultado = new float[1];
        Location.distanceBetween(centro.latitude, centro.longitude,
                casaVicosa.latitude, casaVicosa.longitude, resultado);

        Toast.makeText(this, "Distância até minha casa em Viçosa: "
                + (int) resultado[0] + " metros", Toast.LENGTH_LONG).show();
    }

    private LatLng buscarLocalizacaoPorNome(String nome) {
        DBHelper.Localizacao loc = dbHelper.buscarLocalizacaoPorNome(nome);
        if (loc != null) {
            return new LatLng(loc.latitude, loc.longitude);
        } else {
            return null;
        }
    }
}
