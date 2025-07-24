package nl.jessenagel.optimization.cemtrouting;

import java.util.Map;

public record DistanceMatrix(long id, String status, Map<String,Map<String,Double>> matrix) {
}
