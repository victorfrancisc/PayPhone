package com.example.payphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class MainActivity2 extends AppCompatActivity {

    String datodeentrada;
    TextView pagar;
    EditText telefono;
    EditText cedula;
    Button generarcom;
    Spinner mySpinner;
    double entero;
    double calculo;
    int valorapagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        datodeentrada = getIntent().getStringExtra("valor");
        pagar = findViewById(R.id.textPagar);
        pagar.setText(datodeentrada);
        telefono = findViewById(R.id.editTextTextMovil);
        cedula = findViewById(R.id.editTextTextCedula);
        generarcom = findViewById(R.id.buttonGeneral);

        mySpinner = (Spinner) findViewById(R.id.spinner);
        generarcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumir();
            }
        });

    }

    public void consumir() {

        String urls = "https://pay.payphonetodoesposible.com/api/Sale";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        DecimalFormat formato2 = new DecimalFormat("#.##");
        entero = Double.valueOf(pagar.getText().toString());

        calculo=(entero-0.12);
        String valor = String.valueOf(formato2.format(calculo));
        valor = valor.replace(".", "");
        valorapagar=Integer.valueOf(valor);



        double resta= entero-calculo;
        String restacam=String.valueOf(formato2.format(resta));
        restacam=restacam.replace(".","");

        String cambioentero=String.valueOf(entero);
        cambioentero=cambioentero.replace(".","");


        int spinner_pos = mySpinner.getSelectedItemPosition();
        String[] size_values = getResources().getStringArray(R.array.size_values);
        String size = String.valueOf(size_values[spinner_pos]);

        JSONObject postData = new JSONObject();

        try{
            postData.put("phoneNumber", telefono.getText().toString());
            postData.put("countryCode", size);
            postData.put("clientUserId", cedula.getText().toString());
            postData.put("reference", "none");
            postData.put("responseUrl", "http://paystoreCZ.com/confirm.php");
            postData.put("amount", Integer.valueOf(cambioentero));
            postData.put("amountWithTax", valorapagar);
            postData.put("amountWithoutTax", 0);
            postData.put("tax", Integer.valueOf(restacam));
            postData.put("clientTransactionId", rand());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urls, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(postData.toString());
                        System.out.println(response);

                        Toast.makeText(null, response.toString(), Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer 9Fh21JRDlI3IODE38Ipt4cBR6uPfCuvQSgpbnrLyIIBEEFKRyp-E2_uswgUWm4_Ij24vH-Usw4Oyd-GJgvWvx1zesJp5Pd6s5WFRo7ESHr5rn1g-D-ggUOBhlCy1NLwzp9Or7tN703psTPTKDSi4plg3wsX7YcxBoqWJX119mLVypV5OiPbac0MVkpYmlx6F1f_N9_bSIo0Xo45gKCdzgfuy6uVXL3CPX1ctBEmS58GiAEwXbxtVlfDjBfpp1jpLJ8_9-qQc22KRwPzRG5vaMDPqHyzApu5kpQp_y2of_phXlu-ycKcLe9CidwPrwhAKgOodGSp48eRoGTpgU4PmLBjOoeA");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }

    public String rand() {
        Random random = new Random();
        return String.valueOf(random.nextInt(98389 - 12390 + 1) + 12390);


    }







}