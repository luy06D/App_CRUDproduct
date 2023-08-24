package com.example.wproducto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Product_register extends AppCompatActivity {



    EditText etDescripcion, etPrecio, etFechaV;
    Spinner spinner_lista, spinner_lote;
    RadioButton rbCaja, rbPaquete, rbBotella, rbLibre;
    RadioGroup radioGroup;
    Button btRegistrar;

    String radioG, tipo, lote , descripcion , precio, fechaV;

    final String URL = "http://192.168.1.101/wproducto/controllers/producto.controller.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_register);
        //Carga los spinner
        spinners();

        loadUI();

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateBoxes();
               // addRadioButton();

            }
        });

    }


    private void  validateBoxes(){

        tipo = spinner_lista.toString().trim();
        lote = spinner_lote.toString().trim();
        radioG = radioGroup.toString().trim();
        descripcion = etDescripcion.getText().toString().trim();
        precio = etPrecio.getText().toString().trim();
        fechaV = etFechaV.getText().toString().trim();


        if(descripcion.isEmpty()){
            etDescripcion.setError("Complete el campo");
        } else if (precio.isEmpty()) {
            etPrecio.setError("Complete los campos");
        } else if (fechaV.isEmpty()) {
            etFechaV.setError("Complete el campo");
        }else{
            showDialogRegister();
        }


    }

    private  void showDialogRegister(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Productos");
        dialog.setMessage("¿Está seguro de registrar?");
        dialog.setCancelable(false);

        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registerProduct();
            }
        });

        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.show();
    }

    private void registerProduct(){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Error", error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("operacion", "register");
                parametros.put("tipo", tipo );
                parametros.put("descripcion", descripcion);
                parametros.put("precio", precio);
                parametros.put("fechavencimiento", fechaV);
                parametros.put("presentacion",radioG.toString() );
                parametros.put("lote", lote );
                return parametros;
            }



        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void addRadioButton() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioId);
        int indiceB = radioGroup.indexOfChild(radioButton);



        Toast.makeText(this, "El indice: "+ indiceB , Toast.LENGTH_SHORT).show();

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

        radioGroup = findViewById(R.id.radiogrupo);

        spinner_lista = findViewById(R.id.spinner_lista);
        spinner_lote = findViewById(R.id.spinner_lote);
        rbCaja = findViewById(R.id.rbCaja);
        rbPaquete = findViewById(R.id.rbPaquete);
        rbBotella = findViewById(R.id.rbBotella);
        rbLibre = findViewById(R.id.rbLibre);
        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecio = findViewById(R.id.etPrecio);
        etFechaV = findViewById(R.id.etFechaV);
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