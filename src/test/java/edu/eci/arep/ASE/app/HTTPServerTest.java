package edu.eci.arep.ASE.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.eci.arep.ASE.app.http.HTTPConnection;
import edu.eci.arep.ASE.app.services.ReadFile;

public class HTTPServerTest {
    private HTTPConnection httpConnection = new HTTPConnection("https://www.omdbapi.com/", "d91dcd3");

    @Test
    public void deberiaSerConcurrente(){
        
        List.of(1,100).parallelStream().forEach((num)->{
            for(int i=0;i<num;i++){
                Thread thread = new Thread(() -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        var movieTest = httpConnection.getDatosPelicula("Shadowhunters");
                        JsonNode jsonNode = objectMapper.readTree(movieTest);

                        assertNotNull(movieTest);
                        assertNotEquals(movieTest, "");
                        assertEquals("Shadowhunters", jsonNode.get("Title").asText());
                       
                    } catch (Exception e) {
                        fail();
                    }

                });
                thread.start();

            }           

        });
    }
}
