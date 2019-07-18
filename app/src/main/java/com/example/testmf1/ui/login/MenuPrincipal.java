package com.example.testmf1.ui.login;

import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.StrictMode;
import android.service.autofill.Dataset;


import androidx.appcompat.app.AppCompatActivity;

import com.example.testmf1.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        barritas = (BarChart) findViewById(R.id.barras);
        pastelito = (PieChart) findViewById(R.id.pastel);
        String [] datosraw = Ayuda.getParamsGraficaComisionesMes(mes,lcveusr).split("-----");
        datosBarra[0] = Double.valueOf(datosraw[1]);
        datosBarra[1] = Double.valueOf(datosraw[2]);
        datosBarra[2] = Double.valueOf(datosraw[3]);
        barritas = (BarChart) pintaChartBarra(barritas,"comisiones del mes",Color.WHITE,3000);


    }

    private Chart pintaChartBarra(Chart grafica, String des, int colorTexto, int tiempoAnim) {
        grafica.getDescription().setText(des);
        grafica.getDescription().setTextSize(15);
        grafica.setBackgroundColor(colorTexto);
        grafica.animateY(tiempoAnim);
        /*leyenda de la grafica (el cuadrito con los nombres)*/
        Legend leyenda = grafica.getLegend();
        leyenda.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        ArrayList<LegendEntry> entradas = new ArrayList<LegendEntry>();
        for (int i = 0; i < labelsBarra.length; i++) {
            LegendEntry entrada = new LegendEntry();
            entrada.formColor = colores[i];
            entrada.label = labelsBarra[i];
            entradas.add(entrada);
        }
        leyenda.setCustom(entradas);
        /*llenamos la grafica de barras*/
        ArrayList<BarEntry> datosParaBarra = new ArrayList<BarEntry>();
        for (int i = 0; i < datosBarra.length; i++) {
            BarEntry entrada = new BarEntry(i, (float) datosBarra[i]);
            datosParaBarra.add(entrada);
        }
        /*ajustamos ejes*/
        grafica.getXAxis().setGranularityEnabled(true);
        grafica.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        grafica.getXAxis().setValueFormatter(new MyValueFormatter());
        BarData dataBarra = new BarData(new BarDataSet(datosParaBarra,""));
        grafica.setData(dataBarra);
        grafica.invalidate();
        return grafica;
    }
}

