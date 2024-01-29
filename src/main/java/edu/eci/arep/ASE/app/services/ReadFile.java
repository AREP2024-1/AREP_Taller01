package edu.eci.arep.ASE.app.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import edu.eci.arep.ASE.app.http.HTTPConnection;


public class ReadFile {

    /*
     * Lee el archivo index.html y lo envia al cliente.
     * @param Socket del cliente al que se enviarán los datos de la película..
     * @throws IOException Si hay algún error de entrada/salida durante la ejecución.
     */
    public void  lecturaArchivo(Socket clientSocket) throws IOException{

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String outputLine;

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html\r\n");
        out.println("\r\n");
        File file = new File("src/main/resources/public/html/index.html");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));  
        while((outputLine = bufferedReader.readLine()) != null){
            out.println(outputLine);
            out.println("\n");
        }
                
        out.close();
        bufferedReader.close();
    }

    /*
     * Envía datos de película al cliente a través del socket proporcionado.
     * @param clientSocket Socket del cliente al que se enviarán los datos de la película.
     * @param httpConnection Instancia deL HTTPConnection utilizada para la conexión con la API.
     * @param title Título de la película de la que se desea recuperar los datos.
     * @throws IOException Si hay algún error de entrada/salida durante la ejecución.
     */
    public void getMovieData(Socket clientSocket, HTTPConnection httpConnection, String title) throws IOException{
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String outputLine = httpConnection.cacheMovie(title);

        out.println("HTTP/1.1 200 OK\r\n");
        //out.println("Content-Type: text/json\r\n");
        out.println("\r\n");
        out.println(outputLine);

        out.close();

    }
        
}
