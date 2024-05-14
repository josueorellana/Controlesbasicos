package com.example.controlesbasicos;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class utilidades {
    static String urlConsulta = "http://192.168.0.6:5984/david/_design/enrique/_view/enrique";
    static String urlMto = "http://192.168.0.6:5984/david";
    static String user = "Josue";
    static String passwd = "ajedimoou";
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user +":"+ passwd).getBytes());
    public String generarIdUnico(){return java.util.UUID.randomUUID().toString();}
}
