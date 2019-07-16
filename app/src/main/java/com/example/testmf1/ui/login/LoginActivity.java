package com.example.testmf1.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.net.*;
import java.util.*;
import java.io.*;
import com.example.testmf1.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        final EditText usuario = (EditText) findViewById(R.id.username);
        final EditText contraseña = (EditText) findViewById(R.id.password);
        final Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usu = usuario.getText().toString();
                String pass = contraseña.getText().toString();
                try {
                    URL url = new URL("http://grudis-desarrollo.ddns.net:8997/cgi-bin/cgiip.exe/WService=TDMNV/FAD/WbAccesUSRAnd.html");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("Usuario", usu);
                    parameters.put("Contras", pass);
                    con.setRequestMethod("GET");
                    con.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(con.getOutputStream());
                    out.writeBytes(Ayuda.getParamsString(parameters));
                    out.flush();
                    out.close();

                    if (Ayuda.getFullResponse(con).contains("CadenaMisteriosaDeConfirmacion")) {
                        Intent menu = new Intent(LoginActivity.this, MenuPrincipal.class);
                        startActivity(menu);
                    } else {
                        Toast.makeText(loginButton.getContext(), "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        });
    }
}
