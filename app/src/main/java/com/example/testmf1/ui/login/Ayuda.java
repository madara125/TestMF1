package com.example.testmf1.ui.login;
import android.content.Intent;
import android.widget.Toast;

import java.io.*;
import java.net.*;
import java.util.*;
import android.widget.Button;

import static androidx.core.content.ContextCompat.startActivity;

public class Ayuda {

    /*clase que inicio como un servicio de ayuda, y se convertira en una clase contenedora de metodos chidos :v*/
    /*contiene la¿os metodos de conexion con el sistema de margen, ya sea desarrollo o produccion*/

    public static String getParamsGraficaComisionesMes(int mes, String usu){
        try {
            URL url = new URL("http://grudis-desarrollo.ddns.net:8997/cgi-bin/cgiip.exe/WService=TDMNV/FTS/WsReporComisTotAnd.html");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Map<String, String> parameters = new HashMap<>();
            parameters.put("lcveusr", usu);
            parameters.put("mesIni", String.valueOf(mes));
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(Ayuda.getParamsString(parameters));
            out.flush();
            out.close();
            return Ayuda.getFullResponse(con);
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return "";
    }

    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }


    public static String getFullResponse(HttpURLConnection con) throws IOException {
        StringBuilder fullResponseBuilder = new StringBuilder();

        fullResponseBuilder.append(con.getResponseCode())
                .append(" ")
                .append(con.getResponseMessage())
                .append("\n");

        con.getHeaderFields()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() != null)
                .forEach(entry -> {

                    fullResponseBuilder.append(entry.getKey())
                            .append(": ");

                    List<String> headerValues = entry.getValue();
                    Iterator<String> it = headerValues.iterator();
                    if (it.hasNext()) {
                        fullResponseBuilder.append(it.next());

                        while (it.hasNext()) {
                            fullResponseBuilder.append(", ")
                                    .append(it.next());
                        }
                    }

                    fullResponseBuilder.append("\n");
                });

        Reader streamReader = null;

        if (con.getResponseCode() > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();

        fullResponseBuilder.append("Response: ")
                .append(content);

        return fullResponseBuilder.toString();
    }

}
