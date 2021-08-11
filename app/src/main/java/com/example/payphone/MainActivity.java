package com.example.payphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {


    Button comparazapato;
    TextView acomulador;
    TextView zapatouno;
    ImageButton comprar;
    double dato=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comparazapato=findViewById(R.id.button2comprar);
        acomulador=findViewById(R.id.textViewAcumlador);
        zapatouno=findViewById(R.id.textView4);
        comprar=findViewById(R.id.imageButtonPagar);
        comparazapato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acomularvalor();

            }
        });

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enviar=new Intent(MainActivity.this, MainActivity2.class);
                enviar.putExtra("valor",acomulador.getText());
                startActivity(enviar);
            }
        });
    }
    public void acomularvalor()
    {

        acomulador.setText(zapatouno.getText());

    }
}