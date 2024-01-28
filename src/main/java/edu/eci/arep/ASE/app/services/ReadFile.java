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
