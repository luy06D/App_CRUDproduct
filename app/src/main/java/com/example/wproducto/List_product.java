package com.example.wproducto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class List_product extends AppCompatActivity {


    ListView lvProductos;

    private List<String> dataList = new ArrayList<>();

    private List<Integer> dataID = new ArrayList<>();

    final String URL = "http://192.168.1.101/wproducto/controllers/producto.controller.php";

    private CustomAdapter adapter;

    private String[] opciones = {"Editar","Eliminar","Cancelar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        loadUI();

        getData();

        lvProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                showOptions(dataID.get(posicion), posicion);
            }
        });
    }


    private void showOptions(int pk , int posicionItem){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(dataList.get(posicionItem));
        dialogo.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {

                switch (item){
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), product_update.class);
                        intent.putExtra("idproducto", pk);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        dialogInterface.dismiss();
                        break;
                }

            }
        });

        dialogo.show();
    }


    private void getData(){
        dataID.clear();
        dataList.clear();
        adapter = new CustomAdapter(this, dataList);
        lvProductos.setAdapter(adapter);

        //Nueva URl para mostrar la lista
        Uri.Builder URLFull = Uri.parse(URL).buildUpon();
        URLFull.appendQueryParameter("operacion", "list_product");
        String URLUpdate = URLFull.build().toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLUpdate, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = new JSONObject(response.getString(i));
                        dataList.add(jsonObject.getString("tipo") + " - " +
                                    jsonObject.getString("descripcion"));
                        dataID.add(jsonObject.getInt("idproducto"));
                    }
                    adapter.notifyDataSetChanged();

                }catch (Exception err){
                    err.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });

        Volley.newRequestQueue(this).add(jsonArrayRequest);

    }
    private void loadUI(){
        lvProductos = findViewById(R.id.lvProductos);
    }
}