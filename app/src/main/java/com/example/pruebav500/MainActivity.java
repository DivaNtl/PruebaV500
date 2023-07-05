package com.example.pruebav500;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int indiceActual;
    private TextInputLayout etNumero, etTitular, etExpira;
    private RadioGroup rdTipo;
    private Button btnBuscar, btnGrabar, btnEliminar, btnSiguiente, btnAtras;
    private ArrayList<Tarjeta> tarjetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        referencias ( );
        eventos ( );

        tarjetas = new ArrayList<> ( );
        indiceActual = -1;
    }
        private void referencias() {
                etNumero = findViewById(R.id.etNumero);
                etTitular = findViewById(R.id.etTitular);
                etExpira = findViewById(R.id.etExpira);
                rdTipo = findViewById(R.id.rdTipo);
                btnBuscar = findViewById(R.id.btnBuscar);
                btnGrabar = findViewById(R.id.btnGrabar);
                btnEliminar = findViewById(R.id.btnEliminar);
                btnSiguiente = findViewById(R.id.btnSiguiente);
                btnAtras = findViewById(R.id.btnAtras);

    }

          private void  eventos() {
                btnBuscar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buscarTarjeta();
                    }
                });

                btnGrabar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        grabarTarjeta();
                    }
                });

                btnEliminar.setOnClickListener(new View.OnClickListener() {
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
            }
            private void buscarTarjeta() {
                clearScreen();
                String numeroTarjeta = etNumero.getEditText().toString();
                if (numeroTarjeta.isEmpty()) {
                    etNumero.setError("Ingrese un número de tarjeta");
                    return;
                }
                for (int i = 0; i < tarjetas.size(); i++) {
                    Tarjeta tarjeta = tarjetas.get(i);
                    if (tarjeta.getNumero().equals(numeroTarjeta)){
                        indiceActual = i;
                        displayData(tarjeta);
                        Toast.makeText( com.example.pruebav500.MainActivity.this, "Tarjeta encontrada", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                clearScreen ();
                Toast.makeText( com.example.pruebav500.MainActivity.this, "Tarjeta no encontrada", Toast.LENGTH_SHORT).show();
            }

            private void grabarTarjeta() {
                Log.d("TarjetaActivity", "grabarTarjeta se ha llamado");
                String numeroTarjetaStr = etNumero.getEditText().getText().toString();
                String titular = etTitular.getEditText().getText().toString();
                String expiraStr = etExpira.getEditText().getText().toString();

                boolean error = false;
                if (numeroTarjetaStr.isEmpty()) {
                    etNumero.setError("Ingrese un número de tarjeta");
                    error = true;
                }
                if (titular.isEmpty()) {
                    etTitular.setError("Ingrese el nombre del titular");
                    error = true;
                }
                if (expiraStr.isEmpty()) {
                    etExpira.setError("Ingrese la fecha de expiración");
                    error = true;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
                sdf.setLenient(false);
                Date expiryDate;
                try {
                    expiryDate = sdf.parse(expiraStr);
                } catch (ParseException e) {
                    etExpira.setError("Fecha de expiración inválida");
                    return;
                }

                // Verifica que la fecha de expiración no sea inferior a la fecha actual ni más de 6 años mayor
                Calendar current = Calendar.getInstance();
                Calendar expiry = Calendar.getInstance();
                expiry.setTime(expiryDate);
                if (expiry.before(current)) {
                    etExpira.setError("Fecha de expiración no puede ser anterior a la fecha actual");
                    error = true;
                }
                current.add(Calendar.YEAR, 6);
                if (expiry.after(current)) {
                    etExpira.setError("Fecha de expiración no puede ser más de 6 años mayor a la fecha actual");
                    error = true;
                }

                if (error) {
                    return;
                }
                // Convierte los valores a enteros
                int numeroTarjeta = Integer.parseInt(numeroTarjetaStr);
                

                // Obtiene el tipo de tarjeta seleccionado
                String tipo = "";
                if (rdTipo.getCheckedRadioButtonId() == R.id.rdGold) {
                    tipo = "Gold";
                } else if (rdTipo.getCheckedRadioButtonId() == R.id.rdPlatinum) {
                    tipo = "Platinum";
                } else if (rdTipo.getCheckedRadioButtonId() == R.id.rdSignature) {
                    tipo = "Signature";

                }

                // Crea una nueva tarjeta con los datos ingresados
                Tarjeta nuevaTarjeta = new Tarjeta(numeroTarjeta, titular, expiryDate, tipo);

                // Busca si ya existe una tarjeta con el mismo número
                for (int i = 0; i < tarjetas.size(); i++) {
                    if (tarjetas.get(i).getNumero() == numeroTarjeta) {
                        // Si la tarjeta ya existe, la reemplaza con la nueva tarjeta y sale del método
                        tarjetas.set(i, nuevaTarjeta);
                        Toast.makeText( MainActivity.this, "Tarjeta actualizada exitosamente", Toast.LENGTH_SHORT).show();
                        clearScreen();
                        return;
                    }
                }

                // Si la tarjeta no existe, la agrega al ArrayList
                tarjetas.add(nuevaTarjeta);
                Toast.makeText( MainActivity.this, "Tarjeta grabada exitosamente", Toast.LENGTH_SHORT).show();
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
                SimpleDateFormat format = new SimpleDateFormat("MM/yy", Locale.getDefault());
                String fechaFormateada = format.format(tarjeta.getExpira());
                etExpira.getEditText().setText(fechaFormateada);
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

            private void eliminarTarjeta() {
                String numeroTarjetaStr = etNumero.getEditText().getText().toString();

                if (numeroTarjetaStr.isEmpty()) {
                    etNumero.setError("Ingrese un número de tarjeta");
                    return;
                }

                int numeroTarjeta = Integer.parseInt(numeroTarjetaStr);

                // Buscar la tarjeta en el ArrayList y eliminarla si existe
                for (int i = 0; i < tarjetas.size(); i++) {
                    Tarjeta tarjeta = tarjetas.get(i);
                    if (tarjeta.getNumero() == numeroTarjeta) {
                        tarjetas.remove(i);
                        Toast.makeText( com.example.pruebav500.MainActivity.this, "Tarjeta eliminada exitosamente", Toast.LENGTH_SHORT).show();
                        clearScreen();
                        return;
                    }
                }

                Toast.makeText( com.example.pruebav500.MainActivity.this, "Tarjeta no encontrada", Toast.LENGTH_SHORT).show();
            }

            private void siguienteTarjeta() {
                if (indiceActual < tarjetas.size() - 1) {
                    indiceActual++;
                    Tarjeta siguienteTarjeta = tarjetas.get(indiceActual);
                    displayData(siguienteTarjeta);
                } else {
                    Toast.makeText( com.example.pruebav500.MainActivity.this, "No hay más tarjetas disponibles", Toast.LENGTH_SHORT).show();
                }
            }
            private void anteriorTarjeta() {
                if (indiceActual > 0) {
                    indiceActual--;
                    Tarjeta anteriorTarjeta = tarjetas.get(indiceActual);
                    displayData(anteriorTarjeta);
                } else {
                    Toast.makeText( com.example.pruebav500.MainActivity.this, "No hay tarjetas anteriores", Toast.LENGTH_SHORT).show();
                }
            }

        }