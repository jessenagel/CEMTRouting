package nl.jessenagel.pusher.cemtrouting;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import net.sf.geographiclib.*;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class CEMTSystem {
    private static final Logger log = LoggerFactory.getLogger(CEMTSystem.class);
    @Id@GeneratedValue
    private Long id;
    public CEMTGraph graph;
    public CEMTSystem() {
    }


    @Bean
    public CEMTGraph cemtGraph() {
        this.graph = new CEMTGraph();
        populateGraph();
        return this.graph;
    }

    public Long getId() {
        return id;
    }


    private void populateGraph(){
        String filePath = "bevaarbaarheid.json";
        this.graph = new CEMTGraph();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(content);
            JsonNode features = rootNode.path("features");

            for (JsonNode feature : features) {
                JsonNode coordinates = feature.path("geometry").path("coordinates");
                CEMTNode prevCEMTNode = null;
                CEMTKlasse klasse = CEMTParser.parse(feature.path("properties").path("sks_code").asText());
                if(klasse ==  null){
                    log.error(feature.path("properties").path("sks_code").asText());
                }
                for (JsonNode coordinateNode : coordinates) {
                    double lon = coordinateNode.get(0).asDouble();
                    double lat = coordinateNode.get(1).asDouble();
                    CEMTNode CEMTNode = new CEMTNode(lat, lon);
                    graph.addVertex(CEMTNode);
                    if (prevCEMTNode != null) {
                        graph.addEdge(prevCEMTNode, CEMTNode, calculateDistance(prevCEMTNode, CEMTNode), klasse);
                    }
                    prevCEMTNode = CEMTNode;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private double calculateDistance(CEMTNode c1, CEMTNode c2) {
        // Implement the Haversine formula or any other distance calculation method
        return Geodesic.WGS84.Inverse(c1.getLatitude(), c1.getLongitude(), c2.getLatitude(), c2.getLongitude()).s12;

    }
}
