package com.example.wproducto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Product_register extends AppCompatActivity {



    EditText etDescripcion, etPrecio, etFechaV;
    Spinner spinner_lista, spinner_lote;
    RadioButton rbCaja, rbPaquete, rbBotella, rbLibre;
    Button btFechaV, btRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_register);
        //Carga los spinner
        spinners();

        loadUI();

        btFechaV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCalendar();
            }
        });



    }

    private void spinners (){
        // SPINNER - TIPO
        Spinner spinner = (Spinner) findViewById(R.id.spinner_lista);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_lista, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // SPINNER - LOTE
        Spinner spinner_lote = (Spinner) findViewById(R.id.spinner_lote);
        ArrayAdapter<CharSequence> adapter_lote = ArrayAdapter.createFromResource(this,
                R.array.array_lote, android.R.layout.simple_spinner_item);
        adapter_lote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_lote.setAdapter(adapter_lote);

    }

    private  void loadUI(){
        spinner_lista = findViewById(R.id.spinner_lista);
        spinner_lote = findViewById(R.id.spinner_lote);
        rbCaja = findViewById(R.id.rbCaja);
        rbPaquete = findViewById(R.id.rbPaquete);
        rbBotella = findViewById(R.id.rbBotella);
        rbLibre = findViewById(R.id.rbLibre);
        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecio = findViewById(R.id.etPrecio);
        etFechaV = findViewById(R.id.etFechaV);
        etFechaV.setEnabled(false);
        btFechaV = findViewById(R.id.btTime);
        btRegistrar = findViewById(R.id.btRegistrar);
    }

    private void abrirCalendar(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        calendar.set(selectedYear, selectedMonth, selectedDay);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String selectedDate = sdf.format(calendar.getTime());
                        etFechaV.setText(selectedDate);
                    }
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }


}