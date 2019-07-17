package com.example.testmf1.ui.login;

import android.graphics.Color;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.example.testmf1.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MenuPrincipal extends AppCompatActivity {
    private BarChart barritas;
    private PieChart pastelito;
    private String [] labelsBarra = new String[] {"Comisiones Margen","Comisiones Promotores","Utilidad"};
    private double [] datosBarra = new double[3];
    Random r1 = new Random();
    private int [] colores = new int[] { Color.rgb(r1.nextInt(256) , r1.nextInt(256) , r1.nextInt(256)) , Color.rgb(r1.nextInt(256) , r1.nextInt(256) , r1.nextInt(256)) , Color.rgb(r1.nextInt(256) , r1.nextInt(256) , r1.nextInt(256))  };
    String lcveusr = getIntent().getStringExtra("lcveusr");
    int mes = Calendar.getInstance().MONTH;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);
        barritas = (BarChart) findViewById(R.id.barras);
        pastelito = (PieChart) findViewById(R.id.pastel);

    }

    private Chart pintaChart(Chart grafica, String des, int colorTexto, int tiempoAnim){
        grafica.getDescription().setText(des);
        grafica.getDescription().setTextSize(15);
        grafica.setBackgroundColor(colorTexto);
        grafica.animateY(tiempoAnim);



        return grafica;
    }
}
