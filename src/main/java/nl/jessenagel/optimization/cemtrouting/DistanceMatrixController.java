package nl.jessenagel.optimization.cemtrouting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DistanceMatrixController {
    Logger logger = LogManager.getLogger(DistanceMatrixController.class);
    private final CEMTGraph graph;

    public DistanceMatrixController(CEMTGraph graph) {
        this.graph = graph;
    }

    @GetMapping("/distanceMatrix")
    public DistanceMatrix distanceMatrix(@RequestParam(value = "coordinates", defaultValue = "Empty") String coordinates, @RequestParam(value = "CEMTClass", defaultValue = "Empty") String CEMTClass, @RequestParam(value = "nearestNodeMethod", defaultValue = "nearest") String nearestNodeMethod) {
        logger.info("Request for distance matrix with CEMTClass {}", CEMTClass);
        if (coordinates.equals("Empty")) {
            return new DistanceMatrix(1, "You did not provide any coordinates.", null);
        }
        //Check if the CEMTClass is valid
        CEMTKlasse klasse = CEMTKlasse.zero;
        if (!CEMTClass.equals("Empty")) {
            klasse = CEMTParser.parse(CEMTClass);
            if (klasse == null) {
                return new DistanceMatrix(1, "Error, CEMTClass not found", null);
            }
        }
        //Parse the coordinates
        List<CEMTNode> nodes = new ArrayList<>();
        Map<String, CEMTNode> nodeMap = new java.util.HashMap<>();
        List<String> nodeNames = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(coordinates);
            for (JsonNode node : rootNode) {
                double lat = node.get(0).asDouble();
                double lon = node.get(1).asDouble();
                CEMTNode cemtNode = null;
                if (nearestNodeMethod.equals("nearest")) {
                    cemtNode = graph.getNodeOrNearest(lat, lon);
                } else if (nearestNodeMethod.equals("class")) {
                    cemtNode = graph.getNodeOrNearest(lat, lon, klasse);
                } else {
                    return new DistanceMatrix(1, "Error: Unknown nearestNodeMethod: " + nearestNodeMethod, null);
                }
                nodeMap.put(lat + "," + lon, cemtNode);
                if (cemtNode == null) {
                    return new DistanceMatrix(1, "Error: Unable to find nodes near the provided coordinates.", null);
                }
                nodeNames.add(lat + "," + lon);
                nodes.add(cemtNode);
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return new DistanceMatrix(1, "Error: 'coordinates' should be formatted as '[[lat1,lon1],[lat2,lon2],...]'.", null);
        }

        //Calculate the distance matrix by calling the CEMTGraph

        Map<CEMTNode, Map<CEMTNode, Double>> distanceMatrix = graph.getDistanceMatrix(nodes, klasse);
        if (distanceMatrix == null) {
            return new DistanceMatrix(1, "Error: Unable to calculate the distance matrix.", null);
        }
        //Convert the distance matrix to a Map<String,Map<String,Double>>
        Map<String, Map<String, Double>> distanceMatrixString = new java.util.HashMap<>();
        for (String nodeNameFrom : nodeNames) {
            distanceMatrixString.put(nodeNameFrom, new java.util.HashMap<>());
            for (String nodeNameTo : nodeNames) {
                CEMTNode nodeFrom = nodeMap.get(nodeNameFrom);
                CEMTNode nodeTo = nodeMap.get(nodeNameTo);
                if (nodeFrom == null || nodeTo == null) {
                    return new DistanceMatrix(1, "Error: Unable to find nodes near the provided coordinates.", null);
                }
                Double distance = distanceMatrix.get(nodeFrom).get(nodeTo);
                if (distance == null) {
                    return new DistanceMatrix(1, "Error: Unable to calculate the distance matrix.", null);
                }
                distanceMatrixString.get(nodeNameFrom).put(nodeNameTo, distance);
            }
        }
        return new DistanceMatrix(0, "OK", distanceMatrixString);
    }
}
