package com.example.pruebav500;

import static com.example.pruebav500.R.id.etNumero;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class TarjetaActivity extends AppCompatActivity {
    private ArrayList<Tarjeta> tarjetas;
    private int indiceActual;
    private TextInputLayout etNumero;
    private TextInputLayout etTitular;
    private TextInputLayout etExpira;
    private RadioGroup rdTipo;
    private Button btnBuscar;
    private Button btnGrabar;
    private Button btnEliminar;
    private Button btnSiguiente;
    private Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tarjetas = new ArrayList<>();
        indiceActual = -1;

        // Inicializar vistas
        etNumero = findViewById(R.id.etNumero);
        etTitular = findViewById(R.id.etTitular);
        etExpira = findViewById(R.id.etExpira);
        rdTipo = findViewById(R.id.rdTipo);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAtras = findViewById(R.id.btnAtras);

        // Asignar Listeners
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarTarjeta();
            }
        });

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grabarTarjeta();
            }
        });

       /* btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarTarjeta();
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                siguienteTarjeta();
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anteriorTarjeta();
            }
        });

        etExpira.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });*/
    }



    // Métodos para buscar, agregar, modificar y eliminar tarjetas, y navegar a través de las tarjetas en la lista.
    private void buscarTarjeta() {
        clearScreen();
        String numeroTarjeta = etNumero.getEditText().toString();
        if (numeroTarjeta.isEmpty()) {
            Toast.makeText(TarjetaActivity.this, "Ingrese un número de tarjeta", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < tarjetas.size(); i++) {
            Tarjeta tarjeta = tarjetas.get(i);
            if (tarjeta.getNumero().equals(numeroTarjeta)){
                indiceActual = i;
                displayData(tarjeta);
                Toast.makeText(TarjetaActivity.this, "Tarjeta encontrada", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(TarjetaActivity.this, "Tarjeta no encontrada", Toast.LENGTH_SHORT).show();
    }

    private void grabarTarjeta() {
        // Obtén los valores ingresados por el usuario
        String numeroTarjetaStr = etNumero.getEditText().getText().toString();
        String titular = etTitular.getEditText().getText().toString();
        String expiraStr = etExpira.getEditText().getText().toString();

        // Valida los datos ingresados
        if (numeroTarjetaStr.isEmpty() || titular.isEmpty() || expiraStr.isEmpty() || rdTipo.getCheckedRadioButtonId() == -1) {
            Toast.makeText(TarjetaActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convierte los valores a enteros
        int numeroTarjeta = Integer.parseInt(numeroTarjetaStr);
        int expira = Integer.parseInt(expiraStr);

        // Asumiendo que tipo es una cadena que puede ser "Gold", "Platinum", o "Signature"
        String tipo = "";
        if (rdTipo.getCheckedRadioButtonId() == R.id.rdGold) {
            tipo = "Gold";
        } else if (rdTipo.getCheckedRadioButtonId() == R.id.rdPlatinum) {
            tipo = "Platinum";
        } else if (rdTipo.getCheckedRadioButtonId() == R.id.rdSignature) {
            tipo = "Signature";

        }

        // Crea una nueva tarjeta con los datos ingresados
        Tarjeta nuevaTarjeta = new Tarjeta(numeroTarjeta, titular, expira, tipo);

        // Busca si ya existe una tarjeta con el mismo número
        for (int i = 0; i < tarjetas.size(); i++) {
            if (tarjetas.get(i).getNumero() == numeroTarjeta) {
                // Si la tarjeta ya existe, la reemplaza con la nueva tarjeta y sale del método
                tarjetas.set(i, nuevaTarjeta);
                Toast.makeText(TarjetaActivity.this, "Tarjeta actualizada exitosamente", Toast.LENGTH_SHORT).show();
                clearScreen();
                return;
            }
        }

        // Si la tarjeta no existe, la agrega al ArrayList
        tarjetas.add(nuevaTarjeta);
        Toast.makeText(TarjetaActivity.this, "Tarjeta grabada exitosamente", Toast.LENGTH_SHORT).show();
        clearScreen();
    }

    private void clearScreen() {
        // Limpia los campos de entrada y los botones de radio
        etNumero.getEditText().setText("");
        etTitular.getEditText().setText("");
        etExpira.getEditText().setText("");
        rdTipo.clearCheck();
    }

    private void displayData(Tarjeta tarjeta) {
        // Muestra los datos de la tarjeta en los campos de entrada y los botones de radio
        etNumero.getEditText().setText(String.valueOf(tarjeta.getNumero()));
        etTitular.getEditText().setText(tarjeta.getTitular());
        etExpira.getEditText().setText(String.valueOf(tarjeta.getExpira()));
        // Asumiendo que tipo es una cadena que puede ser "Gold", "Platinum", o "Signature"
        switch (tarjeta.getTipo()) {
            case "Gold":
                rdTipo.check(R.id.rdGold);
                break;
            case "Platinum":
                rdTipo.check(R.id.rdPlatinum);
                break;
            case "Signature":
                rdTipo.check(R.id.rdSignature);
                break;
        }
    }

    /* private void eliminarTarjeta() {
        // Deberías implementar este método basado en tus necesidades
    }

    private void siguienteTarjeta() {
        // Deberías implementar este método basado en tus necesidades
    }

    private void anteriorTarjeta() {
        // Deberías implementar este método basado en tus necesidades
    }

    private void showDatePickerDialog() {
        // Aquí podrías mostrar un DatePickerDialog para permitir al usuario seleccion
    }*/
}

